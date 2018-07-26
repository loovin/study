package com.tsb.study.activiti.ruletest;

import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class OutputDelegate implements JavaDelegate {
    public void execute(DelegateExecution exe) {
        System.out.println("结果输出类");
        List<Member> members = (List<Member>)exe.getVariable("members");
        for(Member m : members) {
            System.out.println("客户身份：" + m.getIdentity() + ", 消费金额：" + m.getAmount() + ", 优惠后金额：" + m.getResult());
        }
    }
}
