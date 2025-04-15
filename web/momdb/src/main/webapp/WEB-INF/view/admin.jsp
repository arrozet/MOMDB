<%@ page import="es.uma.taw.momdb.entity.User" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: roz
  Date: 15/04/2025
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome back, Admin!</title>
</head>
<%
    List<User> users = (List<User>) request.getAttribute("users");
    User myUser = (User) session.getAttribute("user");
%>
<body>
<h1>Welcome back, <%= myUser.getUsername() %>!<br></h1>
<table border="1">
    <tr>
        <th>User</th>
        <th>Role</th>
    </tr>
    <%
        for(User u : users){

    %>
    <tr>
        <td><%= u.getUsername()%></td>
        <td><%= u.getRole().getName()%></td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
