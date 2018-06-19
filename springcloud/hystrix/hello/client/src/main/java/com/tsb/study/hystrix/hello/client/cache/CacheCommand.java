package com.tsb.study.hystrix.hello.client.cache;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

public class CacheCommand extends HystrixCommand<String> {
    private String cacheKey;
    public CacheCommand(String cacheKey) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("TestGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("TestCommand")));
        this.cacheKey = cacheKey;
    }

    protected String run() throws Exception {
        return "Success";
    }

    @Override
    protected String getFallback() {
        return "FallBack";
    }

    @Override
    public String getCacheKey() {
        return cacheKey;
    }

    public static void main(String str[]){
        //ctx 表明在close()范围内对该命令是同一次请求，只有对于同一次请求才会缓存
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
        String key = "cache-key";
        for(int i=0;i<3;i++){
            CacheCommand c = new CacheCommand(key);
            c.execute();
            System.out.println(c.isResponseFromCache());
        }
        HystrixRequestCache cache = HystrixRequestCache.getInstance(HystrixCommandKey.Factory.asKey("TestCommand"),
                HystrixConcurrencyStrategyDefault.getInstance());
        cache.clear(key);

        CacheCommand c = new CacheCommand(key);
        c.execute();
        System.out.println(c.isResponseFromCache());

        ctx.close();
    }
}
