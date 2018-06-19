package com.tsb.study.lucene.analyzer;

import org.apache.lucene.analysis.*;
import org.apache.lucene.util.Version;

import java.io.Reader;
import java.util.Set;

/**
 * 自定义停用词分词器
 */
public class A02_MyStopAnalyzer extends Analyzer {
    private Set stops;
    public A02_MyStopAnalyzer(String[] strs){
        stops = StopFilter.makeStopSet(Version.LUCENE_35,strs,true);
        stops.addAll(StopAnalyzer.ENGLISH_STOP_WORDS_SET);
    }
    public TokenStream tokenStream(String s, Reader reader) {
        return new StopFilter(
                Version.LUCENE_35,
                new LowerCaseFilter(Version.LUCENE_35,
                        new LetterTokenizer(Version.LUCENE_35,reader)),
                stops);
    }

    private static void test01() throws Exception {
        Analyzer a1 = new A02_MyStopAnalyzer(new String[]{"I","you","hate"});
        Analyzer a2 = new StopAnalyzer(Version.LUCENE_35);
        String text = "how are you thank you i hate you";
        A01_Hello.displayAllTokenInfo(text,a1);
        A01_Hello.displayAllTokenInfo(text,a2);
    }

    public static void main(String s[]){
        try {
            test01();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
