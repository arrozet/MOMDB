<%@ page import="es.uma.taw.momdb.entity.Movie" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.momdb.entity.User" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My movies | MOMDB</title>
</head>
<%
    User myUser = (User) session.getAttribute("user");
    List<Movie> movies = (List<Movie>) request.getAttribute("movies");
%>
<body>

<jsp:include page="cabecera_user.jsp" />

<h1 style="justify-content: center">MOMDB<br>Where your fantasies come true</h1>

<form method="POST" action="/user/filtrar">
    <input type="text" name="filter" placeholder="Search...">
    <input type="submit" value="Filtrar">
</form>

<div class="movie-grid">
    <% if(movies != null) {
        for (Movie movie: movies) {
    %>
    <div class="movie-card">
        <a href="/user/movie?id=<%= movie.getId() %>" class="movie-link">
            <div class="movie-title"><%= movie.getOriginalTitle() %></div>
        </a>
    </div>
    <%
        }
    } else {
    %>
    <div class="no-movies">No hay películas disponibles</div>
    <% } %>
</div>

<style>
    h1 {
        text-align: center;
        margin: 20px 0;
        font-size: 28px;
    }

    .movie-grid {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
        gap: 20px;
        padding: 20px;
    }

    .movie-card {
        text-align: center;
    }

    .movie-title {
        margin-top: 8px;
        font-size: 18px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    .movie-link {
        text-decoration: none;
        color: inherit;
    }

    .movie-link:hover .movie-title {
        color: #007bff;
        text-decoration: underline;
    }
</style>

</body>
</html>