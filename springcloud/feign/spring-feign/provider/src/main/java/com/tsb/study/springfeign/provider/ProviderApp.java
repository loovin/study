package com.tsb.study.springfeign.provider;

import java.util.Scanner;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderApp {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);// 8080 或 8081
		String port = scan.nextLine();
		new SpringApplicationBuilder(ProviderApp.class).properties("server.port=" + port).run(args);
	}

}
