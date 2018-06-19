package com.tsb.study.solr.highlight;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.net.MalformedURLException;

/**
 * Created by Administrator on 2018/1/28.
 */
public class H01_Hello {
    static final String URL = "http://localhost:8080/solr";
    static CommonsHttpSolrServer server = null;
    static {
        try {
            server = new CommonsHttpSolrServer(URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 高亮的字段必须被存储
     */
    static void test1() throws Exception {
        String q = "测试";
        SolrQuery query = new SolrQuery(q);
        query.setHighlight(true)
                .setHighlightSimplePre("<red>")
                .setHighlightSimplePost("</red>")
                .setStart(0)
                .setRows(5);
        query.setParam("hl.fl","msg_title,msg_content");//高亮的字段
        QueryResponse response = server.query(query);
        SolrDocumentList sld = response.getResults();
        System.out.println("一共查到数据条数："+sld.getNumFound());
        for(SolrDocument sd: sld){
            String id = (String) sd.getFieldValue("id");
            System.out.println(response.getHighlighting().get(id).get("msg_content"));
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
