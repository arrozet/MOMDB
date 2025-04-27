<%@ page import="es.uma.taw.momdb.entity.Movie" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%
    Movie movie = (Movie) request.getAttribute("movie");
%>
<head>
    <title>Movie Details | MOMDB</title>
</head>

<body>

<jsp:include page="cabecera_user.jsp" />

<div class="container">
    <div class="movie-header">
        <h1 class="movie-title"><%= movie.getOriginalTitle() %></h1>
    </div>

    <div class="movie-info">
        <p><strong>Fecha de lanzamiento:</strong> <%= movie.getReleaseDate() != null ? movie.getReleaseDate() : "No disponible" %></p>
        <p><strong>Puntuación:</strong> <%= movie.getVoteAverage() != null ? movie.getVoteAverage() : "No disponible" %>
            (<%= movie.getVoteCount() != null ? movie.getVoteCount() : "0" %> votos)</p>
        <p><strong>Descripción:</strong></p>
        <p><%= movie.getOverview() != null ? movie.getOverview() : "No hay descripción disponible" %></p>
    </div>

    <div class="movie-actions">
        <a href="/user/" class="btn-volver">Volver</a>
    </div>
</div>

<style>
    .container {
        max-width: 800px;
        margin: 0 auto;
        padding: 20px;
        background-color: #f9f9f9;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }
    .movie-header {
        text-align: center;
        margin-bottom: 20px;
    }
    .movie-title {
        font-size: 28px;
        font-weight: bold;
        color: #333;
    }
    .movie-info p {
        margin: 10px 0;
        font-size: 16px;
        line-height: 1.5;
        color: #555;
    }
    .movie-info strong {
        color: #000;
    }

    .movie-actions {
        text-align: center;
        margin-top: 20px;
    }

    .btn-volver {
        display: inline-block;
        background-color: #007bff;
        color: white;
        padding: 10px 15px;
        text-decoration: none;
        border-radius: 4px;
        margin-top: 20px;
    }
    .btn-volver:hover {
        background-color: #0056b3;
    }
</style>
</body>
</html>