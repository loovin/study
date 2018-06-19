package com.tsb.study.lucene.filter;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 * 过滤条件：where
 */
public class F01_Hello {
    private String[] content = {
            "welcome to tsb, i like book",
            "you are my son, i like pingpang",
            "i am you father, i like game",
            "do you want to die, i like footbool",
            "china i like",
            "i like  like",
            "you are so naive, i like team" };
    private String[] names = { "zhangsan", "lisi", "john", "aetty", "mike", "cake","bacle" };

    private Directory directory = null;
    private IndexReader reader = null;

    public F01_Hello() {
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

            Document doc = null;
            for (int i = 0; i < content.length; i++) {
                doc = new Document();
                doc.add(new Field("content", content[i], Field.Store.YES, Field.Index.ANALYZED));
                doc.add(new Field("name", names[i], Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
                doc.add(new NumericField("size",Field.Store.YES, true).setIntValue(content[i].length()));
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

    public void search(String queryStr,Filter filter) throws Exception {
        IndexSearcher searcher = getSearcher();
        QueryParser parser = new QueryParser(Version.LUCENE_35,"content",new StandardAnalyzer(Version.LUCENE_35));
        Query query = parser.parse(queryStr);
        TopDocs tds = null;
        if(null != filter) {
            tds = searcher.search(query, filter,15);
        }else{
            tds = searcher.search(query,15);
        }
        for(ScoreDoc sd : tds.scoreDocs){
            Document doc = searcher.doc(sd.doc);
            System.out.println(sd.doc +":("+sd.score+")["+ doc.get("name")+"::::"+doc.get("content")+"::::"+doc.get("size")+"]");
        }
    }

    private static TermRangeFilter termRangeFilter(){
        TermRangeFilter filter = new TermRangeFilter("name","b","j",true,true);
        return filter;
    }

    private static Filter queryWraperFilter(){
        QueryWrapperFilter filter = new QueryWrapperFilter(new WildcardQuery(new Term("name","*ke")));
        return filter;
    }

    public static void main(String[] args) {

        try {
            F01_Hello util = new F01_Hello();
            util.index();
            System.out.println("---------------------不过滤-----------------");
            util.search("like",null);
            System.out.println("---------------------name:a-j-----------------");
            util.search("like",termRangeFilter());
            System.out.println("---------------------数字过滤-----------------");
            util.search("like",NumericRangeFilter.newIntRange("size",20,30,true,true));
            System.out.println("---------------------通过query过滤（非常实用）-----------------");
            util.search("like",queryWraperFilter());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
