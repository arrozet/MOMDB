<%--
  Created by IntelliJ IDEA.
  User: arrozet (Rubén Oliva)
  Date: 20/06/2025
  Time: 12:00
--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>${empty user.userId ? 'Add User' : 'Edit User'}</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/admin.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body class="page-background">

<jsp:include page="cabecera_admin.jsp"/>

<section class="section">
    <div class="container">
        <h1 class="title admin-title">${empty user.userId ? 'Add New User' : 'Edit User'}</h1>
        <div class="box">
            <form:form method="post" action="/admin/saveUser" modelAttribute="user">
                <form:hidden path="userId"/>

                <div class="field">
                    <label class="label has-text-grey-darker" for="username">Username</label>
                    <div class="control">
                        <form:input path="username" id="username" cssClass="input" required="true"/>
                    </div>
                </div>

                <div class="field">
                    <label class="label has-text-grey-darker" for="email">Email</label>
                    <div class="control">
                        <form:input path="email" id="email" type="email" cssClass="input" required="true"/>
                    </div>
                </div>

                <div class="field">
                    <label class="label has-text-grey-darker" for="password">Password</label>
                    <div class="control">
                        <form:password path="password" id="password" cssClass="input" placeholder="${empty user.userId ? '' : 'Leave blank to keep current password'}"/>
                    </div>
                    <c:if test="${empty user.userId}">
                        <p class="help">Password is required for new users.</p>
                    </c:if>
                </div>

                <div class="field">
                    <label class="label has-text-grey-darker" for="profilePic">Profile Picture URL</label>
                    <div class="control">
                        <form:input path="profilePic" id="profilePic" cssClass="input"/>
                    </div>
                </div>

                <div class="field">
                    <label class="label has-text-grey-darker" for="roleId">Role</label>
                    <div class="control">
                        <div class="select">
                            <form:select path="roleId" id="roleId" items="${userRoles}" itemValue="id" itemLabel="name"/>
                        </div>
                    </div>
                </div>

                <div class="field is-grouped">
                    <div class="control">
                        <button type="submit" class="button is-info">Save</button>
                    </div>
                    <div class="control">
                        <a href="/admin/users" class="button is-link is-light">Cancel</a>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</section>
</body>
</html> 