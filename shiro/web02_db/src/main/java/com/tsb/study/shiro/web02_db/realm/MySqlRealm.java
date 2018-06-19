package com.tsb.study.shiro.web02_db.realm;

import com.tsb.study.shiro.web02_db.dao.UserDao;
import com.tsb.study.shiro.web02_db.domain.User;
import com.tsb.study.shiro.web02_db.util.DbUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.sql.Connection;

/**
 * Created by Administrator on 2018/2/10.
 */
public class MySqlRealm extends AuthorizingRealm {
    private UserDao userDao = new UserDao();

    /**
     * 为当前登陆成功的用户授予角色和权限
     * @param principalCollection
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Connection conn = null;
        try{
            conn = DbUtil.getCon();
            authorizationInfo.setRoles(userDao.getRoles(conn,userName));
            authorizationInfo.setStringPermissions(userDao.getPermissions(conn,userName));
        }catch (Exception ex){

        }finally {
            try {
                DbUtil.closeCon(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return authorizationInfo;
    }

    /**
     * 验证当前登陆用户
     * shiro会根据返回的authInfo里的密码去跟登陆密码校验
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        Connection conn = null;
        try{
            conn = DbUtil.getCon();
            User user = userDao.getByUserName(conn,userName);
            if(null != user){
                AuthenticationInfo authInfo = new SimpleAuthenticationInfo(user.getUserName(),user.getPassWord(),"xx");
                return authInfo;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            try {
                DbUtil.closeCon(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
