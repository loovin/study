package com.tsb.study.activiti.query.usergroup;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;

import java.util.List;

/**
 * Created by Administrator on 2018/7/19.
 */
public class QueryGroupList {

    public static void main(String[] args) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        IdentityService is = engine.getIdentityService();

        List<Group> groups = is.createGroupQuery().list();
        for(Group g : groups) {
            System.out.println(g.getId() + "---" + g.getName() + "---" + g.getType());
        }

        long size = is.createGroupQuery().count();
        System.out.println(size);
    }

}
