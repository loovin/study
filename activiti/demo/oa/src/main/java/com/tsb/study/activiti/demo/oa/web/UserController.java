package com.tsb.study.activiti.demo.oa.web;

import javax.servlet.http.HttpSession;

import org.activiti.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    
    @Autowired
    private IdentityService identityService;

    @RequestMapping("/login")
    public String login(String name, String passwd, HttpSession session) {
        
        boolean success = identityService.checkPassword(name, passwd);
        if(success) {
            session.setAttribute("user", name);
            return "main";
        } else {
            return "login";
        }
    }
}
