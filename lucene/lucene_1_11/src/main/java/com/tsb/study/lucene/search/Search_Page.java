package com.tsb.study.lucene.search;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页搜索,2 种方式
 * 1、将所有doc查询出来,然后取对应页起始到结束位置的docs,不推荐
 * 2、记住上一次搜索结果的最后一个doc,然后从那个doc往后找
 */
public class Search_Page {
    private static List<String[]> list = new ArrayList();

    static{
        for(int i=0;i<200;i++){
            String content = "";
            content += "abc ";
            for(int m=0;m<10;m++){
                content += (char)(int)(Math.random()*26+97);
                content += (char)(int)(Math.random()*26+97);
                content += (char)(int)(Math.random()*26+97);
                content += (char)(int)(Math.random()*26+97);
                content += (char)(int)(Math.random()*26+97);
                content += " ";
            }
            String str[] = new String[]{i+"",content};
            list.add(str);
        }
    }

    private Directory directory = null;
    private IndexReader reader = null;

    public Search_Page(){
        directory = new RAMDirectory();
    }

    public synchronized IndexSearcher getSearcher() {
        try {
            if (null == reader) {
                reader = IndexReader.open(directory);
            } else {
                IndexReader tmpReader = IndexReader.openIfChanged(reader);
                if (null != tmpReader) {
                    reader.close();
                    reader = tmpReader;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new IndexSearcher(reader);
    }

    public void index() {
        System.out.println("============重新构建索引===========");
        IndexWriter writer = null;
        try {
            writer = new IndexWriter(directory,
                    new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));
            writer.deleteAll();

            for (String[] str : list) {
                Document doc = new Document();
                doc.add(new Field("name",str[0],Field.Store.YES, Field.Index.NOT_ANALYZED));
                doc.add(new Field("content",str[1],Field.Store.YES, Field.Index.ANALYZED));
                writer.addDocument(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 分页：方式一，不推荐
     * @param pageIndex
     * @param pageSize
     * @throws Exception
     */
    private void search(int pageIndex,int pageSize) throws Exception {
        IndexSearcher searcher = getSearcher();
        QueryParser parser = new QueryParser(Version.LUCENE_35, "content", new StandardAnalyzer(Version.LUCENE_35));
        Query query = parser.parse("abc");
        TopDocs tds = searcher.search(query,500);
        ScoreDoc scoreDoc[] = tds.scoreDocs;
        int start = (pageIndex - 1) * pageSize;
        int end = pageIndex * pageSize;
        for(int i=start;i<end;i++){
            Document document = searcher.doc(scoreDoc[i].doc);
            System.out.println(document.get("name") + "\t" + document.get("content"));
        }
    }

    /**
     * 分页：方式二，搜索某一结果之后的结果
     * @param pageIndex
     * @param pageSize
     * @throws Exception
     */
    private void searchByAfter(int pageIndex,int pageSize) throws Exception {
        IndexSearcher searcher = getSearcher();
        QueryParser parser = new QueryParser(Version.LUCENE_35, "content", new StandardAnalyzer(Version.LUCENE_35));
        Query query = parser.parse("abc");
        ScoreDoc lastSd = getLastScoreDoc(pageIndex, pageSize, query, searcher);
        TopDocs tds = searcher.searchAfter(lastSd,query,pageSize);
        ScoreDoc scoreDoc[] = tds.scoreDocs;
        for(int i=0;i<scoreDoc.length;i++){
            Document document = searcher.doc(scoreDoc[i].doc);
            System.out.println(document.get("name") + "\t" + document.get("content"));
        }
    }

    private ScoreDoc getLastScoreDoc(int pageIndex,int pageSize,Query query,IndexSearcher searcher) throws Exception {
        if(pageIndex==1)return null;//如果是第一页就返回空
        int num = pageSize*(pageIndex-1);//获取上一页的最后数量
        TopDocs tds = searcher.search(query, num);
        return tds.scoreDocs[num-1];
    }

    public static void main(String s[]){
        try {
            Search_Page search_page = new Search_Page();
            search_page.index();
            //search_page.search(3,30);
            search_page.searchByAfter(3,12);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(list);
    }
}
