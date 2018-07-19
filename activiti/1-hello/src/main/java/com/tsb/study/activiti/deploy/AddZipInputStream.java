package com.tsb.study.activiti.deploy;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;

/**
 * 1、每次部署都会在act_re_deployment 个中生成一条部署记录
 * 2、zip 文件会被解压成一个个的文件，然后每个文件在act_ge_bytearray 表中生成一条记录
 */
public class AddZipInputStream {

    public static void main(String[] args) throws Exception {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 存储服务
        RepositoryService rs = engine.getRepositoryService();
        
        DeploymentBuilder builder = rs.createDeployment();
        
        InputStream fis = AddZipInputStream.class.getResourceAsStream("/deploy/datas.zip");//new FileInputStream(new File("classpath:/deploy/datas.zip"));
        ZipInputStream zis = new ZipInputStream(fis);
        
        builder.addZipInputStream(zis);
        
        builder.deploy();
    }

}
