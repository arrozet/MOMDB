<%--
author: edugbau (Eduardo González - 92.9%), arrozet (Rubén Oliva - 7.1%)
--%>

<%@ page import="es.uma.taw.momdb.entity.Movie" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.momdb.dto.UserDTO" %>
<%@ page import="es.uma.taw.momdb.dto.MovieDTO" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
        <h2 class="title is-4 has-text-white mt-5">Available movies to analyze</h2>

        <form:form method="POST" action="${pageContext.request.contextPath}/analyst/filtrar" modelAttribute="filtro">
            <div class="columns is-vcentered is-mobile is-multiline">
                <div class="column is-3-desktop is-6-tablet is-12-mobile">
                    <div class="field">
                        <label class="label has-text-white">Search</label>
                        <div class="control">
                            <form:input path="texto" class="input is-info has-background-grey" placeholder="Movie title..."/>
                        </div>
                    </div>
                </div>
                <div class="column is-2-desktop is-3-tablet is-6-mobile">
                    <div class="field">
                        <label class="label has-text-white">Genre</label>
                        <div class="control is-expanded">
                            <form:select path="generoId" class="select is-info has-background-grey is-fullwidth">
                                <form:option value="" label="All"/>
                                <form:options items="${generos}" itemValue="id" itemLabel="genero"/>
                            </form:select>
                        </div>
                    </div>
                </div>
                <div class="column is-2-desktop is-3-tablet is-6-mobile">
                    <div class="field">
                        <label class="label has-text-white">Year</label>
                        <div class="control is-expanded">
                            <form:select path="year" class="select is-info has-background-grey is-fullwidth">
                                <form:option value="">All</form:option>
                                <form:option value="1960" label="> 1960"/>
                                <form:option value="1970" label="> 1970"/>
                                <form:option value="1980" label="> 1980"/>
                                <form:option value="1990" label="> 1990"/>
                                <form:option value="2000" label="> 2000"/>
                                <form:option value="2010" label="> 2010"/>
                            </form:select>
                        </div>
                    </div>
                </div>
                <div class="column is-2-desktop is-3-tablet is-6-mobile">
                    <div class="field">
                        <label class="label has-text-white">Rating</label>
                        <div class="control is-expanded">
                            <form:select path="rating" class="select is-info has-background-grey is-fullwidth">
                                <form:option value="">All</form:option>
                                <form:option value="0.0" label="> 0"/>
                                <form:option value="2.5" label="> 2.5"/>
                                <form:option value="5.0" label="> 5"/>
                                <form:option value="7.5" label="> 7.5"/>
                            </form:select>
                        </div>
                    </div>
                </div>
                <div class="column is-2-desktop is-12-tablet is-12-mobile">
                    <div class="field">
                        <label class="label has-text-white is-hidden-mobile">&nbsp;</label>
                        <div class="control">
                            <button type="submit" class="button is-info is-fullwidth">
                                <span class="icon"><i class="fas fa-search"></i></span>
                                <span>Filter</span>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </form:form>

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

        <%
            Integer currentPage = (Integer) request.getAttribute("currentPage");
            Integer totalPages = (Integer) request.getAttribute("totalPages");
            String baseUrl = "/analyst/";
        %>

        <% if (totalPages != null && totalPages > 1) { %>
        <nav class="pagination is-centered mt-5" role="navigation" aria-label="pagination">
            <%-- Botón Anterior --%>
            <% if (currentPage > 1) { %>
            <a href="<%= baseUrl %>?page=<%= currentPage - 1 %>" class="pagination-previous">Anterior</a>
            <% } else { %>
            <a class="pagination-previous" disabled>Anterior</a>
            <% } %>

            <%-- Botón Siguiente --%>
            <% if (currentPage < totalPages) { %>
            <a href="<%= baseUrl %>?page=<%= currentPage + 1 %>" class="pagination-next">Siguiente</a>
            <% } else { %>
            <a class="pagination-next" disabled>Siguiente</a>
            <% } %>

            <ul class="pagination-list">
                <li>
                    <span class="pagination-link is-current has-background-info has-text-white" aria-current="page">
                        Página <%= currentPage %> de <%= totalPages %>
                    </span>
                </li>
            </ul>
        </nav>
        <% } %>



    </div>
</section>

</body>
</html>
