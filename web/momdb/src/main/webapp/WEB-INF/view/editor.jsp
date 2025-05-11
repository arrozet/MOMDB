<%@ page import="es.uma.taw.momdb.entity.Movie" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: usuario
  Date: 11/05/2025
  Time: 20:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Editor</title>
</head>
<%
    List<Movie> movies = (List<Movie>) request.getAttribute("movies");
%>
<body>
<h1>Se vienen cositas</h1>
<table border="1">
    <tr>
        <th>Peliculas</th>
    </tr>
    <%
        for(Movie movie:movies){
    %>
    <tr>
        <td><%=movie.getOriginalTitle()%></td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
