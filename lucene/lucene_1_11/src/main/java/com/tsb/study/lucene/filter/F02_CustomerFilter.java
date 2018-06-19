package com.tsb.study.lucene.filter;

import com.tsb.study.lucene.analyzer.A04_Synonym;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.OpenBitSet;
import org.apache.lucene.util.Version;

import java.io.IOException;

/**
 * 自定义过滤器，过滤掉已删除的数据
 */
public class F02_CustomerFilter extends Filter{
    private String[] deletedIds = {"3","4","5"};//被删除数据的id

    public DocIdSet getDocIdSet(IndexReader reader) throws IOException {
        OpenBitSet obs = new OpenBitSet(reader.maxDoc());
        obs.set(0,reader.maxDoc()-1);//先把所有的元素都设置为1, 即全部不过滤

        int[] docs = new int[1];
        int[] freqs = new int[1];
        for(String delId : deletedIds){
            TermDocs termDocs = reader.termDocs(new Term("id",delId));
            //将查询出来的对象的位置存储到docs中, 出现的频率存储到freqs中, 返回查询出来的条数
            int count = termDocs.read(docs,freqs);
            if(count > 0){
                obs.clear(docs[0]);
            }
        }

        return obs;
    }

    private static void search() throws Exception {
        Query query = new TermQuery(new Term("content","like"));
        IndexSearcher searcher = getSearcher();
        TopDocs tds = searcher.search(query,new F02_CustomerFilter(),10);
        for(ScoreDoc sd : tds.scoreDocs){
            Document doc = searcher.doc(sd.doc);
            System.out.println(doc.get("content") +":::::::::::"+doc.get("id"));
        }
    }

    public static void main(String s[]){
        try {
            search();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String[] content = {
            "welcome to tsb, i like book",
            "you are my son, i like pingpang",
            "i am you father, i like game",
            "do you want to die, i like footbool",
            "china i like",
            "i like  like",
            "you are so naive, i like team" };

    private static IndexSearcher getSearcher(){
        try {
            Directory directory = new RAMDirectory();
            IndexWriter indexWriter = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_35,new A04_Synonym()));
            Document doc = null;
            for (int i = 0; i < content.length; i++) {
                doc = new Document();
                doc.add(new Field("content", content[i], Field.Store.YES, Field.Index.ANALYZED));
                doc.add(new Field("id", String.valueOf(i+1), Field.Store.YES, Field.Index.NOT_ANALYZED));
                indexWriter.addDocument(doc);
            }
            indexWriter.close();

            IndexSearcher searcher = new IndexSearcher(IndexReader.open(directory));
            return searcher;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
