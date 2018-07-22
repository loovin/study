package com.tsb.study.activiti.event.endevent.dlg;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class CompenstaionDelegate implements JavaDelegate {

    public void execute(DelegateExecution arg0) {
        System.out.println("进行补偿处理");
    }

}
