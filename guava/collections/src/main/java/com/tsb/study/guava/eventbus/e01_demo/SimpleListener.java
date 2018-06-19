package com.tsb.study.guava.eventbus.e01_demo;

import com.google.common.eventbus.Subscribe;

/**
 * Created by Administrator on 2018/3/24.
 */
public class SimpleListener {

    @Subscribe
    public void doAction(String event){
        System.out.println("接收到事件："+event);
    }

    @Subscribe
    public void doAction1(String event){
        System.out.println("处理2接收事件："+event);
    }

    @Subscribe
    public void intEvent(Integer event){
        System.out.println("int event："+event);
    }
}
