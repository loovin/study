package com.tsb.study.hystrix.hello.client.isolation;


import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import rx.Observer;

public class IsolationCommand extends HystrixCommand<String>{
    private int index;
    public IsolationCommand(int index){
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

    public static void main(String str[]) throws Exception {
        //线程池最大并发数量为3
        ConfigurationManager.getConfigInstance()
                .setProperty("hystrix.threadpool.default.coreSize",3);
        for(int i=0;i<6;i++){
            IsolationCommand c = new IsolationCommand(i);
            c.queue();
        }
        Thread.sleep(5000);
    }
}
