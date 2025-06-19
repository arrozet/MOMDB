<%@ page import="es.uma.taw.momdb.dto.MovieDTO" %>
<%@ page import="es.uma.taw.momdb.dto.CrewDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Edit crew member</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/user.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<%
    MovieDTO movie = (MovieDTO) request.getAttribute("movie");
    CrewDTO crew = (CrewDTO) request.getAttribute("crew");
    String error = (String) request.getAttribute("error");
%>
<body class="has-background-white-ter">
<jsp:include page="cabecera_editor.jsp" />

<div class="section">
    <div class="container box">
        <h1 class="title is-4">Edit crew member for: <%=movie.getTitulo()%></h1>
        

        <% if(error != null) { %>
            <div class="notification is-danger is-light">
                <%=error%>
            </div>
        <% } %>

        <form:form method="post" modelAttribute="crew" action="/editor/movie/crew/save">
            <form:hidden path="id" />
            <form:hidden path="peliculaId" />
            <div class="field">
                <label class="label">Role</label>
                <div class="control">
                    <div class="select is-fullwidth">
                        <form:select path="rolId" items="${roles}" itemValue="id" itemLabel="role" />
                    </div>
                </div>
            </div>
            <div class="field">
                <label class="label">Person</label>
                <div class="control">
                    <div class="select is-fullwidth">
                        <form:select path="personaId" items="${people}" itemValue="id" itemLabel="name" />
                    </div>
                </div>
            </div>
            <div class="field is-grouped">
                <div class="control">
                    <button type="submit" class="button is-primary">Guardar</button>
                </div>
                <div class="control">
                    <a href="/editor/movie/crew?id=${crew.peliculaId}" class="button is-light">Cancelar</a>
                </div>
            </div>
        </form:form>
    </div>
</div>

</body>
</html> 