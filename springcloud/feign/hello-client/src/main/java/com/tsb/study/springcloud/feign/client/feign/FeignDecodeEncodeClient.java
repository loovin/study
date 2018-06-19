package com.tsb.study.springcloud.feign.client.feign;

import feign.Feign;
import feign.codec.StringDecoder;
import feign.gson.GsonEncoder;

/**
 * Created by Administrator on 2018/6/10.
 */
public class FeignDecodeEncodeClient {
    public static void main(String str[]){
        Call call = Feign.builder()
                .encoder(new GsonEncoder())
                .target(Call.class,
                "http://localhost:8080");

        Person person = new Person();
        person.setId(1);
        person.setName("testp");
        person.setAge(33);

        String result = call.createPerson(person);
        System.out.println(result);

    }
}
