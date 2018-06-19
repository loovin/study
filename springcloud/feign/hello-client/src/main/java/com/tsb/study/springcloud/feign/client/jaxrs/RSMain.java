package com.tsb.study.springcloud.feign.client.jaxrs;

import com.tsb.study.springcloud.feign.client.feign.Call;
import feign.Feign;
import feign.codec.StringDecoder;
import feign.jaxrs.JAXRSContract;

/**
 * Created by Administrator on 2018/6/10.
 */
public class RSMain {
    public static void main(String s[]){
        RSClient client = Feign.builder()
                .contract(new JAXRSContract())//需要解析RSClient 类里面的jax 注解
                .target(RSClient.class,
                "http://localhost:8080");
        System.out.println(client.call());
    }
}
