package com.tsb.study.shiro.web02_db.dao;

import com.tsb.study.shiro.web02_db.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2018/2/10.
 */
public class UserDao {
    public User getByUserName(Connection conn, String userName) throws Exception{
        User user = null;

        String sql = "select * from t_user where user_name=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,userName);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()){
            user = new User();
            user.setId(rs.getInt("id"));
            user.setUserName(rs.getString("user_name"));
            user.setPassWord(rs.getString("pass_word"));
        }

        return user;
    }

    public Set<String> getPermissions(Connection conn, String userName) throws Exception {
        Set<String> permissions = new HashSet<String>();
        String sql = "select p.permission_name from t_user u,t_role r,t_permission p where u.role_id=r.id and p.role_id=r.id and u.user_name=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,userName);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            permissions.add(rs.getString("permission_name"));
        }
        return permissions;
    }

    public Set<String> getRoles(Connection conn, String userName) throws Exception {
        Set<String> roles = new HashSet<String>();
        String sql = "select r.role_name from t_user u,t_role r where u.role_id=r.id and u.user_name=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,userName);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            roles.add(rs.getString("role_name"));
        }
        return roles;
    }
}
