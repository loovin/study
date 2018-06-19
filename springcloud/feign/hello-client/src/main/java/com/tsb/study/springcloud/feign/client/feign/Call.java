package com.tsb.study.springcloud.feign.client.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface Call {

    @RequestLine("POST /person/create")
    @Headers("Content-Type: application/json")
    public String createPerson(Person person);

    @RequestLine("GET /call")
    public String call();

    /*
    @RequestLine("GET /person/{id}")
    public Person getPerson(@Param("id") Integer id);
    */

}
