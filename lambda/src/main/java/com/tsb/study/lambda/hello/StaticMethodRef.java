package com.tsb.study.lambda.hello;

import java.util.function.Supplier;

/**
 * 静态方法引用
 */
public class StaticMethodRef {

    public static String getValue(){
        return "hello";
    }

    public static void main(String str[]){
        Supplier<String> s = StaticMethodRef :: getValue;
        System.out.println(s.get());
    }
}
