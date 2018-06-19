package com.tsb.study.solr.adddoc;

import org.apache.solr.client.solrj.beans.Field;

/**
 * Created by Administrator on 2018/1/28.
 */
public class Message {
    private String id;
    private String title;
    private String[] content;

    public Message(){

    }

    public Message(String id,String title,String[]content){
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public String getId() {
        return id;
    }
    @Field
    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    @Field("msg_title")
    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getContent() {
        return content;
    }
    @Field("msg_content")
    public void setContent(String[] content) {
        this.content = content;
    }
}
