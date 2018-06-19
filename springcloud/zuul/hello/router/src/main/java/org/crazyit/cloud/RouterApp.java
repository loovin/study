package org.crazyit.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
//http://localhost:9000/source/hello/zzz
@SpringBootApplication
@EnableZuulProxy
public class RouterApp {

	public static void main(String[] args) {
		SpringApplication.run(RouterApp.class, args);
	}

}
