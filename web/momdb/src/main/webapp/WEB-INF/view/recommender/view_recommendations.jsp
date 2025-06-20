<%--
  File: view_recommendations.jsp
  User: amcgiluma (Juan Manuel Valenzuela)
--%>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.momdb.dto.MovieDTO" %>
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
    List<MovieDTO> recommendedMovies = (List<MovieDTO>) request.getAttribute("recommendedMovies");
%>

<section class="hero is-dark is-small page-banner">
    <div class="hero-body">
        <div class="container">
            <div class="columns is-vcentered">
                <div class="column">
                    <p class="title">Recommendations for: <%= originalMovie.getTitulo() %></p>
                    <p class="subtitle">Based on similar genres.</p>
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
        <div class="columns is-multiline is-mobile mt-4">
            <% if(recommendedMovies != null && !recommendedMovies.isEmpty()) {
                for (MovieDTO movie: recommendedMovies) {
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
                    <p class="has-text-centered">No recommendations found for this movie.</p>
                </div>
            </div>
            <% } %>
        </div>
    </div>
</section>

</body>
</html>