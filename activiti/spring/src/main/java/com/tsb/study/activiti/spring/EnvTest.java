package com.tsb.study.activiti.spring;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EnvTest {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                new String[] { "activiti.cfg.xml" });
        ProcessEngine engine = (ProcessEngine)ctx.getBean("processEngine");
        
        RepositoryService rs = (RepositoryService)ctx.getBean("repositoryService");
        RuntimeService runService = (RuntimeService)ctx.getBean("runtimeService");
        TaskService taskService = (TaskService)ctx.getBean("taskService");
        
        Deployment dep = rs.createDeployment().addClasspathResource("test2.bpmn").deploy();
        ProcessDefinition pd = rs.createProcessDefinitionQuery().deploymentId(dep.getId()).singleResult();
        ProcessInstance pi = runService.startProcessInstanceById(pd.getId());
        
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        System.out.println("当前流程节点：" + task.getName());
    }

}
