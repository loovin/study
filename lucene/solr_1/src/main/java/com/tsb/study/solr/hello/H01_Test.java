package com.tsb.study.solr.hello;

import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import java.net.MalformedURLException;

/**
 * Created by Administrator on 2018/1/28.
 */
public class H01_Test {
    static final String URL = "http://localhost:8080/solr";

    private static void test1(){
        try {
            CommonsHttpSolrServer server = new CommonsHttpSolrServer(URL);

            //删除所有索引
            server.deleteByQuery("*:*");
            server.commit();

            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id","1");
            doc.addField("msg_title","这是我的第一个solrj的程序");
            doc.addField("msg_content","我的solrj的程序究竟能不能跑得起来呢？");
            server.add(doc);
            server.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String s[]){
        test1();
    }

}
