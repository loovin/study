package com.classload.hotloaddemo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/13.
 */
public class ManagerFactory {
    //记录热加载类的加载信息
    static final Map<String,LoadInfo> loadTimeMap = new HashMap();
    public static final String CLASS_PATH = "F:\\";
    public static final String MY_MANAGER = "com.classload.MyManager";

    public static IBaseManager getManager(String className){
        File loadFile = new File(CLASS_PATH + className.replaceAll("\\.","/")+".class");
        long lastModifyTime = loadFile.lastModified();
        if(loadTimeMap.get(className) == null){
            load(className,lastModifyTime);
        }else if(loadTimeMap.get(className).getLoadTime() != lastModifyTime){
            load(className,lastModifyTime);
        }
        return loadTimeMap.get(className).getBaseManager();
    }

    private static void load(String className,long lastModifyTime){
        MyClassLoader myClassLoader = new MyClassLoader(CLASS_PATH);
        Class loadClass = null;
        try {
            loadClass = myClassLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        IBaseManager manager = newInstance(loadClass);
        LoadInfo loadInfo = new LoadInfo(myClassLoader,lastModifyTime);
        loadInfo.setBaseManager(manager);
        loadTimeMap.put(className,loadInfo);
    }

    /**
     * 以反射的方式创建目标类
     * @param loadClass
     * @return
     */
    private static IBaseManager newInstance(Class loadClass) {
        try {
            return (IBaseManager)loadClass.getConstructor(new Class[]{}).newInstance(new Object[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
