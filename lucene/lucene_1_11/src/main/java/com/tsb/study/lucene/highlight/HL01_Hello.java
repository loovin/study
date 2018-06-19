package com.tsb.study.lucene.highlight;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.util.Version;

import java.io.IOException;

/**
 * 高亮
 */
public class HL01_Hello {

    public static void lighter01() {
        try {
            String txt = "我爱北京天安门，天安门上彩旗飞，伟大领袖毛主席，指引我们向前进，向前进";
            TermQuery query = new TermQuery(new Term("f", "伟大"));
            QueryScorer scorer = new QueryScorer(query);
            Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
            Formatter formatter = new SimpleHTMLFormatter("<span>","</span>");
            Highlighter highlighter = new Highlighter(formatter,scorer);
            highlighter.setTextFragmenter(fragmenter);
            String str = highlighter.getBestFragment(new MMSegAnalyzer(), "f", txt);
            System.out.println(str);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void lighter02() {
        try {
            String txt = "我爱北京天安门，天安门上彩旗飞，伟大领袖毛主席，指引我们向前进，向前进";
            Query query = (new QueryParser(Version.LUCENE_35, "f", new MMSegAnalyzer())).parse("北京 伟大");
            QueryScorer scorer = new QueryScorer(query);
            Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
            Highlighter highlighter = new Highlighter(scorer);
            highlighter.setTextFragmenter(fragmenter);
            String str = highlighter.getBestFragment(new MMSegAnalyzer(), "f", txt);
            System.out.println(str);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String s[]){
        lighter01();
        lighter02();
    }

}
