package com.tsb.study.guava.eventbus.e01_demo;

import com.google.common.eventbus.EventBus;

/**
 * Created by Administrator on 2018/3/24.
 */
public class SimpleEventBus {

    public static void main(String str[]){
        final EventBus eventBus = new EventBus();
        eventBus.register(new SimpleListener());

        eventBus.post("this is a simple event");

        eventBus.post(123);
    }
}
