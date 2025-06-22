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
    <title>My profile | MOMDB</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
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
        <div class="columns is-centered">
            <div class="column is-half">
                <div class="box" style="background-color: #4a4a4a;">
                    <div class="media">
                        <div class="media-left">
                            <figure class="image is-96x96">
                                <img class="is-rounded" src="${userDTO.profilePic}" alt="User profile picture">
                            </figure>
                        </div>
                        <div class="media-content" style="display: flex; flex-direction: column; justify-content: center;">
                            <p class="title is-4 has-text-white">
                                ${userDTO.username}
                                <span class="icon has-text-warning">
                                    <i class="fas fa-medal"></i>
                                </span>
                            </p>
                            <p class="subtitle is-6 has-text-grey-lighter">${userDTO.rolename}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
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