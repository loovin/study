package org.crazyit.cloud.collapser;

import java.util.concurrent.Future;

import org.crazyit.cloud.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CollController {
	
	@Autowired
	private CollService collService;

	@RequestMapping(value = "/coll", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String testCollapse() throws Exception {
		// 连续执行3次请求
		Future<Member> f1 = collService.getMember(1);
		Future<Member> f2 = collService.getMember(2);
		Future<Member> f3 = collService.getMember(3);
		Member p1 = f1.get();
		System.out.println("result="+p1.toString());
		Member p2 = f2.get();
		System.out.println("result="+p2.toString());
		Member p3 = f3.get();
		System.out.println("result="+p3.toString());
		return "";
	}
}
