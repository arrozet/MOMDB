<%--
  Created by IntelliJ IDEA.
  User: amcgiluma (Juan Manuel Valenzuela)
  Date: 20/06/2025
  Time: 17:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/login.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body class="login-page">
<jsp:include page="banner.jsp"/>

<div class="login-box">
    <h1 class="title has-text-centered">Registration</h1>

    <c:if test="${not empty error}">
        <div class="notification is-danger is-light">
                ${error}
        </div>
    </c:if>

    <form:form method="post" action="/register" modelAttribute="user">
        <div class="field">
            <label for="username" class="label">Username</label>
            <div class="control has-icons-left">
                <form:input type="text" class="input is-info" id="username" path="username" required="required"/>
                <span class="icon is-small is-left">
                    <i class="fas fa-user"></i>
                </span>
            </div>
        </div>
        <div class="field">
            <label for="email" class="label">Email</label>
            <div class="control has-icons-left">
                <form:input type="email" class="input is-info" id="email" path="email" required="required"/>
                <span class="icon is-small is-left">
                    <i class="fas fa-envelope"></i>
                </span>
            </div>
        </div>
        <div class="field">
            <label for="password" class="label">Password</label>
            <div class="control has-icons-left">
                <form:password class="input is-info" id="password" path="password" required="required"/>
                <span class="icon is-small is-left">
                    <i class="fas fa-lock"></i>
                </span>
            </div>
        </div>
        <div class="field">
            <div class="buttons">
                <button type="submit" class="button login">Register</button>
                <a href="/login" class="button register">Back to Login</a>
            </div>
        </div>
    </form:form>
</div>

</body>
</html>