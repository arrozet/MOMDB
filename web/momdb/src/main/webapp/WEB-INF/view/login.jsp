<%--
  Created by IntelliJ IDEA.
  User: roz
  Date: 15/04/2025
  Time: 13:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome to MOMDB!</title>
    <link rel="stylesheet" href="/css/styles.css">
    <meta name="viewport" content="width=device-width, initial-scale=1"> <%-- Importante para responsive con Bulma --%>
</head>
<%
    String error = (String) request.getAttribute("error");
    if (error == null) {
        error = "";
    }
%>
<%--
<body>
<form method="post" action="/authenticate">
    <table>
        <tr>
            <td>Username:</td>
            <td><input type="text" name="username"/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="text" name="password"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Login"/></td>
        </tr>
    </table>
</form>
<p style="color: red"><%=error%></p>
</body>
--%>
<body class="login-page">
<div class="login-box">
    <h1 class="title has-text-centered">MOMDB Login</h1>

    <form method="post" action="/authenticate">
        <div class="field">
            <label class="label" for="username">Username</label>
            <div class="control has-icons-left">
                <input class="input" type="text" name="username" id="username" placeholder="e.g. jorge_user" required>
                <span class="icon is-small is-left">
                        <i class="fas fa-user"></i>
                </span>
            </div>
        </div>

        <div class="field">
            <label class="label" for="password">Password</label>
            <div class="control has-icons-left">
                <input class="input" type="password" name="password" id="password" placeholder="********" required>
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
            <div class="control">
                <button class="button is-primary is-fullwidth" type="submit">Login</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
