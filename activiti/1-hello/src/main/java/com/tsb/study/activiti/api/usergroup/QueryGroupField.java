package com.tsb.study.activiti.api.usergroup;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;

/**
 * singleResult 如果查出多条会抛出异常
 */
public class QueryGroupField {

    public static void main(String[] args) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        IdentityService is = engine.getIdentityService();

        Group g = is.createGroupQuery().groupName("Group_0").singleResult();
        System.out.println(g.getId() + "---" + g.getName() + "---" + g.getType());
    }

}
