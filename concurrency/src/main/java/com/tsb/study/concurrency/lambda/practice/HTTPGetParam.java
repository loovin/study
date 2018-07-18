package com.tsb.study.concurrency.lambda.practice;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2018/7/9.
 */
public class HTTPGetParam {
    static String get = "itemId=1&userId=1000&token=fdew23sdfm&key=skd";

    public static void main(String str[]){
        Map<String,String> param = Stream.of(get.split("&")).map(x->x.split("=")).collect(Collectors.toMap(x->x[0], x->x[1]));
        System.out.println(param);
    }
}
