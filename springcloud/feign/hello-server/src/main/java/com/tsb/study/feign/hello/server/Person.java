package com.tsb.study.feign.hello.server;

/**
 * Created by Administrator on 2018/6/10.
 */
public class Person {
    private int id;
    private String name;
    private int age;

    @Override
    public String toString() {
        return id+"    "+name + "    " + age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
