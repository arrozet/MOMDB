<%@ page import="es.uma.taw.momdb.dto.MovieDTO" %>
<%@ page import="es.uma.taw.momdb.dto.CrewDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.momdb.dto.CharacterDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Manage Characters</title>
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
                <li class="is-active"><a>Characters</a></li>
                <li><a href="/editor/movie/crew?id=<%=movie.getId()%>">Crew</a></li>
                <li><a href="/editor/movie/reviews?id=<%=movie.getId()%>">Reviews</a></li>
            </ul>
        </div>

        <div class="table-container">
            <%
                if(movie.getEquipo() !=null){
            %>
            <table class="table is-fullwidth is-striped">
                <thead>
                <tr>
                    <th>Character Name</th>
                    <th>Actor</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <%
                    for (CrewDTO character : movie.getEquipo()) {
                        if(!character.getPersonajes().isEmpty()){%>
                <%for(CharacterDTO personaje: character.getPersonajes()){%>
                <tr>
                    <td><%= personaje.getCharacterName() %></td>
                    <td><%= character.getPersona() %></td>
                    <td>
                        <a href="/editor/movie/character/edit?id=<%=character.getId()%>&characterId=<%=personaje.getId()%>"
                           class="button is-small is-info">
                                                <span class="icon">
                                                    <i class="fas fa-edit"></i>
                                                </span>
                            <span>Edit</span>
                        </a>
                        <a href="/editor/movie/character/delete?characterId=<%=personaje.getId()%>&movieId=<%=movie.getId()%>"
                           class="button is-small is-danger">
                                                <span class="icon">
                                                    <i class="fas fa-trash"></i>
                                                </span>
                            <span>Delete</span>
                        </a>
                    </td>
                </tr>
                <%
                            } //for
                        }
                    }
                } else{
                %>
                There are no characters for this movie
                <%
                    }
                %>
                </tbody>
            </table>
        </div>

        <div class="mt-4">
            <a href="/editor/movie/character/new?movieId=<%=movie.getId()%>" class="button is-primary">
                <span class="icon">
                    <i class="fas fa-plus"></i>
                </span>
                <span>Add New Character</span>
            </a>
        </div>
    </div>
</div>

</body>
</html> 