package com.tsb.study.hystrix.hello.client.isolation;


import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class IsolationSemaphareCommand extends HystrixCommand<String>{
    private int index;
    public IsolationSemaphareCommand(int index){
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("TestGroup")));
        this.index = index;
    }

    protected String run() throws Exception {
        Thread.sleep(500);
        System.out.println("执行，当前索引：" + index);
        return "Success";
    }

    @Override
    protected String getFallback() {
        System.out.println("回退，当前索引：" + index);
        return "Fallback";
    }

    public static void main(String str[]) throws InterruptedException {
        //线程池最大并发数量为3
        ConfigurationManager.getConfigInstance()
                .setProperty("hystrix.command.default.execution.isolation.semaphore.maxConcurrentRequests",3);
        ConfigurationManager.getConfigInstance()
                .setProperty("hystrix.command.default.execution.isolation.strategy",
                        HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE);
        for(int i=0;i<6;i++){
            final int idx = i;
            new Thread(){
                @Override
                public void run() {
                    IsolationSemaphareCommand c = new IsolationSemaphareCommand(idx);
                    c.execute();
                }
            }.start();
        }
        Thread.sleep(5000);
    }
}
