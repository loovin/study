package org.crazyit.cloud.cache;

import org.crazyit.cloud.Member;
import org.crazyit.cloud.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {
	
	@Autowired
	private CacheService cacheService;

	@RequestMapping(value = "/cache", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String cache() {
		for(int i = 0; i < 3; i++) {
			System.out.println(cacheService.cacheMember(1));
		}
		return "";
	}
	
	@RequestMapping(value = "/rc", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String testRemoveCache() {
		System.out.println(cacheService.getCache(1));
		System.out.println(cacheService.getCache(1));
		
		cacheService.removeCache(1);
		System.out.println("#########  分隔线   ###########");
		System.out.println(cacheService.getCache(1));
		return "";
	}
}
