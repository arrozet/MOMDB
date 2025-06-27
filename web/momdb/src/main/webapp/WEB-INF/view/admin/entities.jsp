<%--
author: arrozet (Rubén Oliva)
--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.momdb.dto.UserDTO" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Entity Management</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/admin.css">
    <link rel="stylesheet" href="/css/user.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<%
    UserDTO myUser = (UserDTO) session.getAttribute("user");
    List<String> everyEntity = (List<String>) request.getAttribute("everyEntity");
    List<?> entities = (List<?>) request.getAttribute("entities");
%>

<body class="page-background">
<jsp:include page="cabecera_admin.jsp" />

<section class="section">
    <div class="container">
        <h1 class="title admin-title">Entity Management</h1>
        
        <%-- Mostrar mensaje de error si existe --%>
        <% if (session.getAttribute("errorMessage") != null) { %>
            <div class="notification is-danger">
                <button class="delete" onclick="this.parentElement.remove();"></button>
                <strong>Error:</strong> <%= session.getAttribute("errorMessage") %>
                <% if (session.getAttribute("errorEntityType") != null && session.getAttribute("errorEntityName") != null) { %>
                    <br>
                    <small>Could not create <%= session.getAttribute("errorEntityType") %> with value "<%= session.getAttribute("errorEntityName") %>" because one with the same ID already exists.</small>
                <% } %>
            </div>
            <%-- Limpiar los atributos de error de la sesión --%>
            <% session.removeAttribute("errorMessage"); %>
            <% session.removeAttribute("errorEntityType"); %>
            <% session.removeAttribute("errorEntityName"); %>
        <% } %>
        
         <div class="box">
            <form:form method="post" action="/admin/showEntities" modelAttribute="genericEntity">
                <div class="field has-addons">
                    <div class="control">
                        <div class="select is-info">
                            <form:select path="selectedEntity" items="${everyEntity}" onchange="this.form.submit()" cssClass="user has-background-grey"/>
                        </div>
                    </div>
                    <div class="control is-expanded">
                        <form:input path="filterName" cssClass="user input is-info has-background-grey" placeholder="Filter by name"/>
                    </div>
                    <div class="control">
                        <button type="submit" class="button is-info">
                            <span class="icon">
                                <i class="fas fa-search"></i>
                            </span>
                        </button>
                    </div>
                    <div class="control">
                        <a href="/admin/addEntity?entityType=${genericEntity.selectedEntity}" class="button is-success">
                            <span class="icon">
                                <i class="fas fa-plus"></i>
                            </span>
                        </a>
                    </div>
                </div>
            </form:form>
            <table class="table is-striped is-fullwidth">
                <thead>
                    <tr>
                        <th class="has-text-grey-darker">Id</th>
                        <th class="has-text-grey-darker">Value</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                <%--
                    NOTA: Uso JSTL <c:forEach> en lugar de un bucle for de Java (<% ... %>) porque
                    la lista 'entities' es de tipo genérico (List<?>).
                    - Un scriptlet de Java fallaría en la compilación, ya que el compilador no puede
                      garantizar que los objetos de tipo '?' tengan los métodos .getId() o .getName().
                    - JSTL, en cambio, resuelve las propiedades como ${entity.id} en tiempo de ejecución,
                      llamando dinámicamente a los métodos getId() y getName(), lo que sí funciona.
                --%>
                <c:forEach var="entity" items="${entities}">
                    <tr>
                        <td>${entity.id}</td>
                        <td>${entity.name}</td>
                        <%-- Botones de editar y eliminar --%>
                        <td>
                            <%-- Botón de editar --%>
                            <div class="field has-addons">
                                <p class="control">
                                    <a class="button is-small is-info" href="/admin/editEntity?id=${entity.id}&entityType=${genericEntity.selectedEntity}">
                                        <span class="icon">
                                            <i class="fas fa-edit"></i>
                                        </span>
                                    </a>
                                </p>
                                <%-- Botón de eliminar --%>
                                <p class="control">
                                <form action="/admin/deleteEntity" method="post" style="display: inline;">
                                    <input type="hidden" name="id" value="${entity.id}">
                                    <input type="hidden" name="entityType" value="${genericEntity.selectedEntity}">
                                    <button type="submit" class="button is-small is-danger">
                                        <span class="icon">
                                            <i class="fas fa-trash"></i>
                                        </span>
                                    </button>
                                </form>
                                </p>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</section>
</body>
</html> 