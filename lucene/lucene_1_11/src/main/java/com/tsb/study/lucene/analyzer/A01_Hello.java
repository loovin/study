package com.tsb.study.lucene.analyzer;


import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.io.StringReader;

/**
 * 分词查看
 */
public class A01_Hello {

    /**
     * 查看分词基本信息
     * @param str
     * @param analyzer
     * @throws Exception
     */
    public static void displayToken(String str, Analyzer analyzer) throws Exception {
        TokenStream tokenStream = analyzer.tokenStream("content",new StringReader(str));
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        while(tokenStream.incrementToken()){
            System.out.print("["+charTermAttribute+"]");
        }
        System.out.println();
    }

    /**
     * 查看分词的全部信息
     * 1、posision增量信息
     * 2、offset
     */
    public static void displayAllTokenInfo(String str, Analyzer analyzer) throws Exception {
        TokenStream tokenStream = analyzer.tokenStream("content",new StringReader(str));
        PositionIncrementAttribute position = tokenStream.addAttribute(PositionIncrementAttribute.class);
        OffsetAttribute offset = tokenStream.addAttribute(OffsetAttribute.class);
        CharTermAttribute term = tokenStream.addAttribute(CharTermAttribute.class);
        TypeAttribute type = tokenStream.addAttribute(TypeAttribute.class);
        while (tokenStream.incrementToken()){
            System.out.print(position.getPositionIncrement()+":");
            System.out.print(term+"["+offset.startOffset()+"-"+offset.endOffset()+"]-->"+type.type());
            System.out.println();
        }
        System.out.println();
    }

    private static void test01() throws Exception {
        Analyzer analyzer1 = new StandardAnalyzer(Version.LUCENE_35);
        Analyzer analyzer2 = new StopAnalyzer(Version.LUCENE_35);
        Analyzer analyzer3 = new SimpleAnalyzer(Version.LUCENE_35);
        Analyzer analyzer4 = new WhitespaceAnalyzer(Version.LUCENE_35);

        String text = "this is my house,I am come from hunan changsha,My email is abc@qq.com," +
                "My QQ is 123456";
        //System.out.println("--------------StandardAnalyzer------------");
        displayToken(text,analyzer1);
        //System.out.println("--------------StopAnalyzer------------");
        displayToken(text,analyzer2);
        //System.out.println("--------------SimpleAnalyzer------------");
        displayToken(text,analyzer3);
        //System.out.println("--------------WhitespaceAnalyzer------------");
        displayToken(text,analyzer4);
    }

    private static void test02() throws Exception {
        String text = "我来自中国湖南长沙岳麓区";
        Analyzer analyzer1 = new StandardAnalyzer(Version.LUCENE_35);
        Analyzer analyzer2 = new StopAnalyzer(Version.LUCENE_35);
        Analyzer analyzer3 = new SimpleAnalyzer(Version.LUCENE_35);
        Analyzer analyzer4 = new WhitespaceAnalyzer(Version.LUCENE_35);
        //System.out.println("--------------StandardAnalyzer------------");
        displayToken(text,analyzer1);
        //System.out.println("--------------StopAnalyzer------------");
        displayToken(text,analyzer2);
        //System.out.println("--------------SimpleAnalyzer------------");
        displayToken(text,analyzer3);
        //System.out.println("--------------WhitespaceAnalyzer------------");
        displayToken(text,analyzer4);
    }

    private static void test03() throws Exception {
        String text = "how are you thank you";
        Analyzer analyzer1 = new StandardAnalyzer(Version.LUCENE_35);
        Analyzer analyzer2 = new StopAnalyzer(Version.LUCENE_35);
        Analyzer analyzer3 = new SimpleAnalyzer(Version.LUCENE_35);
        Analyzer analyzer4 = new WhitespaceAnalyzer(Version.LUCENE_35);
        System.out.println("--------------StandardAnalyzer------------");
        displayAllTokenInfo(text,analyzer1);
        System.out.println("--------------StopAnalyzer------------");
        displayAllTokenInfo(text,analyzer2);
        System.out.println("--------------SimpleAnalyzer------------");
        displayAllTokenInfo(text,analyzer3);
        System.out.println("--------------WhitespaceAnalyzer------------");
        displayAllTokenInfo(text,analyzer4);
    }

    public static void main(String s[]){
        try {
            test03();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
