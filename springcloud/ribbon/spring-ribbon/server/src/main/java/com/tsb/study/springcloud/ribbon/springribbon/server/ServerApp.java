package com.tsb.study.springcloud.ribbon.springribbon.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServerApp {
    public static void main(String args[]){
        new SpringApplicationBuilder(ServerApp.class).web(true).run(args);
    }

}
