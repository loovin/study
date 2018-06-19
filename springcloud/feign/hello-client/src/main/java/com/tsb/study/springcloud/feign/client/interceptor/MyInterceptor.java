package com.tsb.study.springcloud.feign.client.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Created by Administrator on 2018/6/10.
 */
public class MyInterceptor implements RequestInterceptor {
    public void apply(RequestTemplate requestTemplate) {
        System.out.println("start intercept.......................");
        requestTemplate.header("Content-Type","application/json");
    }
}
