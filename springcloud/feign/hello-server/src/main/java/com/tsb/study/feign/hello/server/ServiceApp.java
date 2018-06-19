package com.tsb.study.feign.hello.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Scanner;

@SpringBootApplication
@RestController
public class ServiceApp {
    public static void main(String s[]){
        new SpringApplicationBuilder(ServiceApp.class).properties("server.port=8080").run(s);
    }

    @RequestMapping("call")
    public String call(HttpServletRequest request){
        return "the result visit from : "+request.getRequestURL();
    }
}
