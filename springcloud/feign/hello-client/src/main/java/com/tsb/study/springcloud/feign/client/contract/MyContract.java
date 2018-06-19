package com.tsb.study.springcloud.feign.client.contract;

import feign.Contract;
import feign.MethodMetadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MyContract extends Contract.BaseContract {
    /**
     * 处理类级的注解
     */
    protected void processAnnotationOnClass(MethodMetadata methodMetadata, Class<?> aClass) {

    }

    /**
     * 处理方法级的注解
     */
    protected void processAnnotationOnMethod(MethodMetadata methodMetadata, Annotation annotation, Method method) {
        if(MyUrl.class.isInstance(annotation)){
            MyUrl myUrl = method.getAnnotation(MyUrl.class);
            String url = myUrl.url();
            String mymethod = myUrl.method();
            methodMetadata.template().method(mymethod);
            methodMetadata.template().append(url);
        }
    }

    /**
     * 处理参数级的注解
     */
    protected boolean processAnnotationsOnParameter(MethodMetadata methodMetadata, Annotation[] annotations, int i) {
        return false;
    }
}
