package com.classload.hotloaddemo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 *  自定义类加载器来实现java类的热加载
 */
public class MyClassLoader extends ClassLoader {
    private String classPath;

    public MyClassLoader(String classPath){
        super(ClassLoader.getSystemClassLoader());
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = this.loadClassData(name);
        return this.defineClass(name,data,0,data.length);
    }

    private byte[] loadClassData(String name){
        try{
            name = name.replace(".","/");
            String path = classPath + name + ".class";
            FileInputStream fileInputStream = new FileInputStream(new File(path));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int b = 0;
            while((b = fileInputStream.read())!=-1){
                baos.write(b);
            }
            fileInputStream.close();
            return baos.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
