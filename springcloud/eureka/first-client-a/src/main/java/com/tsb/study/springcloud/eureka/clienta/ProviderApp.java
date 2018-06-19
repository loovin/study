package com.tsb.study.springcloud.eureka.clienta;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Scanner;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class ProviderApp {
    public static void main(String args[]){
        Scanner scan = new Scanner(System.in);//8090 或 8091
        String port = scan.nextLine();
        new SpringApplicationBuilder(ProviderApp.class).properties("server.port="+port).run(args);
    }

    @RequestMapping("/call")
    public String provider(HttpServletRequest request){
        System.out.println("client request................");
        return "provider echo ::" + request.getRequestURL();
    }
}
