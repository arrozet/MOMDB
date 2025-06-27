<%--
author: Artur797 (Artur Vargas)
--%>

<%@ page import="es.uma.taw.momdb.dto.MovieDTO" %>
<%@ page import="es.uma.taw.momdb.dto.CrewDTO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Add Character</title>
    <link rel="stylesheet" href="/css/common.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<%
    MovieDTO movie = (MovieDTO) request.getAttribute("movie");
    CrewDTO crew = (CrewDTO) request.getAttribute("crew");
%>
<body class="page-background">
<jsp:include page="cabecera_editor.jsp" />

<div class="section">
    <div class="container box">
        <h1 class="title is-4">Add Character for: <%=movie.getTitulo()%></h1>

        <form:form modelAttribute="crew" action="/editor/movie/character/add" method="post">
            <form:hidden path="peliculaId" value="<%=movie.getId()%>"/>
            
            <div class="field">
                <label class="label">Character Name</label>
                <div class="control">
                    <form:input path="personajeName" cssClass="input" required="required"/>
                </div>
            </div>

            <div class="field">
                <label class="label">Actor</label>
                <div class="control">
                    <div class="select is-fullwidth">
                        <form:select path="personaId" items="${people}" itemValue="id" itemLabel="name" required="required"/>
                    </div>
                </div>
            </div>

            <div class="field is-grouped">
                <div class="control">
                    <button type="submit" class="button is-primary">Add Character</button>
                </div>
            </div>
        </form:form>
    </div>
</div>

</body>
</html> 