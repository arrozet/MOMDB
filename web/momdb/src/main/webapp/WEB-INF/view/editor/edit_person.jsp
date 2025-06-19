<%@ page import="es.uma.taw.momdb.dto.PersonDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.momdb.dto.MovieDTO" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Person</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/user.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<%
    PersonDTO person = (PersonDTO) request.getAttribute("person");
%>
<body class="has-background-white-ter">
<jsp:include page="cabecera_editor.jsp" />
<div class="container">

    <h1 class="title is-3 has-text-centered" style="margin-top:2.5rem;"><%= person.getId() == -1 ? "New Person" : "Edit Person" %></h1>

    <div class="section">
        <div class="container" style="max-width: 400px;">
            <form:form method="POST" action="/editor/person/save" modelAttribute="person">
                <form:hidden path="id" value="<%=person.getId()%>"/>
                <div class="field">
                    <label class="label">Name</label>
                    <div class="control">
                        <form:input path="name" class="input"/>
                    </div>
                </div>
                <div class="field is-grouped is-grouped-right">
                    <div class="control">
                        <button type="submit" class="button is-success">Save</button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>
<% if (person.getId() != -1) { %>
    <div class="section" style="display: flex; justify-content: center; gap: 3rem; flex-wrap: wrap;">
        <div style="min-width: 220px; text-align: center;">
            <h2 class="title is-5">Featured in:</h2>
            <ul style="list-style: none; padding: 0;">
                <% 
                    List<MovieDTO> actorMovies = (List<MovieDTO>) request.getAttribute("actorMovies");
                    if (actorMovies != null) {
                        for (MovieDTO movie : actorMovies) {
                %>
                            <li>
                                <a href="/editor/movie/characters?id=<%= movie.getId() %>"><%= movie.getTitulo() %></a>
                            </li>
                <%      }
                    }
                %>
            </ul>
        </div>
        <div style="min-width: 220px; text-align: center;">
            <h2 class="title is-5">Worked in:</h2>
            <ul style="list-style: none; padding: 0;">
                <% 
                    List<MovieDTO> crewMovies = (List<MovieDTO>) request.getAttribute("crewMovies");
                    if (crewMovies != null) {
                        for (MovieDTO movie : crewMovies) {
                %>
                            <li>
                                <a href="/editor/movie/crew?id=<%= movie.getId() %>"><%= movie.getTitulo() %></a>
                            </li>
                <%      }
                    }
                %>
            </ul>
        </div>
    </div>
<% } %>
</body>
</html> 