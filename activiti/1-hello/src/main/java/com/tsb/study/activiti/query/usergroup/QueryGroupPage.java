package com.tsb.study.activiti.query.usergroup;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;

import java.util.List;

/**
 * Created by Administrator on 2018/7/19.
 */
public class QueryGroupPage {

    public static void main(String[] args) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        IdentityService is = engine.getIdentityService();

        List<Group> groups = is.createGroupQuery().listPage(3,5);
        for(Group g : groups) {
            System.out.println(g.getId() + "---" + g.getName() + "---" + g.getType());
        }

        System.out.println("用户组数量："+is.createGroupQuery().count());
    }
}
