package com.tsb.study.springcloud.ribbon.springribbon.consumer;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import java.util.List;

public class MyRule implements IRule {
    private ILoadBalancer lb;
    public Server choose(Object key) {
        System.out.println("loovin rule.........................................");
        List<Server> servers = lb.getAllServers();
        for(Server server : servers){
            if(server.getPort() == 8080){
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
