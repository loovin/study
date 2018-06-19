package com.tsb.study.shiro.web03_spring.dao;


import com.tsb.study.shiro.web03_spring.entity.User;
import com.tsb.study.shiro.web03_spring.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2018/2/10.
 */
public class UserDao {
    public User getByUserName(String userName) {
        User user = null;
        Connection conn = null;
        try{
            conn = DbUtil.getCon();
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
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                DbUtil.closeCon(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return user;
    }

    public Set<String> getPermissions(String userName)  {
        Connection conn = null;
        try{
            conn = DbUtil.getCon();
            Set<String> permissions = new HashSet<String>();
            String sql = "select p.permission_name from t_user u,t_role r,t_permission p where u.role_id=r.id and p.role_id=r.id and u.user_name=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,userName);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                permissions.add(rs.getString("permission_name"));
            }
            return permissions;
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                DbUtil.closeCon(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Set<String> getRoles(String userName)  {
        Connection conn = null;
        try{
            conn = DbUtil.getCon();
            Set<String> roles = new HashSet<String>();
            String sql = "select r.role_name from t_user u,t_role r where u.role_id=r.id and u.user_name=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,userName);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                roles.add(rs.getString("role_name"));
            }
            return roles;
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                DbUtil.closeCon(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
