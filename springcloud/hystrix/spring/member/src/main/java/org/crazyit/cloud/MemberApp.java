package org.crazyit.cloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MemberApp {

	public static void main(String[] args) {
		new SpringApplicationBuilder(MemberApp.class).web(true).run(args);
	}

}
