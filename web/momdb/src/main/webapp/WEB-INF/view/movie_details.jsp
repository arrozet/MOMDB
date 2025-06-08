<%@ page import="es.uma.taw.momdb.entity.Movie" %>
<%@ page import="es.uma.taw.momdb.dto.MovieDTO" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%
    MovieDTO movie = (MovieDTO) request.getAttribute("movie");
%>
<head>
    <title>Movie Details | MOMDB</title>
    <link rel="stylesheet" href="/css/common.css">
    <meta name="viewport" content="width=device-width, initial-scale=1"> <%-- Importante para responsive con Bulma --%>
</head>

<body class="has-background-white-ter">

<jsp:include page="cabecera_user.jsp" />

<section class="section">
    <div class="container">
        <div class="box has-background-grey">
            <div class="has-text-centered mb-5">
                <h1 class="title is-2"><%= movie.getTitulo() %></h1>
            </div>

            <div class="content">
                <div class="columns">
                    <div class="column is-4 has-text-centered">
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
                    </div>

                    <div class="column is-8">
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

            <div class="has-text-centered mt-5">
                <a href="/user/" class="button is-info">
                    <span class="icon">
                        <i class="fas fa-arrow-left"></i>
                    </span>
                    <span>Volver</span>
                </a>
            </div>
        </div>
    </div>
</section>

</body>
</html>