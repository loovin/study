package com.tsb.study.lucene.score;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.function.CustomScoreProvider;
import org.apache.lucene.search.function.CustomScoreQuery;
import org.apache.lucene.search.function.FieldScoreQuery;
import org.apache.lucene.search.function.ValueSourceQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.util.Random;

/**
 * 自定义排序
 */
public class Sc01_Hello {
    private String[] content = {
            "welcome to tsb, i like book",
            "you are my son, i like pingpang",
            "i am you father, i like game",
            "do you want to die, i like footbool",
            "china i like",
            "i like  like",
            "you are so naive, i like team" };
    private String[] names = { "shangsan", "lisi", "john", "jetty", "mike", "jake","thk" };
    private String[] cat = {"first","second","second","first","first","third","first"};

    private Directory directory = null;
    private IndexReader reader = null;

    public Sc01_Hello() {
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
            Random random = new Random();
            for (int i = 0; i < content.length; i++) {
                int score = random.nextInt(600);
                doc = new Document();
                doc.add(new Field("content", content[i], Field.Store.YES, Field.Index.ANALYZED));
                doc.add(new Field("name", names[i], Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
                doc.add(new Field("cat", cat[i], Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
                doc.add(new NumericField("size",Field.Store.YES, true).setIntValue(content[i].length()));
                doc.add(new NumericField("score", Field.Store.YES,true).setIntValue(score));
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

    public void search(String queryStr) throws Exception {
        System.out.println("=====================按随机生成的 score 进行倒序==================");
        IndexSearcher searcher = getSearcher();
        QueryParser parser = new QueryParser(Version.LUCENE_35,"content",new StandardAnalyzer(Version.LUCENE_35));
        Query query = parser.parse(queryStr);
        //创建评分域
        FieldScoreQuery fscoreQuery = new FieldScoreQuery("score", FieldScoreQuery.Type.INT);
        //根据评分域和原有的query创建自定义的query对象
        TopDocs tds = searcher.search(new CustomScoreQuery(query,fscoreQuery){
            @Override
            protected CustomScoreProvider getCustomScoreProvider(IndexReader reader) throws IOException {
                return new CustomScoreProvider(reader){
                    @Override
                    public float customScore(int doc, float subQueryScore, float valSrcScore) throws IOException {
                        return 1/valSrcScore;
                    }
                };
            }
        },20);

        for(ScoreDoc sd : tds.scoreDocs){
            Document doc = searcher.doc(sd.doc);
            System.out.println(sd.doc +":("+sd.score+")["+ doc.get("name")+"::::"+doc.get("content")+"::::"+doc.get("size")+":::"+doc.get("score")+"]");
        }
    }

    public void search_cat(String queryStr) throws Exception {
        System.out.println("=====================按cat进行排序==================");
        IndexSearcher searcher = getSearcher();
        QueryParser parser = new QueryParser(Version.LUCENE_35,"content",new StandardAnalyzer(Version.LUCENE_35));
        Query query = parser.parse(queryStr);

        TopDocs tds = searcher.search(new CustomScoreQuery(query){
            @Override
            protected CustomScoreProvider getCustomScoreProvider(IndexReader reader) throws IOException {
                return new CatScoreProvider(reader);
            }
        },20);

        for(ScoreDoc sd : tds.scoreDocs){
            Document doc = searcher.doc(sd.doc);
            System.out.println(sd.doc +":("+sd.score+")["+ doc.get("name")+"::::"+doc.get("content")+"::::"+doc.get("size")+":::"+doc.get("score")+"::::"+doc.get("cat")+"]");
        }
    }

    private class CatScoreProvider extends CustomScoreProvider{
        String[] cats = null;
        public CatScoreProvider(IndexReader reader) {
            super(reader);
            try {
                cats = FieldCache.DEFAULT.getStrings(reader,"cat");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public float customScore(int doc, float subQueryScore, float valSrcScore) throws IOException {
            String cat = cats[doc];
            if("first".equals(cat)){
                return  subQueryScore *10;
            }else if("second".equals(cat)){
                return subQueryScore * 5;
            }
            return subQueryScore / 5;
        }
    }

    public static void main(String[] args) {

        try {
            Sc01_Hello util = new Sc01_Hello();
            util.index();
            util.search("like");
            util.search_cat("like");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
