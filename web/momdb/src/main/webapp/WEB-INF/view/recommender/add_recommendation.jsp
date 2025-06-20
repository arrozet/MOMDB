<%--
  Created by IntelliJ IDEA.
  User: amcgiluma (Juan Manuel Valenzuela)
  File: add_recommendation.jsp
--%>

<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.momdb.dto.MovieDTO" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Recommendation | MOMDB</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/user.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body class="page-background">
<jsp:include page="cabecera_recommender.jsp" />

<%
    MovieDTO originalMovie = (MovieDTO) request.getAttribute("originalMovie");
    List<MovieDTO> movies = (List<MovieDTO>) request.getAttribute("movies");
%>

<section class="hero is-light is-small page-banner">
    <div class="hero-body">
        <div class="container has-text-centered">
            <h1 class="title">Add Recommendation for: <%= originalMovie.getTitulo() %></h1>
            <h2 class="subtitle">Select a movie below to recommend it.</h2>
        </div>
    </div>
</section>

<section class="section">
    <div class="container">
        <%-- Sección de filtros adaptada para esta página --%>
        <div class="columns">
            <div class="column is-half is-offset-one-quarter">
                <form:form method="POST" action="/recommender/recommend/add/filtrar" modelAttribute="filtro">
                    <input type="hidden" name="originalMovieId" value="<%= originalMovie.getId() %>">
                    <div class="field has-addons">
                        <div class="control is-expanded">
                            <form:input path="texto" class="user input is-warning has-background-grey" placeholder="Search movies..."/>
                        </div>
                        <div class="control">
                            <form:button class="button is-warning">
                                <span class="icon"><i class="fas fa-search"></i></span>
                            </form:button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>

        <div class="is-mobile is-multiline is-vcentered" style="margin-bottom: 1.5rem;">
            <form:form method="POST" action="/recommender/recommend/add/filtrar" modelAttribute="filtro" class="column is-12">
                <input type="hidden" name="originalMovieId" value="<%= originalMovie.getId() %>">
                <div class="columns is-mobile is-vcentered is-flex is-align-items-flex-end">
                    <div class="column is-2">
                        <label class="label has-text-white">Genre</label>
                        <form:select path="generoId" class="user select is-warning has-background-grey is-fullwidth">
                            <form:option value="" label="All genres"/>
                            <form:options items="${generos}" itemValue="id" itemLabel="genero"/>
                        </form:select>
                    </div>
                    <div class="column is-2">
                        <label class="label has-text-white">Year</label>
                        <form:select path="year" class="user select is-warning has-background-grey is-fullwidth">
                            <form:option value="">All years</form:option>
                            <form:option value="1960" label="After 1960"/>
                            <form:option value="1990" label="After 1990"/>
                            <form:option value="2000" label="After 2000"/>
                            <form:option value="2010" label="After 2010"/>
                        </form:select>
                    </div>
                    <div class="column is-2">
                        <label class="label has-text-white">Rating</label>
                        <form:select path="rating" class="user select is-warning has-background-grey is-fullwidth">
                            <form:option value="">All ratings</form:option>
                            <form:option value="5.0" label="> 5"/>
                            <form:option value="7.5" label="> 7.5"/>
                        </form:select>
                    </div>
                    <div class="column is-4">
                        <form:button class="button is-warning is-fullwidth">
                            <span class="icon"><i class="fas fa-filter"></i></span>
                            <span>Filter</span>
                        </form:button>
                    </div>
                </div>
            </form:form>
        </div>

        <div class="columns is-multiline is-mobile mt-4">
            <% if(movies != null && !movies.isEmpty()) {
                for (MovieDTO movie: movies) {
                    if (movie.getId().equals(originalMovie.getId())) {
                        continue;
                    }
            %>
            <div class="column is-6-mobile is-4-tablet is-3-desktop is-2-widescreen">
                <div class="movie-card">
                    <a href="/recommender/movie?id=<%= movie.getId() %>" class="movie-link">
                        <div class="movie-poster">
                            <figure class="image is-2by3">
                                <img src="<%= movie.getImageLink() != null ? movie.getImageLink() : "/images/placeholder.png" %>" alt="Poster of <%= movie.getTitulo() %>" class="rounded-corners">
                            </figure>
                        </div>
                    </a>
                    <div class="movie-actions mt-2" style="display: flex; justify-content: center;">
                        <form method="POST" action="/recommender/recommend/save">
                            <input type="hidden" name="originalMovieId" value="<%= originalMovie.getId() %>">
                            <input type="hidden" name="recommendedMovieId" value="<%= movie.getId() %>">
                            <button type="submit" class="button is-success is-small">
                                <span class="icon"><i class="fas fa-plus"></i></span>
                                <span>Add</span>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
            <%
                }
            } else {
            %>
            <div class="column is-12">
                <div class="notification is-warning is-light">
                    <p class="has-text-centered">No movies found.</p>
                </div>
            </div>
            <% } %>
        </div>
    </div>
</section>

</body>
</html>