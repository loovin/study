package com.tsb.study.springcloud.feign.client.cxf;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/6/3.
 */
public class CxfClient {
    public static void main(String s[]){
        try {
            WebClient client = WebClient.create("http://localhost:8080/call");
            Response response = client.get();
            System.out.println(IOUtils.readStringFromStream((InputStream) response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
