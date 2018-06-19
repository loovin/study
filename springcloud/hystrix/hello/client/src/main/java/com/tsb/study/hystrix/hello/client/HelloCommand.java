package com.tsb.study.hystrix.hello.client;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HelloCommand extends HystrixCommand<String> {

	public HelloCommand() {
		super(HystrixCommandGroupKey.Factory.asKey("TestGroup"));
	}

	protected String run() throws Exception {
		String url = "http://localhost:8080/normalHello";
		HttpGet httpget = new HttpGet(url);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpResponse response = httpclient.execute(httpget);
		return EntityUtils.toString(response.getEntity());
	}

}
