package com.tsb.study.concurrency.lambda.hello;

import java.util.function.Consumer;

/**
 * 对象方法引用
 */
public class ObjectMethodRef {
    public void doSth(){
        System.out.println("Hello");
    }
    public static void main(String s[]){
        Consumer<ObjectMethodRef> c = ObjectMethodRef::doSth;
        c.accept(new ObjectMethodRef());
    }
}
