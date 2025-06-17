<%@ page import="es.uma.taw.momdb.dto.MovieDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Manage Reviews</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/user.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<%
    MovieDTO movie =  (MovieDTO) request.getAttribute("movie");
%>
<body class="has-background-white-ter">
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
        <p>Review management interface coming soon.</p>
    </div>
</div>

</body>
</html> 