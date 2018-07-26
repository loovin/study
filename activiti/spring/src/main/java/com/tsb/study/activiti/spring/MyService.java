package com.tsb.study.activiti.spring;

import org.activiti.engine.TaskService;

public class MyService {
    public void print(String name) {
        System.out.println("MyService的实现类处理业务方法：" + name);
    }
}
