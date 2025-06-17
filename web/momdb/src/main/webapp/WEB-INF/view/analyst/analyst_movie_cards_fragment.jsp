<%--
Author: edugbau (Eduardo González)
--%>
<%@ page import="es.uma.taw.momdb.entity.Movie" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Movie> moviesFragment = (List<Movie>) request.getAttribute("moviesFragment"); // Nombre de atributo corregido
    if (moviesFragment != null && !moviesFragment.isEmpty()) {
        for (Movie movie : moviesFragment) {
%>
<div class="column is-6-mobile is-4-tablet is-3-desktop is-2-widescreen">
    <div class="card movie-card-analyst mb-4">
        <div class="card-image has-text-centered pt-3">
            <%
                String posterPath = movie.getPosterPath(); // Asumiendo que Movie entity tiene getPosterPath()
                String imageUrl = null;
                if (posterPath != null && !posterPath.isEmpty()) {
                    if (posterPath.startsWith("http")) { // Si ya es una URL completa
                        imageUrl = posterPath;
                    } else { // Si es solo una ruta, construir la URL completa
                        imageUrl = "https://image.tmdb.org/t/p/w185" + posterPath; // Ajustar tamaño si es necesario
                    }
                }
            %>
            <% if (imageUrl != null) { %>
            <figure class="image is-2by3" style="margin-left: auto; margin-right: auto; width: 150px;">
                <img src="<%= imageUrl %>" alt="Póster de <%= movie.getOriginalTitle() %>">
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
    }
%>
