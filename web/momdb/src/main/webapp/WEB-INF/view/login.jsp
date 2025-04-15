<%--
  Created by IntelliJ IDEA.
  User: roz
  Date: 15/04/2025
  Time: 13:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome to MOMDB!</title>
</head>
<%
    String error = (String) request.getAttribute("error");
    if (error == null) {
        error = "";
    }
%>
<body>
<form method="post" action="/authenticate">
    <table>
        <tr>
            <td>Username:</td>
            <td><input type="text" name="username"/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="text" name="password"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Login"/></td>
        </tr>
    </table>
</form>
<p style="color: red"><%=error%></p>
</body>
</html>
