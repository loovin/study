package com.tsb.study.activiti.api.usergroup;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;

import java.util.List;

/**
 * 调用orderBy 后一定要再次调用 desc 或 asc 进行排序，否则不会生效
 */
public class QueryGroupSort {

    public static void main(String[] args) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        IdentityService is = engine.getIdentityService();

        List<Group> groups = is.createGroupQuery().orderByGroupId().desc().orderByGroupName().asc().list();
        for(Group g : groups) {
            System.out.println(g.getId() + "---" + g.getName() + "---" + g.getType());
        }
    }

}
