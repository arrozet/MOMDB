<%---
  Created by IntelliJ IDEA.
  User: arrozet (Rubén Oliva)
  Date: 19/06/2025
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="es.uma.taw.momdb.dto.UserDTO" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Role Management</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/admin.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<%
    UserDTO myUser = (UserDTO) session.getAttribute("user");
%>

<body class="page-background">
<jsp:include page="cabecera_admin.jsp" />

<section class="section">
    <div class="container">
        <h1 class="title admin-title">Role Management</h1>

        <%-- Mostrar mensaje de error si existe --%>
        <% 
            if (session.getAttribute("rolesErrorMessage") != null) { 
        %>

        <div class="notification is-danger">
            <button class="delete" onclick="this.parentElement.remove();"></button>
            <strong>Error:</strong> <%= session.getAttribute("rolesErrorMessage") %>
        </div>
        
        <%-- Limpiar el atributo de error de la sesión --%>
        <% 
            session.removeAttribute("rolesErrorMessage");
            } 
        %>

        <div class="box">
            <form:form method="post" action="/admin/changeUser" modelAttribute="usersForm">
                <table class="table is-striped is-fullwidth">
                    <thead>
                        <tr>
                            <th class="has-text-grey-darker">User</th>
                            <th class="has-text-grey-darker">Role</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="user" items="${usersForm.users}" varStatus="status">
                            <tr>
                                <td>
                                    ${user.username}
                                    <form:hidden path="users[${status.index}].userId" />
                                </td>
                                <td>
                                    <div class="select">
                                        <form:select path="users[${status.index}].roleId" items="${userRoles}" itemValue="id" itemLabel="name"/>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <br>
                <form:button class="button is-info">Save changes</form:button>
            </form:form>
        </div>
    </div>
</section>
</body>
</html> 