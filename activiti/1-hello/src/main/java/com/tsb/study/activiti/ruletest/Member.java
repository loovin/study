package com.tsb.study.activiti.ruletest;

import java.io.Serializable;

public class Member implements Serializable {

    private String identity;
    
    // 消费金额
    private int amount;
    
    // 规则计算后的金额
    private double result;

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double discount) {
        this.result = amount * discount;
    }
    
    public static void main(String[] args) {
        Member m = new Member();
        m.setAmount(100);
        m.setResult(0.8);
        System.out.println(m.getResult());
    }
}
