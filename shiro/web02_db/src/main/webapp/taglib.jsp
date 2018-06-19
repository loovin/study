<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

是否具有admin角色：
<shiro:hasRole name="admin">是</shiro:hasRole>
<shiro:lacksRole name="admin">否</shiro:lacksRole>
<BR>

当前用户信息：<shiro:principal/><BR>

User ID：<principal type="java.lang.Integer"/><BR>

是否具有student:create权限：
<shiro:hasPermission name="student:create">是</shiro:hasPermission>
<shiro:lacksPermission name="student:create">否</shiro:lacksPermission>
<BR>

