package com.tsb.study.authorization.programatic;

import com.tsb.study.authorization.utils.AuthorizationUtils;
import org.apache.shiro.subject.Subject;

import java.util.Arrays;

/**
 * 权限认证：基于角色
 */
public class RoleBasedAuthorization {
    public static void testHasRole(){
        Subject currentUser = AuthorizationUtils.login("classpath:authorization_rolebased.ini","java1234","123456");
        boolean hasrole = currentUser.hasRole("role1");
        System.out.println(hasrole?"有角色role1":"没有角色role1");

        System.out.println("==========================================");
        boolean b[] = currentUser.hasRoles(Arrays.asList("role1","role2","role3"));
        for(boolean bb:b){
            System.out.print(bb +"      ");
        }

        System.out.println();
        System.out.println("==========================================");
        boolean bbb = currentUser.hasAllRoles(Arrays.asList("role1","role2","role3"));
        System.out.println(bbb);
    }

    public static void testCheckRole(){
        Subject currentUser = AuthorizationUtils.login("classpath:authorization_rolebased.ini","java1234","123456");
        currentUser.checkRole("role3");
    }

    public static void main(String s[]){
        testHasRole();
        System.out.println("********************* check role **************");
        try {
            testCheckRole();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
