package com.tsb.study.springcloud.eureka.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import java.util.Scanner;

@SpringBootApplication
@EnableEurekaServer
public class ServerApp {

    public static void main(String args[]){
        Scanner scan = new Scanner(System.in);//slaveA 或 slaveB
        String profiles = scan.nextLine();
        new SpringApplicationBuilder(ServerApp.class).profiles(profiles).run(args);
    }
}
