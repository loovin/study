package org.crazyit.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
//@DefaultProperties(defaultFallback = "getMemberFallback")
public class MemberService {

	@Autowired
	private RestTemplate restTpl;

	@HystrixCommand(fallbackMethod = "getMemberFallback", groupKey = "MemberGroup", commandKey = "MemberCommandKey", 
			commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
	}, threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "2")
	})
	public Member getMember(Integer id) {
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			
		}
		
		Member member = restTpl.getForObject(
				"http://spring-hy-member/member/{id}", Member.class, id);
		return member;
	}

	public Member getMemberFallback(Integer id) {
		Member m = new Member();
		m.setId(1);
		m.setName("error member");
		return m;
	}
}
