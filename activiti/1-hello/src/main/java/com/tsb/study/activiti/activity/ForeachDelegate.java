package com.tsb.study.activiti.activity;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ForeachDelegate implements JavaDelegate {

    public void execute(DelegateExecution execution) {
        System.out.println("执行服务任务: " + execution.getVariable("data"));
    }

}
