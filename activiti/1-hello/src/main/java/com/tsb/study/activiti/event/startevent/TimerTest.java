package com.tsb.study.activiti.event.startevent;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * 定时器开始事件
 */
public class TimerTest {
    public static void main(String[] args) throws Exception {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 存储服务
        RepositoryService rs = engine.getRepositoryService();
        // 运行时服务
        RuntimeService runService = engine.getRuntimeService();
        
        Deployment dep = rs.createDeployment().addClasspathResource("event/startevent/timer.bpmn").deploy();
        
        long dataCount = runService.createProcessInstanceQuery().count();
        System.out.println("sleep前流程实例数量：" + dataCount);
        System.out.println("保持运行不关闭，监控表act_hi_procinst，每5秒会产生一个实例");
    }

}
