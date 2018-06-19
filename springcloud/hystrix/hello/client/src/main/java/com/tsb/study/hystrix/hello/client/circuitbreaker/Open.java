package com.tsb.study.hystrix.hello.client.circuitbreaker;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandMetrics;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 	本案例设置10秒内超过10次请求、错误率大于50%，则打开断路器
 	本例中由于前11次请求会超时，所以在第11次请求时断路器自动打开
 	在第13次时由于调用睡了5秒，此时会尝试执行一次，发现不超时，则自动关闭断路器
 	HealthCounts类能统计当前健康信息，即访问总数、出错次数、出错比例等
 */
public class Open {
    public static void main(String s[]) throws InterruptedException {
        ConfigurationManager.getConfigInstance()
                //条件1 ：10 秒内达到 10 个请求
                .setProperty("hystrix.command.default.circuitBreaker.requestVolumeThreshold",10);
        for(int i=0;i<15;i++){
            if(i==12){
                Thread.sleep(5000L);
            }
            ErrorCommand c = new ErrorCommand(i>10?1:800);
            HystrixCommandMetrics.HealthCounts healthCounts = c.getMetrics().getHealthCounts();
            System.out.println(c.execute()+"      断路器是否打开："+c.isCircuitBreakerOpen()+" , 请求总数："+healthCounts.getTotalRequests());
        }
    }

    static class ErrorCommand extends HystrixCommand<String>{
        int sleep = 0;
        public ErrorCommand(int sleep){
            super(
                    Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("TestGroup"))
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                            .withExecutionTimeoutInMilliseconds(500)
                    )
            );
            this.sleep = sleep;
        }

        @Override
        protected String run() throws Exception {
            Thread.sleep(sleep);
            return "Success";
        }

        @Override
        protected String getFallback() {
            return "fallback";
        }
    }
}
