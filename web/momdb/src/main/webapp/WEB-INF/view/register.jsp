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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container">
<h1 class="mt-5">Registro de Nuevo Usuario</h1>

<c:if test="${not empty error}">
    <div class="alert alert-danger mt-3" role="alert">
            ${error}
    </div>
</c:if>

<form method="post" action="/register" class="mt-3">
    <div class="mb-3">
        <label for="username" class="form-label">Nombre de usuario:</label>
        <input type="text" class="form-control" id="username" name="username" required>
    </div>
    <div class="mb-3">
        <label for="email" class="form-label">Correo electrónico:</label>
        <input type="email" class="form-control" id="email" name="email" required>
    </div>
    <div class="mb-3">
        <label for="password" class="form-label">Contraseña:</label>
        <input type="password" class="form-control" id="password" name="password" required>
    </div>
    <button type="submit" class="btn btn-primary">Registrar</button>
    <a href="/login" class="btn btn-secondary">Volver al Login</a>
</form>

</body>
</html>