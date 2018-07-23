package com.tsb.study.activiti.event.compensation;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class IcbcReduceDelegate implements JavaDelegate {

    public void execute(DelegateExecution arg0) {
        System.out.println("工商银行扣款");
    }

}
