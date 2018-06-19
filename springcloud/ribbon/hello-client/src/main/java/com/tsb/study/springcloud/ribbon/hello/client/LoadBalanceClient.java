package com.tsb.study.springcloud.ribbon.hello.client;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/2.
 */
public class LoadBalanceClient {
    public static void main(String args[]){
        ILoadBalancer lb = new BaseLoadBalancer();
        List<Server> servers = new ArrayList<Server>();
        servers.add(new Server("localhost",8080));
        servers.add(new Server("localhost",8081));
        lb.addServers(servers);

        for (int i = 0; i < 20; i++) {
            Server server = lb.chooseServer(null);
            System.out.println(server);
        }
    }
}
