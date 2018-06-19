package com.tsb.study.authorization.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * 权限认证：工具类
 */
public class AuthorizationUtils {
    public static Subject login(String configFile, String userName, String passWord){
        //读取配置文件，初始化secuitymanager工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);
        SecurityManager securityManager = factory.getInstance();
        //把securitymanager绑定到SecurityUtils
        SecurityUtils.setSecurityManager(securityManager);
        //得到当前用户
        Subject currentUser = SecurityUtils.getSubject();
        //创建token令牌，用户名/密码
        UsernamePasswordToken token = new UsernamePasswordToken(userName,passWord);
        try {
            //进行登陆身份认证
            currentUser.login(token);
            System.out.println("登陆成功");
        }catch (AuthenticationException ex){
            System.err.println("登陆失败");
            ex.printStackTrace();
        }
        return currentUser;
    }
}
