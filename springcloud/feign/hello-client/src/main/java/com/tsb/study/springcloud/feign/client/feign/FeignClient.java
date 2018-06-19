package com.tsb.study.springcloud.feign.client.feign;

import feign.Feign;
import feign.codec.StringDecoder;
import feign.gson.GsonDecoder;

/**
 * Created by Administrator on 2018/6/3.
 */
public class FeignClient {

    public static void main(String str[]){
        Call call = Feign.builder().decoder(new StringDecoder()).target(Call.class,
                "http://localhost:8080");
        System.out.println(call.call());

        /*
        Call call2  = Feign.builder().decoder(new GsonDecoder()).target(Call.class,
                "http://localhost:8080");
        System.out.println(call2.getPerson(1));
        */
    }
}
