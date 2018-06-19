package com.tsb.study.solr.adddoc;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.Field;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 1、直接添加文档
 * 2、基于javabean 添加文档
 */
public class H02_AddDoc {
    static final String URL = "http://localhost:8080/solr";
    static CommonsHttpSolrServer server = null;
    static {
        try {
            server = new CommonsHttpSolrServer(URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private static void test() throws Exception {
        List<SolrInputDocument> list = new ArrayList();

        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id","2");
        doc.addField("msg_title","很好！solr可以工作了");
        doc.addField("msg_content","solr总算可以正式工作了");
        list.add(doc);

        SolrInputDocument doc1 = new SolrInputDocument();
        doc1.addField("id","3");
        doc1.addField("msg_title","测试一下solr的添加");
        doc1.addField("msg_content","看看能不能添加一个列表信息");
        list.add(doc1);

        server.add(list);
        server.commit();
    }

    private static void test1() throws Exception {
        List<Message> list = new ArrayList<Message>();
        list.add(new Message("4","基于java bean的添加",new String[]{"通过java bean完成添加","java bean 的添加"}));
        list.add(new Message("5","基于java bean的列表数据的添加",new String[]{"测试如何通过一个对象添加","通过对象添加的附件"}));
        server.addBeans(list);
        server.commit();
    }

    public static void main(String sr[]){
        try {
            //test();
            test1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
