package com.tsb.study.springfeign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@Autowired
	private HelloClient helloClient;

	@RequestMapping(method = RequestMethod.GET, value="/router")
	public String router() {
		String result = helloClient.hello("angus");
		return result;
	}

	@RequestMapping(method = RequestMethod.GET, value="/police", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Police getPolice() {
		Police p = helloClient.getPolice(1);
		return p;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/myhello")
	public String myHello() {
		return helloClient.myHello();
	}
}
