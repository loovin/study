package com.tsb.study.activiti.api.usergroup;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;

/**
 * Created by Administrator on 2018/7/19.
 */
public class SaveGroup {
    public static void main(String str[]){
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        IdentityService is = engine.getIdentityService();
        for(int i = 0; i < 10; i++) {
            Group group = is.newGroup(String.valueOf(i));
            group.setName("Group_" + i);
            group.setType("TYPE_" + i);
            is.saveGroup(group);
        }
        engine.close();
        System.exit(0);
    }
}
