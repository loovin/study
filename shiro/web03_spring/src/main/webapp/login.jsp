<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<html>
<body>
${errorMsg}
<BR>
<form action="${pageContext.request.contextPath }/user/login.do" method="post">
    userName：<input type="text" name="userName"><BR>
    passWord：<input type="password" name="passWord"><BR>
    <input type="submit" value="登陆">
</form>
</body>
</html>
