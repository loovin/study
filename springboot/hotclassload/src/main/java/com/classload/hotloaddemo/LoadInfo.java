package com.classload.hotloaddemo;

/**
 * Created by Administrator on 2018/1/13.
 */
public class LoadInfo {
    private MyClassLoader myClassLoader;
    private long loadTime;//类加载的时间
    private IBaseManager baseManager;

    public LoadInfo(MyClassLoader myClassLoader,long loadTime){
        super();
        this.myClassLoader = myClassLoader;
        this.loadTime = loadTime;
    }

    public MyClassLoader getMyClassLoader() {
        return myClassLoader;
    }

    public void setMyClassLoader(MyClassLoader myClassLoader) {
        this.myClassLoader = myClassLoader;
    }

    public long getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(long loadTime) {
        this.loadTime = loadTime;
    }

    public IBaseManager getBaseManager() {
        return baseManager;
    }

    public void setBaseManager(IBaseManager baseManager) {
        this.baseManager = baseManager;
    }
}
