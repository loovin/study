package com.tsb.study.springfeign.contract;

import com.netflix.loadbalancer.IRule;
import com.tsb.study.springfeign.ribbon.MyRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Contract;

@Configuration
public class MyConfig {

	@Bean
	public Contract feignContract() {
		return new MyContract();
	}

	@Bean
	public IRule getRule(){
		return new MyRule();
	}
}
