package org.crazyit.cloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class BootApp {

	public static void main(String[] args) {
		new SpringApplicationBuilder(BootApp.class).run(args);
	}

}
