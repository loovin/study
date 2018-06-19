package com.tsb.study.hystrix.hello.client.configuration;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Created by Administrator on 2018/6/16.
 */
public class TimeOutCommand extends HystrixCommand<String>{

    public TimeOutCommand() {
//        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("TestGroup"))
//                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
//                        .withExecutionTimeoutInMilliseconds(1)));

//        super(HystrixCommandGroupKey.Factory.asKey("TestGroup"));

        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("TestGroup"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("TestThreadPool"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("myCommand"))
        );
    }

    protected String run() throws Exception {
        String url = "http://localhost:8080/normalHello";
        HttpGet httpget = new HttpGet(url);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpResponse response = httpclient.execute(httpget);
        return EntityUtils.toString(response.getEntity());
    }

    @Override
    protected String getFallback() {
        System.out.println("error fall back................................");
        return "error:..:";
    }

    public static void main(String st[]){
        ConfigurationManager.getConfigInstance().setProperty(
                "hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds",5000);

        TimeOutCommand c = new TimeOutCommand();
        String result  = c.execute();
        System.out.println(result);

    }
}
