package com.tsb.study.feign.hello.server;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @RequestMapping(value = "/person/create",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createPerson(@RequestBody Person person){
        System.out.println(person.toString());
        return "success : "+person.toString();
    }
}
