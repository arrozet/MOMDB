<%--
Author: projectGeorge (Jorge Repullo)
--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.momdb.dto.MovieDTO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language=  "java" %>
<html>
<head>
    <title>My movies | MOMDB</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/user.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body class="page-background">
<jsp:include page="cabecera_user.jsp" />

<%
    List<MovieDTO> movies = (List<MovieDTO>) request.getAttribute("movies");
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
        <div class="columns">
            <div class="column is-half is-offset-one-quarter">
                <form:form method="POST" action="/user/filtrar" modelAttribute="filtro">
                    <div class="field has-addons">
                        <div class="control is-expanded">
                            <form:input path="texto" class="user input is-info has-background-grey" placeholder="Search movies..."/>
                        </div>
                        <div class="control">
                            <form:button class="button is-info">
                                <span class="icon">
                                    <i class="fas fa-search"></i>
                                </span>
                            </form:button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>

        <div class="is-mobile is-multiline is-vcentered" style="margin-bottom: 1.5rem;">
            <form:form method="POST" action="/user/filtrar" modelAttribute="filtro" class="column is-12">
                <div class="columns is-mobile is-vcentered is-flex is-align-items-flex-end">
                    <div class="column is-2">
                        <label class="label">Genre</label>
                        <form:select path="generoId" class="user select is-info has-background-grey is-fullwidth">
                            <form:option value="" label="All genres"/>
                            <form:options items="${generos}" itemValue="id" itemLabel="genero"/>
                        </form:select>
                    </div>
                    <div class="column is-2">
                        <label class="label">Year</label>
                        <form:select path="year" class="user select is-info has-background-grey is-fullwidth">
                            <form:option value="">All years</form:option>
                            <form:option value="1960" label="After 1960"/>
                            <form:option value="1970" label="After 1970"/>
                            <form:option value="1980" label="After 1980"/>
                            <form:option value="1990" label="After 1990"/>
                            <form:option value="2000" label="After 2000"/>
                            <form:option value="2010" label="After 2010"/>
                        </form:select>
                    </div>
                    <div class="column is-2">
                        <label class="label">Rating</label>
                        <form:select path="rating" class="user select is-info has-background-grey is-fullwidth">
                            <form:option value="">All ratings</form:option>
                            <form:option value="0.0" label="> 0"/>
                            <form:option value="2.5" label="> 2.5"/>
                            <form:option value="5.0" label="> 5"/>
                            <form:option value="7.5" label="> 7.5"/>
                        </form:select>
                    </div>
                    <div class="column is-2">
                        <label class="label">Popularity</label>
                        <form:select path="popularityRange" class="user select is-info has-background-grey is-fullwidth">
                            <form:option value="">Popularity</form:option>
                            <form:option value="0-10" label="0 - 10"/>
                            <form:option value="10-50" label="10 - 50"/>
                            <form:option value="50-200" label="50 - 200"/>
                            <form:option value="200-900" label="200 - 900"/>
                        </form:select>
                    </div>
                    <div class="column is-4">
                        <form:button class="button is-info is-fullwidth">
                                <span class="icon">
                                    <i class="fas fa-arrow-down"></i>
                                </span>
                                <span>Browse</span>
                        </form:button>
                    </div>
            </form:form>
        </div>

        <div class="columns is-multiline is-mobile mt-4">
            <% if(movies != null && !movies.isEmpty()) {
                for (MovieDTO movie: movies) {
            %>
            <div class="column is-6-mobile is-4-tablet is-3-desktop is-2-widescreen">
                <div class="movie-card">
                    <a href="/user/movie?id=<%= movie.getId() %>" class="movie-link">
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
                    <div class="movie-actions mt-2">
                        <% if (movie.isFavorite()) { %>
                            <form method="POST" action="/user/favorites/toggle">
                                <input type="hidden" name="movieId" value="<%= movie.getId() %>">
                                <input type="hidden" name="action" value="remove">
                                <button type="submit" class="button is-danger is-small favorite-btn">
                                    <span class="icon">
                                        <i class="fas fa-heart-broken"></i>
                                    </span>
                                </button>
                            </form>
                        <% } else { %>
                            <form method="POST" action="/user/favorites/toggle">
                                <input type="hidden" name="movieId" value="<%= movie.getId() %>">
                                <input type="hidden" name="action" value="add">
                                <button type="submit" class="button is-info is-small favorite-btn">
                                    <span class="icon">
                                        <i class="fas fa-heart"></i>
                                    </span>
                                </button>
                            </form>
                        <% } %>
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