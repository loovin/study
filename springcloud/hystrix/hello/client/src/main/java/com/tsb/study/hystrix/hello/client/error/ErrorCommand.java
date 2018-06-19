package com.tsb.study.hystrix.hello.client.error;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class ErrorCommand extends HystrixCommand<String> {

	public ErrorCommand() {
		super(HystrixCommandGroupKey.Factory.asKey("TestGroup"));
	}

	protected String run() throws Exception {
		String url = "http://localhost:8080/errorHello";
		HttpGet httpget = new HttpGet(url);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpResponse response = httpclient.execute(httpget);
		return EntityUtils.toString(response.getEntity());
	}

	protected String getFallback() {
		System.out.println("fall back method");
		return "fall back hello";
	}

}
