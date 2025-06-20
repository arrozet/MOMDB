<%@ page import="es.uma.taw.momdb.dto.MovieDTO" %>
<%@ page import="es.uma.taw.momdb.dto.ReviewDTO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Manage Reviews</title>
    <link rel="stylesheet" href="/css/common.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<%
    MovieDTO movie = (MovieDTO) request.getAttribute("movie");
    List<ReviewDTO> reviews = (List<ReviewDTO>) request.getAttribute("reviews");
%>
<body class="page-background">
<jsp:include page="cabecera_editor.jsp" />

<div class="section">
    <div class="container box">
        <h1 class="title is-4">Edit movie: <%=movie.getTitulo()%></h1>

        <div class="tabs is-centered">
            <ul>
                <li><a href="/editor/movie?id=<%=movie.getId()%>">General</a></li>
                <li><a href="/editor/movie/characters?id=<%=movie.getId()%>">Characters</a></li>
                <li><a href="/editor/movie/crew?id=<%=movie.getId()%>">Crew</a></li>
                <li class="is-active"><a>Reviews</a></li>
            </ul>
        </div>

        <h2 class="title is-5">Reviews</h2>

        <% if (reviews != null && !reviews.isEmpty()) { %>
            <div class="table-container">
                <table class="table is-fullwidth is-striped">
                    <thead>
                        <tr>
                            <th>User</th>
                            <th>Rating</th>
                            <th>Content</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (ReviewDTO review : reviews) { %>
                            <tr>
                                <td><%= review.getUsername() %></td>
                                <td><%= review.getRating() %></td>
                                <td><%= review.getContent() %></td>
                                <td>
                                    <a href="/editor/movie/review/delete?movieId=<%=review.getMovieId()%>&userId=<%=review.getUserId()%>"
                                       class="button is-small is-danger">
                                        <span class="icon">
                                            <i class="fas fa-trash"></i>
                                        </span>
                                        <span>Delete</span>
                                    </a>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        <% } else { %>
            <p>No reviews found for this movie.</p>
        <% } %>
    </div>
</div>

</body>
</html> 