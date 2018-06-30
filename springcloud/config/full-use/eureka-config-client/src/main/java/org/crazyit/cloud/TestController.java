package org.crazyit.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@Autowired
	private Environment env;
	
	@RequestMapping(value = "/gp", method = RequestMethod.GET)
	public String getProp() {
		return env.getProperty("test.user.name");
	}
}
