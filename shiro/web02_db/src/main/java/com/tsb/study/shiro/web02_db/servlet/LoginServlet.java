package com.tsb.study.shiro.web02_db.servlet;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("login do get");
        req.getRequestDispatcher("login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("login do post");
        String userName = req.getParameter("userName");
        String passWord = req.getParameter("passWord");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName,passWord);
        //将登陆信息记入cookie
        token.setRememberMe(true);
        try {
            subject.login(token);

            Session session = subject.getSession();
            session.setAttribute("logininfo","login time,user:"+userName);

            resp.sendRedirect("success.jsp");
        }catch (Exception ex){
            ex.printStackTrace();
            req.setAttribute("errorInfo","用户名或者密码错误");
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        }
    }
}
