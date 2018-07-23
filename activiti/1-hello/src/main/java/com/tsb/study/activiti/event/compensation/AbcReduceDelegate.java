package com.tsb.study.activiti.event.compensation;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class AbcReduceDelegate implements JavaDelegate {

    public void execute(DelegateExecution arg0) {
        System.out.println("农业银行扣款");
    }

}
