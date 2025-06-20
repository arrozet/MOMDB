<%@ page import="es.uma.taw.momdb.dto.MovieDTO" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Artur797 (Artur Vargas)
  Date: 16/06/2025
  Time: 12:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie Editor</title>
    <link rel="stylesheet" href="/css/common.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<%
    MovieDTO movie =  (MovieDTO) request.getAttribute("movie");
%>
<body class="page-background">
<jsp:include page="cabecera_editor.jsp" />

<div class="section">
    <div class="container box">
        <% if(movie.getId() != -1) { %>
        <h1 class="title is-4">Edit movie: <%=movie.getTitulo()%></h1>
            <div class="tabs is-centered">
            <ul>
                <li class="is-active"><a>General</a></li>
                <li><a href="/editor/movie/characters?id=<%=movie.getId()%>">Characters</a></li>
                <li><a href="/editor/movie/crew?id=<%=movie.getId()%>">Crew</a></li>
                <li><a href="/editor/movie/reviews?id=<%=movie.getId()%>">Reviews</a></li>
            </ul>
        </div>
        <% }else{ %>
        <h1 class="title is-4">Create movie</h1>
        <%}%>

        

        <form:form method="post" action="/editor/saveMovie" modelAttribute="movie">
            <form:hidden path="id"/>

            <div class="field">
                <label class="label">Title</label>
                <div class="control">
                    <form:input path="titulo" cssClass="input"/>
                </div>
            </div>

            <div class="field">
                <label class="label">Language</label>
                <div class="control">
                    <form:input path="idiomaOriginal" cssClass="input"/>
                </div>
            </div>

            <div class="field">
                <label class="label">Release date</label>
                <div class="control">
                    <form:input type="date" path="fechaDeSalida" cssClass="input"/>
                </div>
            </div>

            <div class="field">
                <label class="label">Revenues</label>
                <div class="control">
                    <form:input path="ingresos" cssClass="input"/>
                </div>
            </div>

            <div class="field">
                <label class="label">Genres</label>
                <div class="control">
                    <form:checkboxes path="generoIds" items="${generos}" itemValue="id" itemLabel="genero" cssClass="mr-3"/>
                </div>
            </div>

            <div class="field">
                <label class="label">Description</label>
                <div class="control">
                    <form:textarea path="descripcion" rows="8" cssClass="textarea"/>
                </div>
            </div>

            <div class="field">
                <label class="label">Image link</label>
                <div class="control">
                    <form:input path="imageLink" cssClass="input"/>
                </div>
            </div>

            <div class="field is-grouped mt-4">
                <div class="control">
                    <button class="button is-link">Save</button>
                </div>
            </div>
        </form:form>
    </div>
</div>

</body>
</html>