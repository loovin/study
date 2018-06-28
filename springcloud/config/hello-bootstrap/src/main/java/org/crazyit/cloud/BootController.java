package org.crazyit.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BootController {
	
	@Autowired
	private ApplicationContext ctx;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello() {
		System.out.println("############  ");
		Environment env = ctx.getEnvironment();
		System.out.println(env.getProperty("test.user.name"));
		return "";
	}
}
