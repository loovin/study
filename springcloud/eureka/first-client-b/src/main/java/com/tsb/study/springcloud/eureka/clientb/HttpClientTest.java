package com.tsb.study.springcloud.eureka.clientb;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Created by Administrator on 2018/5/31.
 */
public class HttpClientTest {
    public static void main(String st[]){
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            for (int i = 0; i < 6; i++) {
                HttpGet httpGet = new HttpGet("http://localhost:8081/call");
                HttpResponse response = client.execute(httpGet);
                System.out.println(EntityUtils.toString(response.getEntity()));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
