package com.tsb.study.activiti.api.job;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ExceptionDelegate implements JavaDelegate {

    public void execute(DelegateExecution arg0) {
        System.out.println("这是处理类");
        throw new RuntimeException("always exception");
    }
}
