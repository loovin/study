package com.classload.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/1/14.
 */
@Controller
public class HotDeployController {
    @RequestMapping(value = "say")
    public String say(HttpServletRequest request){
        request.setAttribute("say","hello!!!ggggg");
        return "say";
    }
}
