<%--
author: Artur797 (Artur Vargas)
--%>

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
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<%
    PersonDTO person = (PersonDTO) request.getAttribute("person");
%>
<body class="page-background">
<jsp:include page="cabecera_editor.jsp" />
<div class="section">
    <div class="container">
        <div class="box">
            <h1 class="title is-3 has-text-centered"><%= person.getId() == -1 ? "New Person" : "Edit Person" %></h1>
            <div class="columns is-centered">
                <div class="column is-half">
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

            <% if (person.getId() != -1) { %>
            <hr>
            <div class="columns mt-4">
                <div class="column has-text-centered">
                    <h2 class="title is-5">Featured in:</h2>
                    <ul style="list-style: none; padding: 0;">
                        <%
                            List<MovieDTO> actorMovies = (List<MovieDTO>) request.getAttribute("actorMovies");
                            if (actorMovies != null && !actorMovies.isEmpty()) {
                                for (MovieDTO movie : actorMovies) {
                        %>
                        <li>
                            <a href="/editor/movie/characters?id=<%= movie.getId() %>"><%= movie.getTitulo() %></a>
                        </li>
                        <%      }
                            } else { %>
                        <li><p>None</p></li>
                        <% }
                        %>
                    </ul>
                </div>
                <div class="column has-text-centered">
                    <h2 class="title is-5">Worked in:</h2>
                    <ul style="list-style: none; padding: 0;">
                        <%
                            List<MovieDTO> crewMovies = (List<MovieDTO>) request.getAttribute("crewMovies");
                            if (crewMovies != null && !crewMovies.isEmpty()) {
                                for (MovieDTO movie : crewMovies) {
                        %>
                        <li>
                            <a href="/editor/movie/crew?id=<%= movie.getId() %>"><%= movie.getTitulo() %></a>
                        </li>
                        <%      }
                            } else { %>
                        <li><p>None</p></li>
                        <% }
                        %>
                    </ul>
                </div>
            </div>
            <% } %>
        </div>
    </div>
</div>
</body>
</html> 