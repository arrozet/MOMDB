<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="es.uma.taw.momdb.entity.User" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.momdb.entity.UserRole" %>
<%@ page import="es.uma.taw.momdb.dto.UserDTO" %>
<%@ page import="es.uma.taw.momdb.dto.UsersFormDTO" %><%--
  Created by IntelliJ IDEA.
  User: roz
  Date: 15/04/2025
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Welcome back, Admin!</title>
</head>
<%
    UsersFormDTO usersForm = (UsersFormDTO) request.getAttribute("usersForm");
    List<UserRole> userRoles = (List<UserRole>) request.getAttribute("userRoles");
    User myUser = (User) session.getAttribute("user");
%>
<body>
    <h1>Welcome back, <%= myUser.getUsername() %>!<br></h1>

    <form:form method="post" action="/admin/changeUser" modelAttribute="usersForm">
        <table border="1">
            <tr>
                <th>User</th>
                <th>Role</th>
            </tr>
            <c:forEach var="user" items="${usersForm.users}" varStatus="status">
                <tr>
                    <td>${user.username}</td>
                    <td>
                        <form:hidden path="users[${status.index}].userId" />
                        <form:hidden path="users[${status.index}].username" />
                        <form:select path="users[${status.index}].roleId" items="${userRoles}" itemValue="id" itemLabel="name"/>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <br>
        <form:button>Save changes</form:button>
    </form:form>

</body>
</html>
