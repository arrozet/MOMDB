<%--
author: arrozet (Rubén Oliva)
--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.taw.momdb.dto.GenericEntityDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    GenericEntityDTO entity = (GenericEntityDTO) request.getAttribute("entity");
    String entityType = (String) request.getAttribute("entityType");
    boolean isNew = entity.getId() == null || entity.getId().isEmpty();
%>

<html>
<head>
    <title><%= isNew ? "Add" : "Edit" %> Entity</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/admin.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body class="page-background">

<jsp:include page="cabecera_admin.jsp" />

<section class="section">
    <div class="container">
        <h1 class="title admin-title"><%= isNew ? "Add" : "Edit" %> <%= entityType %></h1>

        <%-- Mostrar mensaje de error si existe --%>
        <%
            if (session.getAttribute("entityErrorMessage") != null) {
        %>
        <div class="notification is-danger">
            <button class="delete" onclick="this.parentElement.remove();"></button>
            <strong>Error:</strong> <%= session.getAttribute("entityErrorMessage") %>
        </div>
        <%-- Limpiar el atributo de error de la sesión --%>
        <%
                session.removeAttribute("entityErrorMessage");
            }
        %>

        <div class="box">
            <form:form method="post" action='<%= isNew ? "/admin/createEntity" : "/admin/updateEntity" %>' modelAttribute="entity">
                <% if (!isNew) { %>
                    <form:hidden path="id" />
                <% } %>
                <input type="hidden" name="entityType" value="<%= entityType %>">

                <div class="field">
                    <label class="label" for="name">Value</label>
                    <div class="control">
                        <form:input path="name" cssClass="input" id="name" placeholder="Entity value..." />
                    </div>
                </div>

                <div class="field">
                    <div class="control">
                        <button type="submit" class="button is-primary">Save</button>
                        <a href="/admin/entities" class="button is-light">Cancel</a>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</section>

</body>
</html> 