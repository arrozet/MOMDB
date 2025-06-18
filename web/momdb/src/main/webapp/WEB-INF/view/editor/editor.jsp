<%--
  Created by IntelliJ IDEA.
  User: Artur797 (Artur Vargas)
  Date: 11/05/2025
  Time: 20:22
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.taw.momdb.entity.Movie" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.momdb.dto.MovieDTO" %>
<%@ page import="es.uma.taw.momdb.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Editor</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/user.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body class="has-background-white-ter">
<jsp:include page="cabecera_editor.jsp" />

<%
    List<MovieDTO> movies = (List<MovieDTO>) request.getAttribute("movies");
%>
<body>
<section class="hero is-light is-small"> <%-- Hero es básicamente para los títulos --%>
    <div class="hero-body">
        <div class="container has-text-centered">
            <h1 class="title">MOMDB</h1>
            <h2 class="subtitle">Where your fantasies come true</h2>
        </div>
    </div>
</section>

<section class="section">
    <div class="container">
        <div class="columns is-multiline">
            <div class="column is-10">
                <form:form method="POST" action="/editor/filtrar" modelAttribute="filtro">
                    <div class="field has-addons">
                        <div class="control is-expanded">
                            <form:input path="texto" class="input is-info has-background-grey" placeholder="Search movies..."/>
                        </div>
                        <div class="control">
                            <form:button class="button is-info">
                                <span class="icon">
                                    <i class="fas fa-search"></i>
                                </span>
                                <span>Filter</span>
                            </form:button>
                        </div>
                    </div>
                </form:form>
            </div>
            <div class="column is-2 has-text-right">
                <a href="/editor/newMovie" class="button is-success">
                    <span class="icon">
                        <i class="fas fa-plus"></i>
                    </span>
                    <span>New Movie</span>
                </a>
            </div>
        </div>

        <div class="columns is-multiline is-mobile mt-4">
            <% if(movies != null && !movies.isEmpty()) {
                for (MovieDTO movie: movies) {
            %>
            <div class="column is-6-mobile is-4-tablet is-3-desktop is-2-widescreen">
                <div class="card movie-card has-background-grey">
                    <div class="card-content">
                        <div class="content has-text-centered">
                            <a href="/editor/delete?id=<%= movie.getId() %>" class="has-text-danger" style="position: absolute; top: 0.1rem; right: 0.1rem; z-index: 10;">
                                <span class="icon">
                                    <i class="fas fa-trash"></i>
                                </span>
                            </a>
                            <a href="/editor/movie?id=<%= movie.getId() %>" class="movie-link">
                                <div class="movie-poster mb-2">
                                    <% if (movie.getImageLink() != null && !movie.getImageLink().isEmpty()) { %>
                                    <figure class="image is-2by3">
                                        <img src="<%= movie.getImageLink() %>" alt="Póster de <%= movie.getTitulo() %>">
                                    </figure>
                                    <% } else { %>
                                    <span class="icon is-large has-text-info">
                                        <i class="fas fa-film fa-3x"></i>
                                    </span>
                                    <% } %>
                                </div>
                                <p class="title is-5"><%= movie.getTitulo() %></p>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <%
                }
            } else {
            %>
            <div class="column is-12">
                <div class="notification is-info is-light">
                    <p class="has-text-centered">No movies available</p>
                </div>
            </div>
            <% } %>
        </div>
    </div>
</section>
</body>
</html>
