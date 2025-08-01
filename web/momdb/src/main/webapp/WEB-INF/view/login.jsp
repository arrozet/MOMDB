<%--
author: arrozet (Rubén Oliva - 93.8%), amcgiluma (Juan Manuel Valenzuela - 6.2%)
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome to MOMDB!</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/login.css">
    <meta name="viewport" content="width=device-width, initial-scale=1"> <%-- Importante para responsive con Bulma --%>
</head>
<%
    String error = (String) request.getAttribute("error");
    if (error == null) {
        error = "";
    }
%>
<body class="login-page">
<jsp:include page="banner.jsp"/>
    <div class="login-box">
        <h1 class="title has-text-centered">Welcome back to MOMDB!</h1>

        <form method="post" action="/authenticate">
            <div class="field">
                <label class="label" for="username">Username</label>
                <div class="control has-icons-left">
                    <input class="input is-info" type="text" name="username" id="username" placeholder="e.g. jorge_user" required>
                    <span class="icon is-small is-left">
                            <i class="fas fa-user"></i>
                    </span>
                </div>
            </div>

            <div class="field">
                <label class="label" for="password">Password</label>
                <div class="control has-icons-left">
                    <input class="input is-info" type="password" name="password" id="password" placeholder="********" required>
                    <span class="icon is-small is-left">
                            <i class="fas fa-lock"></i>
                    </span>
                </div>
            </div>

            <% if (!error.isEmpty()) { %>
            <div class="notification is-danger is-light">
                <%= error %>
            </div>
            <% } %>

            <div class="field">
                <button class="button login is-fullwidth" type="submit">Login</button>
            </div>
            <div class="has-text-right">
                <small>No account yet? <a href="/register">Register now</a></small>
            </div>
        </form>
    </div>
</body>
</html>
