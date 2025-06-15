<%--
  Created by IntelliJ IDEA.
  User: edugbau (Eduardo González)
  Date: 23/04/2025
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="es.uma.taw.momdb.entity.Movie" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.momdb.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Analyst | MOMDB</title>
</head>
<%
    User myUser = (User) session.getAttribute("user");
    List<Movie> movies = (List<Movie>) request.getAttribute("movies");
%>
<body>
<h1>Welcome back, <%= myUser.getUsername() %>!<br></h1>

<h2>Available movies to analyze</h2>
<form method="POST" action="/analyst/filtrar">
    <input type="text" name="filter" placeholder="Search Movies">
    <input type="submit">
</form>
<table style="width:100%; text-align: center; vertical-align: middle;">
    <tr>
        <th>ID</th>
        <th>Original Title</th>
        <th>Release Date</th>
        <th>Vote Average</th>
        <th>Vote Count</th>
        <th></th>
    </tr>
    <%
        //TODO: Mostrar las películas
        for (Movie movie: movies){


    %>
    <tr>
        <td> <%= movie.getId()%></td>
        <td> <%= movie.getOriginalTitle()%></td>
        <td> <%= movie.getReleaseDate()%></td>
        <td> <%= movie.getVoteAverage()%></td>
        <td> <%= movie.getVoteCount()%></td>
    </tr>

    <%
        }
    %>
</table>


</body>
</html>
