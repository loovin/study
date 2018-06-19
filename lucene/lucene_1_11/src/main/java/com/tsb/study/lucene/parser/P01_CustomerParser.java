package com.tsb.study.lucene.parser;

import com.tsb.study.lucene.analyzer.A04_Synonym;
import jdk.nashorn.internal.runtime.ParserException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.IOException;

/**
 * 自定义parser
 * 1、禁用模糊搜索和通配符搜索
 * 2、扩展数字类型的range
 */
public class P01_CustomerParser extends QueryParser {
    public P01_CustomerParser(Version matchVersion, String f, Analyzer a) {
        super(matchVersion, f, a);
    }

    @Override
    protected Query getWildcardQuery(String field, String termStr) throws ParseException {
        throw new ParserException("由于性能原因,已经禁用了通配符查询，请输入更精确的进行查询");
        //return super.getWildcardQuery(field, termStr);
    }

    @Override
    protected Query getFuzzyQuery(String field, String termStr, float minSimilarity) throws ParseException {
        throw new ParserException("由于性能原因,已经禁用了模糊查询");
    }

    @Override
    protected Query getRangeQuery(String field, String part1, String part2, boolean inclusive) throws ParseException {
        if("size".equals(field)){
            return NumericRangeQuery.newIntRange(field,Integer.parseInt(part1),Integer.parseInt(part2),inclusive,inclusive);
        }
        return super.getRangeQuery(field, part1, part2, inclusive);
        //return new TermRangeQuery(field,part1,part2,inclusive,inclusive);
    }

    private static void searchByCustomerQuery() throws Exception {
        P01_CustomerParser parser = new P01_CustomerParser(Version.LUCENE_35,"content",new StandardAnalyzer(Version.LUCENE_35));
//        Query query = parser.parse("like");
//        Query query = parser.parse("like~");
        Query query = parser.parse("like?");
        IndexSearcher searcher = getSearcher();
        TopDocs tds = searcher.search(query,10);
        for(ScoreDoc sd : tds.scoreDocs){
            Document doc = searcher.doc(sd.doc);
            System.out.println(doc.get("content"));
        }
    }

    private static void searchByNumericRange(String patten) throws Exception {
        P01_CustomerParser parser = new P01_CustomerParser(Version.LUCENE_35,"content",new StandardAnalyzer(Version.LUCENE_35));
        Query query = parser.parse(patten);
        IndexSearcher searcher = getSearcher();
        TopDocs tds = searcher.search(query,10);
        for(ScoreDoc sd : tds.scoreDocs){
            Document doc = searcher.doc(sd.doc);
            System.out.println(doc.get("content") +":::::::::::"+doc.get("size"));
        }
    }

    private static void searchByNumericRange() throws Exception {
        System.out.println("===============numeric Term Range==============");
        searchByNumericRange("size:[20 TO 30]");
        System.out.println("===============默认range==============");
        searchByNumericRange("[a TO b]");
    }

    public static void main(String s[]){
        try {
            //searchByCustomerQuery();
            searchByNumericRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String[] content = { "welcome to tsb, i like book", "you are my son, i like pingpang",
            "i am you father, i like game", "do you want to die, i like football", "i like china",
            "you are so naive, i like team" };
    private static IndexSearcher getSearcher(){
        try {
            Directory directory = new RAMDirectory();
            IndexWriter indexWriter = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_35,new A04_Synonym()));
            Document doc = null;
            for (int i = 0; i < content.length; i++) {
                doc = new Document();
                doc.add(new Field("content", content[i], Field.Store.YES, Field.Index.ANALYZED));
                doc.add(new NumericField("size",Field.Store.YES, true).setIntValue(content[i].length()));
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
