package com.tsb.study.springcloud.ribbon.hello.client;

import com.netflix.client.ClientFactory;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.ConfigurationManager;
import com.netflix.niws.client.http.HttpClientRequest;
import com.netflix.niws.client.http.RestClient;

import java.net.URI;

public class Client {
    public static void main(String s[]){
        try {
            ConfigurationManager.getConfigInstance().setProperty(
                    "my-client.ribbon.listOfServers", "localhost:8080,localhost:8081");
            ConfigurationManager.getConfigInstance().setProperty(
                    "my-client.ribbon.NFLoadBalancerRuleClassName", MyRule.class.getName());
            RestClient client = (RestClient) ClientFactory.getNamedClient("my-client");
            HttpRequest request = HttpRequest.newBuilder().uri(new URI("/call")).build();
            for (int i = 0; i < 20; i++) {
                HttpResponse response = client.executeWithLoadBalancer(request);
                String result = response.getEntity(String.class);
                System.out.println(result);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
