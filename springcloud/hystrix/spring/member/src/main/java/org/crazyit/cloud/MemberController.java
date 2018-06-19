package org.crazyit.cloud;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

	@RequestMapping(value = "/member/{id}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Member call(@PathVariable Integer id) {
		Member p = new Member();
		p.setId(id);
		p.setName("angus");
		return p;
	}

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello() {
		return "hello";
	}

	@RequestMapping(value = "/toHello", method = RequestMethod.GET)
	public String toHello() throws Exception {
		Thread.sleep(1000);
		return "timeout hello";
	}
}
