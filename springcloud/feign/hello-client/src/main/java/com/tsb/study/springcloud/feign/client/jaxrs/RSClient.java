package com.tsb.study.springcloud.feign.client.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * jax-rs 规范
 */
public interface RSClient {
    @GET
    @Path("/call")
    public String call();

}
