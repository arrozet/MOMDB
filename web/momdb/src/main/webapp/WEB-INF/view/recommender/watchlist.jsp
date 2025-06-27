<%--
author: amcgiluma (Juan Manuel Valenzuela)
--%>

<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.momdb.dto.MovieDTO" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Watch later | MOMDB</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/recommender.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body style="background-color: #5B3A7B;">
<jsp:include page="cabecera_recommender.jsp" />

<%
    List<MovieDTO> movies = (List<MovieDTO>) request.getAttribute("movies");
%>

<section class="hero is-small user-page-banner">
    <div class="hero-body">
        <div class="container has-text-centered">
            <h1 class="title">Your watchlist</h1>
        </div>
    </div>
</section>

<section class="section">
    <div class="container">
        <div class="columns is-multiline is-mobile mt-4">
            <% if(movies != null && !movies.isEmpty()) {
                for (MovieDTO movie: movies) {
            %>
            <div class="column is-6-mobile is-4-tablet is-3-desktop is-2-widescreen">
                <div class="movie-card">
                    <a href="/recommender/movie?id=<%= movie.getId() %>" class="movie-link">
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
                        <form method="POST" action="/recommender/watchlist/toggle" style="margin: 0; text-align: center;">
                            <input type="hidden" name="movieId" value="<%= movie.getId() %>">
                            <input type="hidden" name="action" value="remove">
                            <button type="submit" class="button is-warning is-small remove-watchlist-btn">
                                <span class="icon">
                                    <i class="far fa-bookmark"></i>
                                </span>
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
                <div class="notification is-info is-light">
                    <p class="has-text-centered">You don't have any movies in your watchlist yet. Start adding some!</p>
                    <div class="has-text-centered mt-3">
                        <a href="/recommender/" class="button is-info">
                            <span class="icon">
                                <i class="fas fa-film"></i>
                            </span>
                            <span>Browse movies</span>
                        </a>
                    </div>
                </div>
            </div>
            <% } %>
        </div>
    </div>
</section>

</body>
</html> 