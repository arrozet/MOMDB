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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Analyst | MOMDB</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/analyst.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<%
    UserDTO myUser = (UserDTO) session.getAttribute("user");
    List<Movie> movies = (List<Movie>) request.getAttribute("movies");
%>
<body>
<jsp:include page="../cabecera_user.jsp" />

<section class="section">
    <div class="container">

        <h1>Welcome back, <%= myUser.getUsername() %>!<br></h1>

        <h2>Available movies to analyze</h2>
        <form method="POST" action="/analyst/filtrar">
            <input type="text" name="filter" placeholder="Search Movies">
            <input type="submit">
        </form>

        <div class="columns is-multiline mt-4" id="movies-container">
            <%
                List<Movie> moviesList = (List<Movie>) request.getAttribute("movies");
                if (moviesList != null && !moviesList.isEmpty()) {
                    for (Movie movie : moviesList) {
            %>
            <div class="column is-6-mobile is-4-tablet is-3-desktop is-2-widescreen">
                <div class="card movie-card-analyst mb-4">
                    <div class="card-image has-text-centered pt-3">
                        <% if (movie.getImageLink() != null && !movie.getImageLink().isEmpty()) { %>
                        <figure class="image is-2by3" style="margin-left: auto; margin-right: auto; width: 150px;">
                            <img src="<%= movie.getImageLink() %>" alt="Póster de <%= movie.getTitle() == null ? movie.getOriginalTitle() : movie.getTitle() %>">
                        </figure>
                        <% } else { %>
                        <span class="icon is-large has-text-info" style="padding: 40px 0;">
                            <i class="fas fa-film fa-3x"></i>
                        </span>
                        <% } %>
                    </div>
                    <div class="card-content">
                        <p class="title is-5 has-text-centered" style="min-height: 3.5em; margin-bottom: 0.5rem;"><%= movie.getOriginalTitle()%></p>
                        <div class="content is-size-7">
                            <p style="margin-bottom: 0.25rem;"><strong>ID:</strong> <%= movie.getId()%></p>
                            <p style="margin-bottom: 0.25rem;"><strong>Release Date:</strong> <%= movie.getReleaseDate()%></p>
                            <p style="margin-bottom: 0.25rem;"><strong>Vote Average:</strong> <%= movie.getVoteAverage()%></p>
                            <p style="margin-bottom: 0.25rem;"><strong>Vote Count:</strong> <%= movie.getVoteCount()%></p>
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



</body>
</html>
