package com.tsb.study.springcloud.ribbon.hello.client;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import java.util.List;

/**
 * Created by Administrator on 2018/6/2.
 */
public class MyRule implements IRule {
    private ILoadBalancer lb;
    public Server choose(Object key) {
        List<Server> servers = lb.getAllServers();
        for(Server server : servers){
            if(server.getPort() == 8081){
                return server;
            }
        }
        return null;
    }

    public void setLoadBalancer(ILoadBalancer iLoadBalancer) {
        this.lb = iLoadBalancer;
    }

    public ILoadBalancer getLoadBalancer() {
        return this.lb;
    }
}
