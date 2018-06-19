package org.crazyit.cloud.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "spring-hy-member", fallback = HelloClientFallback.class)
public interface HelloClient {

	@RequestMapping(method = RequestMethod.GET, value = "/hello")
	public String hello();
	
	@RequestMapping(method = RequestMethod.GET, value = "/toHello")
	public String toHello();
}
