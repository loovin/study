package com.classload.hotloaddemo;

/**
 * 后台启动线程不断刷新重新加载,实现热加载
 */
public class MessageHandler implements Runnable {
    public void run() {
        while (true){
            IBaseManager manager = ManagerFactory.getManager(ManagerFactory.MY_MANAGER);
            manager.logic();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
