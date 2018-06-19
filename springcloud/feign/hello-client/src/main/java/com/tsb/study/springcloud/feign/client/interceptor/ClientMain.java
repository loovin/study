package com.tsb.study.springcloud.feign.client.interceptor;

import com.tsb.study.springcloud.feign.client.feign.Call;
import com.tsb.study.springcloud.feign.client.jaxrs.RSClient;
import feign.Feign;
import feign.codec.StringDecoder;
import feign.jaxrs.JAXRSContract;

/**
 * Created by Administrator on 2018/6/10.
 */
public class ClientMain {
    public static void main(String str[]){
        RSClient client = Feign.builder()
                .contract(new JAXRSContract())
                .requestInterceptor(new MyInterceptor())
                .target(RSClient.class,
                        "http://localhost:8080");
        System.out.println(client.call());
    }
}
