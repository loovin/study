package com.tsb.study.activiti.event.compensation;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class IcbcAddDelegate implements JavaDelegate {

    public void execute(DelegateExecution arg0) {
        System.out.println("工商银行加款");
    }

}
