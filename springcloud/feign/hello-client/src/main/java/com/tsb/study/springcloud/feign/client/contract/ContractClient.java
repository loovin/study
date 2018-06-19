package com.tsb.study.springcloud.feign.client.contract;

public interface ContractClient {

    @MyUrl(url = "/call",method = "GET")
    public String call();
}
