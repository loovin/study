package com.tsb.study.springboot.controller;

import com.tsb.study.springboot.pojo.Girl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by Administrator on 2018/1/13.
 */
@RestController
@RequestMapping("/girl")
public class GirlController {

    @PostMapping("add")
    public Girl girlAdd(@Valid Girl girl, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
        }
        return girl;
    }
}
