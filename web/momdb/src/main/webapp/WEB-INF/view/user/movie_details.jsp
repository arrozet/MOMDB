<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: projectGeorge (Jorge Repullo)
  Date: 19/04/2025
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.momdb.dto.*" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Movie Details | MOMDB</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/movie_details.css">
    <meta name="viewport" content="width=device-width, initial-scale=1"> <%-- Importante para responsive con Bulma --%>
</head>

<body>

<jsp:include page="cabecera_user.jsp" />

<%
    MovieDTO movie = (MovieDTO) request.getAttribute("movie");
    List<GenreDTO> generos = (List<GenreDTO>) request.getAttribute("generos");
    List<ReviewDTO> reviews = (List<ReviewDTO>) request.getAttribute("reviews");
    UserDTO myUser = (UserDTO) session.getAttribute("user");
%>

<section class="section">
    <div class="container movie-details-container">
        <div class="movie-poster-column">
            <figure class="image is-3by4">
                <img class="rounded-corners" src="<%= movie.getImageLink() != null ? movie.getImageLink() : "/images/placeholder.png" %>" alt="Movie Poster">
            </figure>
            <div class="has-text-centered mt-4">
                <a href="/user/review/write?id=<%= movie.getId() %>" class="button is-info is-fullwidth">
                    Add review
                </a>
            </div>
        </div>

        <div class="movie-details-content">
            <div>
                <div class="has-text-centered mb-5">
                    <h1 class="title is-2 has-text-white"><%= movie.getTitulo() %></h1>
                    <p class="is-italic is-size-6 has-text-weight-semibold has-text-white-ter">
                        <%= movie.getTagline() != null ? movie.getTagline() : "No hay descripción disponible" %>
                    </p>
                </div>

                <div class="content">
                    <h3 class="title is-4 has-text-white">Overview</h3>
                    <div class="p-4 rounded-corners" style="color: white; background-color: #1c1226">
                        <p><%= movie.getDescripcion() != null ? movie.getDescripcion() : "No hay descripción disponible" %></p>
                    </div>

                    <div class="columns is-multiline mt-5">
                        <div class="column is-half">
                            <div class="tags has-addons">
                                <span class="tag is-size-5 movie-info-tag">Rating</span>
                                <span class="tag is-info is-size-5 has-text-white">
                                    <%= movie.getMediaVotos() != null ? movie.getMediaVotos() : "N/A" %>
                                    <span class="icon is-small ml-3">
                                        <i class="fas fa-star"></i>
                                    </span>
                                </span>
                            </div>
                        </div>
                        <div class="column is-half">
                            <div class="tags has-addons">
                                <span class="tag is-size-6 movie-info-tag" style="margin-right: 0.5rem;">Release date</span>
                                <span class="tag is-info is-size-5 has-text-white">
                                    <span><%= movie.getFechaDeSalida() != null ? movie.getFechaDeSalida() : "No disponible" %></span>
                                    <span class="icon is-small ml-3">
                                        <i class="fas fa-calendar"></i>
                                    </span>
                                </span>
                            </div>
                        </div>
                        <div class="column is-half">
                            <div class="tags has-addons">
                                <span class="tag is-size-5 movie-info-tag">Votes</span>
                                <span class="tag is-info is-size-5 has-text-white">
                                    <%= movie.getVotos() != null ? movie.getVotos() : "0" %>
                                    <span class="icon is-small ml-3">
                                        <i class="fas fa-thumbs-up"></i>
                                    </span>
                                </span>
                            </div>
                        </div>
                        <div class="column is-half">
                            <div class="tags has-addons">
                                <span class="tag is-size-5 movie-info-tag">Runtime</span>
                                <span class="tag is-info is-size-5 has-text-white">
                                    <span><%= movie.getDuracion() != null ? movie.getDuracion() + " min" : "No disponible" %></span>
                                    <span class="icon is-small ml-3">
                                        <i class="fas fa-clock"></i>
                                    </span>
                                </span>
                            </div>
                        </div>
                        <div class="column">
                            <div class="field is-grouped is-grouped-multiline">
                                <div class="control">
                                    <div class="tags has-addons">
                                        <span class="tag is-size-5 movie-info-tag">Genres</span>
                                        <span class="icon is-small ml-3">
                                            <i class="fas fa-tag"></i>
                                        </span>
                                    </div>
                                </div>
                                <% for (int i = 0; i < generos.size(); i++) {
                                %>
                                <div class="control">
                                    <span class="tag is-info is-size-5 has-text-white">
                                        <%= generos.get(i).getGenero() %>
                                    </span>
                                </div>
                                <%
                                } %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="movie-details-box">
                <h2 class="title is-4 mb-4 has-text-white">Main Characters</h2>
                <div class="columns is-multiline is-variable is-2">
                    <% if (movie.getEquipo() != null && !movie.getEquipo().isEmpty()) {
                        boolean hasActors = false;
                        for (CrewDTO crew : movie.getEquipo()) {
                            if (crew.getRol().equals("Actor")) {
                                hasActors = true;
                                for (CharacterDTO character : crew.getPersonajes()) {
                    %>
                        <div class="column is-one-third-desktop is-half-tablet is-full-mobile">
                            <p class="has-text-white"><strong><%= character.getCharacterName() %></strong> - <%= crew.getPersona() %></p>
                        </div>
                    <%
                                }
                            }
                        }
                        if (!hasActors) {
                    %>
                        <div class="column is-full"><p class="has-text-white">No character information available</p></div>
                    <%
                        }
                    } else {
                    %>
                        <div class="column is-full"><p class="has-text-white">No character information available</p></div>
                    <% } %>
                </div>
            </div>

            <div class="movie-details-box">
                <h2 class="title is-4 mb-4 has-text-white">Crew Members</h2>
                <div class="columns is-multiline is-variable is-2">
                    <% if (movie.getEquipo() != null && !movie.getEquipo().isEmpty()) {
                        boolean hasCrew = false;
                        for (CrewDTO crew : movie.getEquipo()) {
                            if (!crew.getRol().equals("Actor")) {
                                hasCrew = true;
                    %>
                        <div class="column is-one-quarter-desktop is-one-third-tablet is-half-mobile">
                            <p class="has-text-white"><%= crew.getPersona() %> - <strong><%= crew.getRol() %></strong></p>
                        </div>
                    <%
                            }
                        }
                        if (!hasCrew) {
                    %>
                        <div class="column is-full"><p class="has-text-white">No crew information available</p></div>
                    <%
                        }
                    } else {
                    %>
                        <div class="column is-full"><p class="has-text-white">No crew information available</p></div>
                    <% } %>
                </div>
            </div>
        </div>
    </div>
</section>

<section class="section">
    <div class="container">
        <h2 class="title is-4 has-text-white">Reviews</h2>
        <%
            if (reviews != null && !reviews.isEmpty()) {
                for (ReviewDTO review : reviews) {
        %>
            <div class="review-box">
                <p class="review-author">
                    Review by <%= review.getUsername() != null ? review.getUsername() : "Unknown" %>
                </p>
                <p class="review-rating">
                    ⭐ <%= review.getRating() != null ? review.getRating() : "-" %>/10
                </p>
                <p class="review-content">
                    <%= review.getContent() != null ? review.getContent() : "" %>
                </p>
                <% if (myUser != null && review.getUserId() != null && myUser.getUserId() == review.getUserId()) { %>
                    <div class="review-actions">
                        <a href="/user/review/write?id=<%= review.getMovieId() %>" title="Edit" class="button is-small is-info">
                            <span class="icon"><i class="fas fa-edit"></i></span>
                        </a>
                        <form action="/user/movie/review/delete" method="get" style="display:inline;">
                            <input type="hidden" name="movieId" value="<%= review.getMovieId() %>" />
                            <input type="hidden" name="userId" value="<%= review.getUserId() %>" />
                            <button type="submit" class="button is-small is-danger" title="Delete" onclick="return confirm('Are you sure you want to delete this review?');">
                                <span class="icon"><i class="fas fa-trash"></i></span>
                            </button>
                        </form>
                    </div>
                <% } %>
            </div>
        <%   }
            } else { %>
            <div class="review-section is-light has-text-centered">
                No reviews yet. Be the first to write one!
            </div>
        <% } %>
    </div>
</section>

</body>
</html>