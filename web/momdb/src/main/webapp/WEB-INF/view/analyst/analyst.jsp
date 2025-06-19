<%--
  Created by IntelliJ IDEA.
  User: edugbau (Eduardo González)
  Date: 23/04/2025
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="es.uma.taw.momdb.entity.Movie" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.momdb.dto.UserDTO" %>
<%@ page import="es.uma.taw.momdb.dto.MovieDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Analyst | MOMDB</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/analyst.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<%
    UserDTO myUser = (UserDTO) session.getAttribute("user");
    List<MovieDTO> movies = (List<MovieDTO>) request.getAttribute("movies");
%>
<body class="page-background">
<jsp:include page="../analyst/cabecera_analyst.jsp" />
<jsp:include page="../banner.jsp" />

<section class="section">
    <div class="container">

        <h1>Welcome back, <%= myUser.getUsername() %>!<br></h1>

        <div class="dropdown mb-4" id="tools-dropdown">
            <div class="dropdown-trigger">
                <button class="button" aria-haspopup="true" aria-controls="dropdown-menu-tools">
                    <span>Tools</span>
                    <span class="icon is-small">
                        <i class="fas fa-angle-down" aria-hidden="true"></i>
                    </span>
                </button>
            </div>
            <div class="dropdown-menu" id="dropdown-menu-tools" role="menu">
                <div class="dropdown-content">
                    <a href="#" class="dropdown-item">Tool 1 (Coming Soon)</a>
                    <a href="#" class="dropdown-item">Tool 2 (Coming Soon)</a>
                    <a href="#" class="dropdown-item">Tool 3 (Coming Soon)</a>
                </div>
            </div>
        </div>

        <h2>Available movies to analyze</h2>
        <form method="POST" action="/analyst/filtrar">
            <input type="text" name="filter" placeholder="Search Movies">
            <input type="submit">
        </form>

        <div class="columns is-multiline mt-4" id="movies-container">
            <%

                if (movies != null && !movies.isEmpty()) {
                    for (MovieDTO movie : movies) {
            %>
            <div class="column is-6-mobile is-4-tablet is-3-desktop is-2-widescreen">
                <div class="movie-card">
                    <a href="/analyst/movie/<%= movie.getId() %>" class="movie-link">
                        <div class="movie-poster">
                            <% if (movie.getImageLink() != null && !movie.getImageLink().isEmpty()) { %>
                            <figure class="image is-2by3">
                                <img src="<%= movie.getImageLink() %>" alt="Póster de <%= movie.getTitulo() %>" class="rounded-corners">
                            </figure>
                            <% } else { %>
                            <span class="icon is-large has-text-info movie-poster-placeholder-icon">
                                <i class="fas fa-film fa-3x"></i>
                            </span>
                            <% } %>
                        </div>
                    </a>
                    <div class="analyst-movie-info">
                        <p class="title is-6 has-text-centered has-text-white"><%= movie.getTitulo()%></p>
                        <div class="content is-size-7 has-text-white">
                            <p><strong>ID:</strong> <%= movie.getId()%></p>
                            <p><strong>Release Date:</strong> <%= movie.getFechaDeSalida()%></p>
                            <p><strong>Vote Average:</strong> <%= movie.getMediaVotos()%></p>
                            <p><strong>Vote Count:</strong> <%= movie.getVotos()%></p>
                        </div>
                    </div>
                </div>
            </div>
            <%
                    }
                } else {
            %>
            <div class="column is-12">
                <div class="notification is-info is-light">
                    <p class="has-text-centered">No movies available to analyze.</p>
                </div>
            </div>
            <%
                }
            %>
        </div>



    </div>
</section>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        var dropdown = document.querySelector('#tools-dropdown');
        var dropdownTrigger = dropdown.querySelector('.dropdown-trigger');

        dropdownTrigger.addEventListener('click', function (event) {
            event.stopPropagation();
            dropdown.classList.toggle('is-active');
        });

        document.addEventListener('click', function(event) {
            // Close dropdown if click is outside
            if (!dropdown.contains(event.target)) {
                dropdown.classList.remove('is-active');
            }
        });
    });
</script>

</body>
</html>
