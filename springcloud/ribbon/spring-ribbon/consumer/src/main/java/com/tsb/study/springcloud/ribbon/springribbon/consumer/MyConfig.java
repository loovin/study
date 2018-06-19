package com.tsb.study.springcloud.ribbon.springribbon.consumer;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;

public class MyConfig {
    @Bean
    public IRule getRule(){
        return new MyRule();
    }
}
