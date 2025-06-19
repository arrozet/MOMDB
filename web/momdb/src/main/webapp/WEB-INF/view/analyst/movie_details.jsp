<%--Autor: Eduardo González--%>
<%@ page import="es.uma.taw.momdb.dto.UserDTO" %>
<%@ page import="es.uma.taw.momdb.entity.Movie" %>
<%@ page import="es.uma.taw.momdb.dto.MovieDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie Details | MOMDB</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/analyst.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/movie_details.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<%
    UserDTO myUser = (UserDTO) session.getAttribute("user");
    MovieDTO movie = (MovieDTO) request.getAttribute("movie");
%>
<body>
<jsp:include page="../user/cabecera_user.jsp" />

<section class="section">
    <div class="container">
        <% if (movie != null) { %>
        <div class="columns">
            <div class="column is-4">
                <figure class="image is-2by3">
                    <% if (movie.getImageLink() != null && !movie.getImageLink().isEmpty()) { %>
                    <img src="<%= movie.getImageLink() %>" alt="Póster de <%= movie.getTitulo()%>">
                    <% } else { %>
                    <span class="icon is-large has-text-info" style="padding: 40px 0; width: 100%; height: 100%; display: flex; align-items: center; justify-content: center; border: 1px solid #ccc;">
                        <i class="fas fa-film fa-6x"></i>
                    </span>
                    <% } %>
                </figure>
            </div>
            <div class="column is-8">
                <h1 class="title"><%= movie.getTitulo() %></h1><br>
                <% if (movie.getTitulo() != null && !movie.getTitulo().equals(movie.getIdiomaOriginal())) { %>
                <h2 class="subtitle"><%= movie.getTagline() %></h2>
                <% } %>
                <p><strong>Overview:</strong> <%= movie.getDescripcion() %></p>
                <hr>
                <p><strong>Release Date:</strong> <%= movie.getFechaDeSalida() %></p>
                <p><strong>Vote Average:</strong> <%= movie.getMediaVotos() %></p>
                <p><strong>Vote Count:</strong> <%= movie.getVotos() %></p>
                <p><strong>Revenue:</strong> $<%= String.format("%,d", movie.getIngresos()) %></p>
            </div>
        </div>
        <% } else { %>
        <div class="notification is-danger">
            <p>Movie not found.</p>
        </div>
        <% } %>
    </div>
</section>

</body>
</html>
