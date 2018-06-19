<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8" %>
<html>
<body>
<a href="<%=request.getContextPath()%>/admin">admin（要求登陆）</a><BR>
<a href="<%=request.getContextPath()%>/student">student（要求具备teacher角色）</a><BR>
<a href="<%=request.getContextPath()%>/teacher">teacher（要求具备user:create权限）</a><BR>
<BR><BR>
<a href="taglib.jsp">taglib演示</a><BR>
<BR><BR>
<a href="session.jsp">session使用</a>
</body>
</html>
