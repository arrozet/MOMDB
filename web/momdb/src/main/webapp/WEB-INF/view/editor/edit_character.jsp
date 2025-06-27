<%--
author: Artur797 (Artur Vargas)
--%>

<%@ page import="es.uma.taw.momdb.dto.MovieDTO" %>
<%@ page import="es.uma.taw.momdb.dto.CrewDTO" %>
<%@ page import="es.uma.taw.momdb.dto.CharacterDTO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Edit Character</title>
    <link rel="stylesheet" href="/css/common.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<%
    MovieDTO movie = (MovieDTO) request.getAttribute("movie");
    CrewDTO crew = (CrewDTO) request.getAttribute("crew");
    CharacterDTO character = (CharacterDTO) request.getAttribute("character");
%>
<body class="page-background">
<jsp:include page="cabecera_editor.jsp" />

<div class="section">
    <div class="container box">
        <h1 class="title is-4">Edit Character for: <%=movie.getTitulo()%></h1>

        <form:form modelAttribute="crew" action="/editor/movie/character/save" method="post">
            <form:hidden path="id" value="<%=crew.getId()%>"/>
            <form:hidden path="personajeId" value="<%=character.getId()%>"/>
            <form:hidden path="peliculaId" value="<%=crew.getPeliculaId()%>"/>
            <div class="field">
                <label class="label">Character Name</label>
                <div class="control">
                    <form:input path="personajeName" class="input" />
                </div>
            </div>

            <div class="field">
                <label class="label">Actor</label>
                <div class="control">
                    <div class="select is-fullwidth">
                        <form:select path="personaId" items="${people}" itemValue="id" itemLabel="name" />
                    </div>
                </div>
            </div>

            <div class="field is-grouped">
                <div class="control">
                    <button type="submit" class="button is-primary">Save Changes</button>
                </div>
                <div class="control">
                    <a href="/editor/movie/characters?id=<%=crew.getPeliculaId()%>" class="button is-light">Cancel</a>
                </div>
            </div>
        </form:form>
    </div>
</div>

</body>
</html> 