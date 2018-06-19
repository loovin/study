<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<shiro:notAuthenticated>当前尚未登陆，无法看到演示效果，请先<a href="login.jsp">登陆</a></shiro:notAuthenticated><BR>

<BR><BR>

登陆时放入session的内容：
${logininfo}