package com.tsb.study.springboot.pojo;

import javax.validation.constraints.Min;

/**
 * Created by Administrator on 2018/1/13.
 */

public class Girl {
    private String name;
    @Min(value = 18,message = "未成年")
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
