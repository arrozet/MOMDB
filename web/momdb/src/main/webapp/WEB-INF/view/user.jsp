<%@ page import="es.uma.taw.momdb.entity.Movie" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.momdb.entity.User" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MOMDB</title>
</head>
<%
    User myUser = (User) session.getAttribute("user");
    // List<Movie> movies = (List<Movie>) request.getAttribute("movies");
%>
<body>
<h1>Welcome back, <%= myUser.getUsername() %>!<br></h1>

Se preparó, se puso linda su amiga llamaba


</body>
</html>