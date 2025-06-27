<%--
author: Artur797 (Artur Vargas - 78.7%), arrozet (Rubén Oliva - 21.3%)
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
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body class="page-background">
<jsp:include page="cabecera_editor.jsp" />

<%
    List<MovieDTO> movies = (List<MovieDTO>) request.getAttribute("movies");
    Integer currentPage = (Integer) request.getAttribute("currentPage");
    Integer totalPages = (Integer) request.getAttribute("totalPages");
%>
<section class="hero is-light is-small page-banner"> <%-- Hero es básicamente para los títulos --%>
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
                <div class="movie-card">
                    <a href="/editor/delete?id=<%= movie.getId() %>" class="has-text-danger delete-movie-btn">
                        <span class="icon is-small">
                            <i class="fas fa-trash"></i>
                        </span>
                    </a>
                    <a href="/editor/movie?id=<%= movie.getId() %>" class="movie-link">
                        <div class="movie-poster">
                            <% if (movie.getImageLink() != null && !movie.getImageLink().isEmpty()) { %>
                            <figure class="image is-2by3">
                                <img src="<%= movie.getImageLink() %>" alt="Póster de <%= movie.getTitulo() %>" class="rounded-corners">
                            </figure>
                            <% } else { %>
                            <span class="icon is-large has-text-info">
                                <i class="fas fa-film fa-3x"></i>
                            </span>
                            <% } %>
                        </div>
                    </a>
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

        <% if (totalPages != null && totalPages > 1) { %>
        <nav class="pagination is-centered mt-5" role="navigation" aria-label="pagination">
            <%-- Previous Button --%>
            <% if (currentPage > 1) { %>
            <a href="/editor/?page=<%= currentPage - 1 %>" class="pagination-previous">Previous</a>
            <% } else { %>
            <a class="pagination-previous" disabled>Previous</a>
            <% } %>

            <%-- Next Button --%>
            <% if (currentPage < totalPages) { %>
            <a href="/editor/?page=<%= currentPage + 1 %>" class="pagination-next">Next</a>
            <% } else { %>
            <a class="pagination-next" disabled>Next</a>
            <% } %>

            <ul class="pagination-list">
                <li>
                    <span class="pagination-link is-current has-background-info has-text-white" aria-current="page">
                        Page <%= currentPage %> of <%= totalPages %>
                    </span>
                </li>
            </ul>
        </nav>
        <% } %>
    </div>
</section>
</body>
</html>
