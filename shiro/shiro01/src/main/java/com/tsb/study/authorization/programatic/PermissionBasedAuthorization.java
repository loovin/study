package com.tsb.study.authorization.programatic;

import com.tsb.study.authorization.utils.AuthorizationUtils;
import org.apache.shiro.subject.Subject;

/**
 * 权限认证：基于权限
 */
public class PermissionBasedAuthorization {
    public static void testIsPermitted(){
        Subject currentUser = AuthorizationUtils.login("classpath:authorization_permissionbased.ini","jack","123");
        System.out.println(currentUser.isPermitted("user:delete"));

        boolean b[] = currentUser.isPermitted("user:delete","user:select");
        for(boolean bb : b){
            System.out.print(bb +"    ");
        }
        System.out.println();

        System.out.println(currentUser.isPermittedAll("user:delete","user:select"));

    }

    public static void testCheckPermitted(){
        Subject currentUser = AuthorizationUtils.login("classpath:authorization_permissionbased.ini","jack","123");
        currentUser.checkPermission("user:delete");
    }

    public static void main(String s[]){
        testIsPermitted();
        testCheckPermitted();
    }
}
