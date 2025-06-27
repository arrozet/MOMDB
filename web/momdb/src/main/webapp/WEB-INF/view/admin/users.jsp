<%--
author: arrozet (Rubén Oliva)
--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="es.uma.taw.momdb.dto.UserDTO" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>User Management</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/admin.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<%
    UserDTO myUser = (UserDTO) session.getAttribute("user");
%>

<body class="page-background">
<jsp:include page="cabecera_admin.jsp" />

<section class="section">
    <div class="container">
        <h1 class="title admin-title">User Management</h1>

        <%-- Mostrar mensaje de error si existe --%>
        <%
            if (session.getAttribute("usersErrorMessage") != null) {
        %>

        <div class="notification is-danger">
            <button class="delete" onclick="this.parentElement.remove();"></button>
            <strong>Error:</strong> <%= session.getAttribute("usersErrorMessage") %>
        </div>

        <%-- Limpiar el atributo de error de la sesión --%>
        <%
            session.removeAttribute("usersErrorMessage");
            }
        %>

        <div class="box">
            <form method="get" action="/admin/users" class="mb-4">
                <div class="field has-addons">
                    <div class="control is-expanded">
                        <input type="text" name="filterName" class="user input is-info has-background-grey" placeholder="Filter by username" value="${filterName}">
                    </div>
                    <div class="control">
                        <button type="submit" class="button is-info">
                            <span class="icon">
                                <i class="fas fa-search"></i>
                            </span>
                        </button>
                    </div>
                    <div class="control">
                        <a href="/admin/addUser" class="button is-success">
                            <span class="icon">
                                <i class="fas fa-plus"></i>
                            </span>
                        </a>
                    </div>
                </div>
            </form>

            <form:form method="post" action="/admin/changeUser" modelAttribute="usersForm">
                <table class="table is-striped is-fullwidth">
                    <thead>
                    <tr>
                        <th class="has-text-grey-darker">User</th>
                        <th class="has-text-grey-darker">Role</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="user" items="${usersForm.users}" varStatus="status">
                        <tr>
                            <td>
                                ${user.username}
                                <form:hidden path="users[${status.index}].userId" />
                                <form:hidden path="users[${status.index}].rolename" />
                                <form:hidden path="users[${status.index}].username" />
                            </td>
                            <td>
                                <div class="select">
                                    <form:select path="users[${status.index}].roleId" items="${userRoles}" itemValue="id" itemLabel="name"/>
                                </div>
                            </td>
                            <td>
                                <div class="field has-addons">
                                    <p class="control">
                                        <a class="button is-small is-info" href="/admin/editUser?id=${user.userId}">
                                            <span class="icon"><i class="fas fa-edit"></i></span>
                                        </a>
                                    </p>
                                    <p class="control">
                                        <a href="/admin/deleteUser?id=${user.userId}" class="button is-small is-danger">
                                            <span class="icon"><i class="fas fa-trash"></i></span>
                                        </a>
                                    </p>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <br>
                <form:button class="button is-info">Save role changes</form:button>
            </form:form>
        </div>
    </div>
</section>
</body>
</html> 