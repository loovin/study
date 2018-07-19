package com.tsb.study.activiti.procdef;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;

import java.util.List;
import java.util.UUID;

/**
 * 只有Angus 能启动流程，也可以设置用户组
 */
public class AuthStartProc {
    public static void main(String[] args) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService rs = engine.getRepositoryService();
        IdentityService is = engine.getIdentityService();

        User user = is.newUser(UUID.randomUUID().toString());
        user.setFirstName("Angus");
        is.saveUser(user);

        DeploymentBuilder builder = rs.createDeployment();
        builder.addClasspathResource("first.bpmn");
        Deployment dep = builder.deploy();

        ProcessDefinition def = rs.createProcessDefinitionQuery()
                .deploymentId(dep.getId()).singleResult();
        rs.addCandidateStarterUser(def.getId(), user.getId());

        List<ProcessDefinition> defs = rs.createProcessDefinitionQuery().startableByUser(user.getId()).list();
        for(ProcessDefinition de : defs) {
            System.out.println(de.getId());
        }
    }
}
