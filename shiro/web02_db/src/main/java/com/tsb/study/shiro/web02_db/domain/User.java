package com.tsb.study.shiro.web02_db.domain;

/**
 * Created by Administrator on 2018/2/10.
 */
public class User {
    private String userName;
    private String passWord;
    private Integer id;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
