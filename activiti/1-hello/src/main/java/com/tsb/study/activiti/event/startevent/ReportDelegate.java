package com.tsb.study.activiti.event.startevent;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ReportDelegate implements JavaDelegate {

    public void execute(DelegateExecution arg0) {
        System.out.println("跑人了，要上报了");
    }

}
