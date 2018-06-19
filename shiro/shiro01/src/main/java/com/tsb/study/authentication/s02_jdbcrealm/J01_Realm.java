package com.tsb.study.authentication.s02_jdbcrealm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;


/**
 * 身份认证
 */
public class J01_Realm {
    public static void test01(){
        //读取配置文件，初始化secuitymanager工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:jdbcrealm.ini");
        SecurityManager securityManager = factory.getInstance();
        //把securitymanager绑定到SecurityUtils
        SecurityUtils.setSecurityManager(securityManager);
        //得到当前用户
        Subject currentUser = SecurityUtils.getSubject();
        //创建token令牌，用户名/密码
        UsernamePasswordToken token = new UsernamePasswordToken("java1234","123456");
        try {
            //进行登陆身份认证
            currentUser.login(token);
            System.out.println("登陆成功");
        }catch (AuthenticationException ex){
            System.err.println("登陆失败");
            ex.printStackTrace();
        }
        //退出登陆
        currentUser.logout();
    }

    public static void main(String s[]){
        test01();
    }
}
