<%--
Author: projectGeorge (Jorge Repullo)
--%>

<%@ page import="es.uma.taw.momdb.entity.Movie" %>
<%@ page import="es.uma.taw.momdb.entity.Genre" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.momdb.dto.*" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%
    MovieDTO movie = (MovieDTO) request.getAttribute("movie");
    List<GenreDTO> generos = (List<GenreDTO>) request.getAttribute("generos");
%>
<head>
    <title>Movie Details | MOMDB</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/movie_details.css">
    <meta name="viewport" content="width=device-width, initial-scale=1"> <%-- Importante para responsive con Bulma --%>
</head>

<body class="has-background-white-ter">

<jsp:include page="cabecera_user.jsp" />

<section class="section">
    <div class="container">
        <div class="box has-background-grey">
            <div class="has-text-centered mb-5">
                <h1 class="title is-2"><%= movie.getTitulo() %></h1>
                <p class="is-italic is-size-6 has-text-weight-semibold" style="color:whitesmoke">
                    <%= movie.getTagline() != null ? movie.getTagline() : "No hay descripción disponible" %>
                </p>
            </div>

            <div class="content">
                <div class="columns">
                    <div class="column is-5 has-text-centered">
                        <div class="tags has-addons is-centered mt-3">
                            <span class="tag is-dark is-size-5">Rating</span>
                            <span class="tag is-info is-size-5">
                                <%= movie.getMediaVotos() != null ? movie.getMediaVotos() : "N/A" %>
                                <span class="icon is-small ml-3">
                                    <i class="fas fa-star"></i>
                                </span>
                            </span>
                        </div>

                        <div class="tags has-addons is-centered mt-3">
                            <span class="tag is-dark is-size-5">Votes</span>
                            <span class="tag is-info is-size-5">
                                <%= movie.getVotos() != null ? movie.getVotos() : "0" %>
                                <span class="icon is-small ml-3">
                                    <i class="fas fa-thumbs-up"></i>
                                </span>
                            </span>
                        </div>

                        <div class="tags has-addons is-centered mt-3">
                            <span class="tag is-dark is-size-5">Release date</span>
                            <span class="tag is-info is-size-5">
                                <span><%= movie.getFechaDeSalida() != null ? movie.getFechaDeSalida() : "No disponible" %></span>
                                <span class="icon">
                                    <i class="fas fa-calendar"></i>
                                </span>
                            </span>
                        </div>

                        <div class="tags has-addons is-centered mt-3">
                            <span class="tag is-dark is-size-5">Genres</span>
                            <span class="tag is-info is-size-5">
                                <span>
                                    <%
                                        for (int i = 0; i < generos.size(); i++) {
                                    %>
                                        <%= generos.get(i).getGenero() %><%= (i < generos.size() - 1) ? ", " : "" %>
                                    <%
                                        }
                                    %>
                                </span>
                                <span class="icon">
                                    <i class="fas fa-genre"></i>
                                </span>
                            </span>
                        </div>

                        <!-- Aquí se pueden añadir más detalles sobre la película según sea necesario -->
                    </div>

                    <div class="column is-7">
                        <div class="content">

                            <h3 class="title is-4">Overview</h3>
                            <div class="box has-background-dark" style="color: white">
                                <p><%= movie.getDescripcion() != null ? movie.getDescripcion() : "No hay descripción disponible" %></p>
                            </div>

                            <!-- Aquí se pueden añadir más detalles sobre la película según sea necesario -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="box has-background-grey">
            <div class="columns">
                <div class="column">
                    <h2 class="title is-4 mb-4">Main Characters</h2>
                    <% if (movie.getEquipo() != null && !movie.getEquipo().isEmpty()) {
                        boolean hasActors = false;
                        for (CrewDTO crew : movie.getEquipo()) {
                            if (crew.getRol().equals("Actor")) {
                                hasActors = true;
                                for (CharacterDTO character : crew.getPersonajes()) {
                    %>
                        <p class="mb-3">
                            <strong><%= character.getCharacterName() %></strong> - <%= crew.getPersona() %>
                        </p>
                    <%
                                }
                            }
                        }
                        if (!hasActors) {
                    %>
                        <p class="has-text-grey">No character information available</p>
                    <%
                        }
                    } else {
                    %>
                        <p class="has-text-grey">No character information available</p>
                    <% } %>
                </div>

                <div class="column">
                    <h2 class="title is-4 mb-4">Crew Members</h2>
                    <% if (movie.getEquipo() != null && !movie.getEquipo().isEmpty()) {
                        boolean hasCrew = false;
                        for (CrewDTO crew : movie.getEquipo()) {
                            if (!crew.getRol().equals("Actor")) {
                                hasCrew = true;
                    %>
                        <p class="mb-3">
                            <%= crew.getPersona() %> - <%= crew.getRol() %>
                        </p>
                    <%
                            }
                        }
                        if (!hasCrew) {
                    %>
                        <p class="has-text-grey">No crew information available</p>
                    <%
                        }
                    } else {
                    %>
                        <p class="has-text-grey">No crew information available</p>
                    <% } %>
                </div>
            </div>
        </div>
    </div>
</section>

</body>
</html>