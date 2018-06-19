package com.tsb.study.lucene.analyzer;

import com.chenlb.mmseg4j.MMSeg;
import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.util.Version;

/**
 * Created by Administrator on 2018/1/18.
 */
public class A03_ChineseAnalyzer {
    public static void test01() throws Exception {
        Analyzer a2 = new MMSegAnalyzer();
        String text = "我来自中国湖南长沙大通";
        A01_Hello.displayToken(text,a2);
    }

    public static void main(String a[]){
        try {
            test01();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
