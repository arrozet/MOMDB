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
    <link rel="stylesheet" href="/css/user.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<%
    MovieDTO movie = (MovieDTO) request.getAttribute("movie");
    CrewDTO crew = (CrewDTO) request.getAttribute("crew");
    CharacterDTO character = (CharacterDTO) request.getAttribute("character");
%>
<body class="has-background-white-ter">
<jsp:include page="cabecera_editor.jsp" />

<div class="section">
    <div class="container box">
        <h1 class="title is-4">Edit Character for: <%=movie.getTitulo()%></h1>

        <form:form modelAttribute="crew" action="/editor/movie/character/save" method="post">
            <form:hidden path="id" value="<%=crew.getId()%>"/>
            <form:hidden path="personajeId" value="<%=character.getId()%>"/>
            <div class="field">
                <label class="label">Character Name</label>
                <%=character.getCharacterName()%>
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
            </div>
        </form:form>
    </div>
</div>

</body>
</html> 