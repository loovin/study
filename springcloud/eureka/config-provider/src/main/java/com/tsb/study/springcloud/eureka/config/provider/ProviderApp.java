package com.tsb.study.springcloud.eureka.config.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Service;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class ProviderApp {
    public static void main(String args[]){
        new SpringApplicationBuilder(ProviderApp.class).web(true).run(args);
    }

    @RequestMapping("/call")
    public String provider(HttpServletRequest request){
        return "provider echo ::" + request.getRequestURL();
    }

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/serviceList",produces = "application/json")
    public List<String> serviceList(){
        List<String> serviceIds = discoveryClient.getServices();
        for(String serviceId : serviceIds){
            List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
            for(ServiceInstance serviceInstance : instances){
                System.out.println(serviceInstance.getUri());
                serviceInstance.getMetadata();
            }
        }
        return serviceIds;
    }
}
