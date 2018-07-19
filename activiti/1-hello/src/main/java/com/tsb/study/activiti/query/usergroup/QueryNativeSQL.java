package com.tsb.study.activiti.query.usergroup;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;

import java.util.List;

/**
 */
public class QueryNativeSQL {

    public static void main(String[] args) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        IdentityService is = engine.getIdentityService();

        List<Group> groups = is.createNativeGroupQuery()
                .sql("SELECT * FROM ACT_ID_GROUP where NAME_ = #{name}")
                .parameter("name","Group_0")
                .list();
        for(Group g : groups) {
            System.out.println(g.getId() + "---" + g.getName() + "---" + g.getType());
        }
    }
}
