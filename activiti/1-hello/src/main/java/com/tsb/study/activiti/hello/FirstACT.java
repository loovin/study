package com.tsb.study.activiti.hello;

import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public class FirstACT {
    static void first(){
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        //存储服务
        RepositoryService repositoryService = engine.getRepositoryService();
        //运行时服务
        RuntimeService runtimeService = engine.getRuntimeService();
        //任务服务
        TaskService taskService = engine.getTaskService();

        //部署：会将流程相关的数据保存到数据库表
        repositoryService.createDeployment().addClasspathResource("first.bpmn").deploy();

        //根据process id 启动流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess_1");

        // 普通员工进行请假任务
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        System.out.println("当前流程节点：" + task.getName());
        taskService.complete(task.getId());

        // 经理进行审批流程
        task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        System.out.println("当前流程节点：" + task.getName());
        taskService.complete(task.getId());

        task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        System.out.println("流程结束：" + task);

        engine.close();
        System.exit(0);
    }

    public static void main(String args[]){
        first();
    }
}
