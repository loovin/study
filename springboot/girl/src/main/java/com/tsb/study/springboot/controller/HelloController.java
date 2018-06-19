package com.tsb.study.springboot.controller;

import com.tsb.study.springboot.config.GirlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/1/13.
 */
@RestController
public class HelloController {
    @Value("${cupSize}")
    private String cupSize;
    @Value("${age}")
    private int age;
    @Value("${content}")
    private String content;

    @Autowired
    private GirlConfig girl;

    @RequestMapping(value = "hello")
    public String say(){
        StringBuffer sb = new StringBuffer();
        sb.append("<BR>cupSize: "+cupSize);
        sb.append("<BR>age: "+age);
        sb.append("<BR>content: "+content);
        sb.append("<BR>girl: "+ girl);
        return sb.toString();
    }
}
