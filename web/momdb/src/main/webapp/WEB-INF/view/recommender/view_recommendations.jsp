<%--
  File: view_recommendations.jsp
  User: amcgiluma (Juan Manuel Valenzuela)
--%>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.momdb.dto.MovieDTO" %>
<%@ page import="es.uma.taw.momdb.dto.RecommendationDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Recommendations for <%= ((MovieDTO) request.getAttribute("originalMovie")).getTitulo() %> | MOMDB</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/user.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body class="page-background">

<%
    MovieDTO originalMovie = (MovieDTO) request.getAttribute("originalMovie");
    List<MovieDTO> genreRecommendedMovies = (List<MovieDTO>) request.getAttribute("genreRecommendedMovies");
    List<RecommendationDTO> userRecommendations = (List<RecommendationDTO>) request.getAttribute("userRecommendations");
%>

<section class="hero is-dark is-small page-banner">
    <div class="hero-body">
        <div class="container">
            <div class="columns is-vcentered">
                <div class="column">
                    <p class="title">Recommendations for: <%= originalMovie.getTitulo() %></p>
                </div>
                <div class="column is-narrow">
                    <div class="buttons">
                        <a href="/recommender/recommend/add?id=<%= originalMovie.getId() %>" class="button is-success">Add Recommendation</a>
                        <a href="/recommender/movie?id=<%= originalMovie.getId() %>" class="button is-warning">Back to Movie</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<section class="section">
    <div class="container">
        <h2 class="title is-4">Based on similar genres</h2>
        <div class="columns is-multiline is-mobile mt-4">
            <% if(genreRecommendedMovies != null && !genreRecommendedMovies.isEmpty()) {
                for (MovieDTO movie: genreRecommendedMovies) {
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
                </div>
            </div>
            <%
                }
            } else {
            %>
            <div class="column is-12">
                <div class="notification is-warning is-light">
                    <p class="has-text-centered">No genre recommendations found for this movie.</p>
                </div>
            </div>
            <% } %>
        </div>
    </div>
</section>

<section class="section pt-0">
    <div class="container">
        <h2 class="title is-4">Recommended by users</h2>
        <div class="columns is-multiline is-mobile mt-4">
            <% if(userRecommendations != null && !userRecommendations.isEmpty()) {
                for (RecommendationDTO recommendation : userRecommendations) {
                    MovieDTO movie = recommendation.getRecommendedMovie();
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
                    <div class="has-text-centered mt-2">
                        <p class="is-size-7">By: <strong><%= recommendation.getRecommender().getUsername() %></strong></p>
                    </div>
                </div>
            </div>
            <%
                }
            } else {
            %>
            <div class="column is-12">
                <div class="notification is-info is-light">
                    <p class="has-text-centered">No user recommendations for this movie yet. Be the first!</p>
                </div>
            </div>
            <% } %>
        </div>
    </div>
</section>

</body>
</html>