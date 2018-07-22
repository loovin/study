package com.tsb.study.activiti.event.startevent;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class CountDelegate implements JavaDelegate {

    public void execute(DelegateExecution arg0) {
        System.out.println("清点人数，要抛出错误");
        throw new org.activiti.engine.delegate.BpmnError("abc");
    }

}
