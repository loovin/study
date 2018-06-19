package com.classload.hotloaddemo;

/**
 * Created by Administrator on 2018/1/13.
 */
public class MyManager implements IBaseManager {
    private String aa = "112";
    public void logic() {
        System.out.println("热加载类，实现123");
    }
}
