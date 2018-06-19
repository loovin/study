package com.classload.hotloaddemo;

/**
 * 通过修改并编译MyManager类实现热加载,
 * 但要注意启动时不能在class目录里存在MyManager，因为这样的话MyManager会被appLoader加载，自定义的加载器不能覆盖父加载器所加载的类
 */
public class Test {
    public static void main(String s[]){
        new Thread(new MessageHandler()).start();
    }
}
