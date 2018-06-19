package com.tsb.study.hystrix.hello.client.circuitbreaker;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * 断路器强制打开
 */
public class CircuitBreakerCommand {
    public static void main(String s[]){
        Command c = new Command();
        System.out.println(c.execute());
    }

    static class Command extends HystrixCommand<String>{

        public Command(){
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("TestGroup"))
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withCircuitBreakerForceOpen(true))
            );
        }
        protected String run() throws Exception {
            return "success";
        }

        @Override
        protected String getFallback() {
            return "fallback";
        }
    }
}
