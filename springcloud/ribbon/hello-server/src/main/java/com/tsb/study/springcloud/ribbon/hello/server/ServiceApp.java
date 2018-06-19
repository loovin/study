package com.tsb.study.springcloud.ribbon.hello.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Scanner;

@SpringBootApplication
@RestController
public class ServiceApp {
    static String PORT ;
    public static void main(String s[]){
        Scanner scan = new Scanner(System.in);// 8080 或 8081
        String port = scan.nextLine();
        PORT = port;
        new SpringApplicationBuilder(ServiceApp.class).properties("server.port="+port).run(s);
    }

    @RequestMapping("call")
    public String call(HttpServletRequest request){
        return "the result from ;" + PORT +" visit from : "+request.getRequestURL();
    }
}
