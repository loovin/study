package com.tsb.study.springcloud.ribbon.springribbon.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Scanner;

@SpringBootApplication
@EnableEurekaClient
@RestController
@Configuration
public class ConsumerApp {
    public static void main(String s[]){
        new SpringApplicationBuilder(ConsumerApp.class).web(true).run(s);
    }

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @RequestMapping("/call")
    public String consume(){
        return getRestTemplate().getForObject("http://spring-ribbon-provider/call",String.class);
    }

    @Autowired
    private LoadBalancerClient client;
    //获取服务实例
    @RequestMapping("/lb")
    public ServiceInstance lb(){
        return client.choose("spring-ribbon-provider");
    }

    @Autowired
    SpringClientFactory factory;
    @RequestMapping("/fa")
    public String factory(){
        return factory.getLoadBalancer("spring-ribbon-provider").getClass().getName();
    }
}
