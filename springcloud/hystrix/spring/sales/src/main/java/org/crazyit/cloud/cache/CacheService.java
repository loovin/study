package org.crazyit.cloud.cache;

import org.crazyit.cloud.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;

@Service
public class CacheService {

	@Autowired
	private RestTemplate restTpl;
	
	@CacheResult
	@HystrixCommand
	public String cacheMember(Integer id) {
		System.out.println("调用 cacheMember 方法");
		return "member..cache.....result";
	}

	/**
	 * 该方法与removeCache由于都使用相同的commandKey，因此是成对的
	 * @param id
	 * @return
	 */
	@CacheResult
	@HystrixCommand(commandKey = "cacheKey")
	public String getCache(Integer id) {
		System.out.println("调用 getCache 方法");
		return "cache .. result";
	}
	
	@CacheRemove(commandKey = "cacheKey")
	@HystrixCommand
	public void removeCache(Integer id) {
		System.out.println("删除缓存方法");
	}
}
