package com.tsb.study.lucene.highlight;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;
import com.tsb.study.lucene.analyzer.A04_Synonym;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 查询并高亮
 */
public class HL02_SearchAndHighLight {
    static String[] ids = {"axxde","eejfs"};
    static String[] texts = {
            "我爱中国北京，上彩旗飞，伟大领袖毛主席，指引我们向前进，向前进",
            "在中国天安门的大土地上，有一个美丽的传说，激励着广大人民努力向前进"
    };
    static String[] titles = {
            "天安门上毛泽东",
            "的美丽传说"
    };
    static Map<String,String> textMap = new HashMap();
    static {
        for(int i=0;i<ids.length;i++){
            textMap.put(ids[i],texts[i]);
        }
    }

    private static void search() throws Exception {
        try {
            Directory directory = new RAMDirectory();
            IndexWriter indexWriter = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_35,new MMSegAnalyzer()));
            for(int i=0;i<texts.length;i++) {
                Document document = new Document();
                document.add(new Field("content", texts[i], Field.Store.NO, Field.Index.ANALYZED));
                document.add(new Field("title",titles[i],Field.Store.YES,Field.Index.ANALYZED));
                document.add(new Field("id", ids[i], Field.Store.YES, Field.Index.ANALYZED));
                indexWriter.addDocument(document);
            }
            indexWriter.close();

            Analyzer analyzer = new MMSegAnalyzer();
            IndexSearcher searcher = new IndexSearcher(IndexReader.open(directory));
            //QueryParser queryParser = new QueryParser(Version.LUCENE_35,"title",analyzer);

            MultiFieldQueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_35,new String[]{"title","content"},analyzer);

            Query query = queryParser.parse("天安门");
            TopDocs tds = searcher.search(query,10);
            for(ScoreDoc sd : tds.scoreDocs){
                Document doc = searcher.doc(sd.doc);
                String title = lightStr(doc.get("title"),analyzer,query,"title");
                String content = lightStr(textMap.get(doc.get("id")),analyzer,query,"content");
                System.out.println(doc.get("id")+":::"+title+":::"+content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String lightStr(String str, Analyzer analyzer, Query query,String field) throws Exception {
        String ret = null;
        QueryScorer scorer = new QueryScorer(query);
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
        Formatter formatter = new SimpleHTMLFormatter("<B>","</B>");
        Highlighter lighter = new Highlighter(formatter,scorer);
        lighter.setTextFragmenter(fragmenter);
        ret = lighter.getBestFragment(analyzer,field,str);
        if(null == ret){
            return str;
        }
        return ret;
    }

    public static void main(String a[]){
        try {
            search();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
