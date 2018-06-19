package org.crazyit.cloud.feign;

import org.springframework.stereotype.Component;

@Component
public class HelloClientFallback implements HelloClient {

	public String hello() {
		return "fallback hello";
	}

	public String toHello() {
		return "fallback timeout hello";
	}

}
