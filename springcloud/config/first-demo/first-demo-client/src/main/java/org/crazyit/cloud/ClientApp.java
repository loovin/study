package org.crazyit.cloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ClientApp {
	public static void main(String[] args) {
		new SpringApplicationBuilder(ClientApp.class).run(args);
	}
}
