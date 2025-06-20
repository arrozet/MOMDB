<%@ page import="es.uma.taw.momdb.dto.MovieDTO" %>
<%@ page import="es.uma.taw.momdb.dto.CrewDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Manage Crew</title>
    <link rel="stylesheet" href="/css/common.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<%
    MovieDTO movie =  (MovieDTO) request.getAttribute("movie");
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
                <li class="is-active"><a>Crew</a></li>
                <li><a href="/editor/movie/reviews?id=<%=movie.getId()%>">Reviews</a></li>
            </ul>
        </div>

        <div class="table-container">
            <%
                if(movie.getEquipo() != null){
            %>
            <table class="table is-fullwidth is-striped">
                <thead>
                <tr>
                    <th>Role</th>
                    <th>Name</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <%
                    for (CrewDTO crew : movie.getEquipo()) {
                        if(!"Actor".equals(crew.getRol())){ %>
                <tr>
                    <td><%= crew.getRol() %></td>
                    <td><%= crew.getPersona() %></td>
                    <td>
                        <a href="/editor/movie/crew/edit?crewId=<%=crew.getId()%>&movieId=<%=movie.getId()%>"
                           class="button is-small is-info">
                            <span class="icon">
                                <i class="fas fa-edit"></i>
                            </span>
                            <span>Edit</span>
                        </a>
                        <a href="/editor/movie/crew/delete?crewId=<%=crew.getId()%>&movieId=<%=movie.getId()%>"
                           class="button is-small is-danger">
                            <span class="icon">
                                <i class="fas fa-trash"></i>
                            </span>
                            <span>Delete</span>
                        </a>
                    </td>
                </tr>
                <%  }
                    }
                } else{
                %>
                There is no crew members for this movie
                <%
                    }
                %>
                </tbody>
            </table>
        </div>

        <div class="mt-4">
            <a href="/editor/movie/crew/new?movieId=<%=movie.getId()%>" class="button is-primary">
                <span class="icon">
                    <i class="fas fa-plus"></i>
                </span>
                <span>Add New Crew Member</span>
            </a>
        </div>
    </div>
</div>

</body>
</html> 