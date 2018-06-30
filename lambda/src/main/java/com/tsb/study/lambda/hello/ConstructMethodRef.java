package com.tsb.study.lambda.hello;

import java.util.function.Supplier;

/**
 * Created by Administrator on 2018/6/29.
 */
public class ConstructMethodRef {
    public ConstructMethodRef(){

    }
    public static void main(String args[]){
        Supplier<ConstructMethodRef> s = ConstructMethodRef::new;
        System.out.println(s.get());
    }
}
