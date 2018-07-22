package com.tsb.study.activiti.event.endevent;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class CountDelegate implements JavaDelegate {

    public void execute(DelegateExecution arg0) {
        System.out.println("清点人数，该流程结束事件为错误结束事件");
    }

}
