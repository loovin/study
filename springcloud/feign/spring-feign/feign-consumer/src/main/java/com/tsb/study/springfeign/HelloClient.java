package com.tsb.study.springfeign;

import com.tsb.study.springfeign.contract.MyUrl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("spring-feign-provider")
public interface HelloClient {

	@RequestMapping(method = RequestMethod.GET, value="/hello/{name}")
	String hello(@PathVariable("name") String name);
	
	
	@RequestMapping(method = RequestMethod.GET, value="/call/{id}")
	Police getPolice(@PathVariable("id") Integer id);
	
	@MyUrl(url = "/hellowd", method = "GET")
	String myHello();
}
