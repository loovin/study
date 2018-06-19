package com.tsb.study.springcloud.ribbon.springribbon.consumer;

import org.springframework.cloud.netflix.ribbon.RibbonClient;

/*
可以通过该配置类注入rule ， 也可以通过yml中申明rule ， 这里注释，但在yml中有申明
@RibbonClient(name = "spring-ribbon-provider", configuration = MyConfig.class)
 */
public class MyClient {

}
