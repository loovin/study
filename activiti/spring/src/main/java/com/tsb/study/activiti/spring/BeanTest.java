package com.tsb.study.activiti.spring;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanTest {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                new String[] { "activiti.cfg.xml" });
        RepositoryService rs = (RepositoryService)ctx.getBean("repositoryService");
        RuntimeService runService = (RuntimeService)ctx.getBean("runtimeService");
        
        Deployment dep = rs.createDeployment().addClasspathResource("test-bean.bpmn").deploy();
        ProcessDefinition pd = rs.createProcessDefinitionQuery().deploymentId(dep.getId()).singleResult();
        
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("name", "angus");
        ProcessInstance pi = runService.startProcessInstanceById(pd.getId(), vars);
    }

}
