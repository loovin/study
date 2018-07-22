package com.tsb.study.activiti.api.job;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class MyJavaDelegate implements JavaDelegate {

    public void execute(DelegateExecution arg0) {
        System.out.println("这是处理类");
    }
}
