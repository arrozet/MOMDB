<%--
  Created by IntelliJ IDEA.
  User: amcgiluma (Juan Manuel Valenzuela)
  Date: 20/06/2025
  Time: 17:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Registro de Usuario</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/login.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body class="login-page">
<jsp:include page="banner.jsp"/>

<div class="login-box">
    <h1 class="title has-text-centered">Registro de Nuevo Usuario</h1>

    <c:if test="${not empty error}">
        <div class="notification is-danger is-light">
                ${error}
        </div>
    </c:if>

    <form method="post" action="/register">
        <div class="field">
            <label for="username" class="label">Nombre de usuario:</label>
            <div class="control has-icons-left">
                <input type="text" class="input is-info" id="username" name="username" required>
                <span class="icon is-small is-left">
                    <i class="fas fa-user"></i>
                </span>
            </div>
        </div>
        <div class="field">
            <label for="email" class="label">Correo electrónico:</label>
            <div class="control has-icons-left">
                <input type="email" class="input is-info" id="email" name="email" required>
                <span class="icon is-small is-left">
                    <i class="fas fa-envelope"></i>
                </span>
            </div>
        </div>
        <div class="field">
            <label for="password" class="label">Contraseña:</label>
            <div class="control has-icons-left">
                <input type="password" class="input is-info" id="password" name="password" required>
                <span class="icon is-small is-left">
                    <i class="fas fa-lock"></i>
                </span>
            </div>
        </div>
        <div class="field">
            <div class="buttons">
                <button type="submit" class="button login">Registrar</button>
                <a href="/login" class="button register">Volver al Login</a>
            </div>
        </div>
    </form>
</div>

</body>
</html>