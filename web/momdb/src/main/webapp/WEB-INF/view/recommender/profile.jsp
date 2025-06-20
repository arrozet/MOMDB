<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.taw.momdb.dto.UserDTO" %>
<%--
  Created by IntelliJ IDEA.
  User: amcgiluma (Juan Manuel Valenzuela)
  Date: 20/06/2025
  Time: 16:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile | MOMDB</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/user.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body class="page-background">
<jsp:include page="cabecera_recommender.jsp" />

<section class="hero is-small page-banner">
    <div class="hero-body">
        <div class="container has-text-centered">
            <h1 class="title">Your profile</h1>
        </div>
    </div>
</section>

<section class="section">
    <div class="container">
        <form:form method="POST" action="/recommender/editProfile" modelAttribute="userDTO">
            <form:hidden path="userId"/>
            <div class="field">
                <label class="label has-text-white">Username</label>
                <form:input path="username" class="input is-warning has-background-grey"/>
            </div>
            <div class="field">
                <label class="label has-text-white">Profile picture link</label>
                <form:input path="profilePic" class="input is-warning has-background-grey"/>
            </div>
            <div class="field">
                <label class="label has-text-white">Role name</label>
                <form:input path="rolename" class="input is-warning has-background-grey" readonly="true"/>
            </div>
            <div class="field">
                <button type="submit" class="button is-warning">Save changes</button>
            </div>
        </form:form>

        <div class="container has-text-centered mt-5">
            <a href="/logout" class="button is-danger">Logout</a>
        </div>
    </div>
</section>

</body>
</html>