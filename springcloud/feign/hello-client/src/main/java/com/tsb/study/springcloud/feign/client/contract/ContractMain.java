package com.tsb.study.springcloud.feign.client.contract;

import com.tsb.study.springcloud.feign.client.jaxrs.RSClient;
import feign.Feign;
import feign.jaxrs.JAXRSContract;

/**
 * Created by Administrator on 2018/6/10.
 */
public class ContractMain {
    public static void main(String st[]){
        ContractClient client = Feign.builder()
                .contract(new MyContract())//需要解析RSClient 类里面的jax 注解
                .target(ContractClient.class,
                        "http://localhost:8080");
        System.out.println(client.call());
    }
}
