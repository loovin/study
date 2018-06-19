package org.crazyit.cloud.collapser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.crazyit.cloud.Member;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class CollService {

	@HystrixCollapser(batchMethod = "getMembers", collapserProperties = {
			@HystrixProperty(name = "timerDelayInMilliseconds", value = "1000")
	})
	public Future<Member> getMember(Integer id) {
		System.out.println("执行单个查询的方法");
		return null;
	}
	
	@HystrixCommand
	public List<Member> getMembers(List<Integer> ids) {
		System.out.println("请求getMembers方法。。。。。。。。。。。。。。。。");
		List<Member> mems = new ArrayList<Member>();
		for(Integer id : ids) {
			Member m = new Member();
			m.setId(id);
			m.setName("angus");
			mems.add(m);
		}
		return mems;
	}
}
