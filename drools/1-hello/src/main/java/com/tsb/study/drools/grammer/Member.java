package com.tsb.study.drools.grammer;

public class Member {

    private String identity;
    
    // 消费金额
    private int amount;
    
    // 优惠后的金额
    private int afterDiscount;

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

    public int getAfterDiscount() {
        return afterDiscount;
    }

    public void setAfterDiscount(int afterDiscount) {
        this.afterDiscount = afterDiscount;
    }
    
}
