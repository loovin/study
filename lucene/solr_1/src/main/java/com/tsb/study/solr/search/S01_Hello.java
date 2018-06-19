package com.tsb.study.solr.search;

import com.tsb.study.solr.adddoc.Message;
import org.apache.solr.client.solrj.ResponseParser;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.net.MalformedURLException;
import java.util.List;

/**
 * 1、getResults
 * 2、getBeans 不常用，无法获取总数量，无法加高亮
 *
 */
public class S01_Hello {
    static final String URL = "http://localhost:8080/solr";
    static CommonsHttpSolrServer server = null;
    static {
        try {
            server = new CommonsHttpSolrServer(URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    static void test() throws Exception {
        String q = "*";
        //String q = "测试";
        SolrQuery query = new SolrQuery(q);
        query.setStart(0);
        query.setRows(2);
        QueryResponse response = server.query(query);
        SolrDocumentList sld = response.getResults();
        System.out.println("一共查到数据条数："+sld.getNumFound());
        for(SolrDocument sd: sld){
            System.out.print(sd.getFieldValue("msg_content"));
            System.out.println(sd.toString());
        }
    }

    static void test1() throws Exception {
        String q = "基于";
        //String q = "测试";
        SolrQuery query = new SolrQuery(q);
        query.setStart(0);
        query.setRows(2);
        QueryResponse response = server.query(query);
        List<Message> list = response.getBeans(Message.class);
        for(Message message:list){
            System.out.println(message.getId());
        }
    }

    public static void main(String s[]){
        try {
            test1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
