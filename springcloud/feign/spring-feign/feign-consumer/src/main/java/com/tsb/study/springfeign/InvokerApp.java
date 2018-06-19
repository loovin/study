package com.tsb.study.springfeign;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class InvokerApp {

	public static void main(String[] args) {
		new SpringApplicationBuilder(InvokerApp.class).web(true).run(args);
	}

}
