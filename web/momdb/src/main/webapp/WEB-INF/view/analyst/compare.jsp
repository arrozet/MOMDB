<%--
author: edugbau (Eduardo González)
--%>

<%@ page import="es.uma.taw.momdb.dto.UserDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.momdb.dto.MovieDTO" %>
<%@ page import="es.uma.taw.momdb.dto.PersonDTO" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Compare Movies | MOMDB</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/analyst.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<%
    UserDTO myUser = (UserDTO) session.getAttribute("user");
    List<MovieDTO> movies = (List<MovieDTO>) request.getAttribute("movies");
    MovieDTO movie1 = (MovieDTO) request.getAttribute("movie1");
    MovieDTO movie2 = (MovieDTO) request.getAttribute("movie2");
    Integer currentPage = (Integer) request.getAttribute("currentPage");
    Integer totalPages = (Integer) request.getAttribute("totalPages");
    List<PersonDTO> sharedCast = (List<PersonDTO>) request.getAttribute("sharedCast");
    List<PersonDTO> sharedCrew = (List<PersonDTO>) request.getAttribute("sharedCrew");
%>
<body class="page-background">
<jsp:include page="../analyst/cabecera_analyst.jsp"/>
<jsp:include page="../banner.jsp"/>

<section class="section">
    <div class="container">
        <h1 class="title has-text-white">Compare Movies</h1>

        <%-- Comparison Result Section --%>
        <% if (movie1 != null && movie2 != null) { %>
        <div class="columns mt-5">
            <div class="column">
                <div class="card">
                    <div class="card-header">
                        <p class="card-header-title"><%= movie1.getTitulo() %></p>
                    </div>
                    <div class="card-content">
                        <p><strong>Release Date:</strong> <%= movie1.getFechaDeSalida() %></p>
                        <p><strong>Popularity:</strong> <%= movie1.getPopularity() %></p>
                        <p><strong>Revenue:</strong> <%= movie1.getIngresos() %></p>
                        <p><strong>Vote Average:</strong> <%= movie1.getMediaVotos() %></p>
                        <p><strong>Vote Count:</strong> <%= movie1.getVotos() %></p>
                        <p><strong>Runtime:</strong> <%= movie1.getDuracion() %> min</p>
                    </div>
                </div>
            </div>
            <div class="column">
                <div class="card">
                    <div class="card-header">
                        <p class="card-header-title"><%= movie2.getTitulo() %></p>
                    </div>
                    <div class="card-content">
                        <p><strong>Release Date:</strong> <%= movie2.getFechaDeSalida() %></p>
                        <p><strong>Popularity:</strong> <%= movie2.getPopularity() %></p>
                        <p><strong>Revenue:</strong> <%= movie2.getIngresos() %></p>
                        <p><strong>Vote Average:</strong> <%= movie2.getMediaVotos() %></p>
                        <p><strong>Vote Count:</strong> <%= movie2.getVotos() %></p>
                        <p><strong>Runtime:</strong> <%= movie2.getDuracion() %> min</p>
                    </div>
                </div>
            </div>
        </div>

        <div class="columns mt-5">
            <div class="column">
                <div class="card">
                    <div class="card-header">
                        <p class="card-header-title">Shared Cast</p>
                    </div>
                    <div class="card-content">
                        <% if (sharedCast != null && !sharedCast.isEmpty()) { %>
                        <ul>
                            <% for (PersonDTO person : sharedCast) { %>
                            <li><%= person.getName() %></li>
                            <% } %>
                        </ul>
                        <% } else { %>
                        <p>No shared cast members found.</p>
                        <% } %>
                    </div>
                </div>
            </div>
            <div class="column">
                <div class="card">
                    <div class="card-header">
                        <p class="card-header-title">Shared Crew</p>
                    </div>
                    <div class="card-content">
                        <% if (sharedCrew != null && !sharedCrew.isEmpty()) { %>
                        <ul>
                            <% for (PersonDTO person : sharedCrew) { %>
                            <li><%= person.getName() %></li>
                            <% } %>
                        </ul>
                        <% } else { %>
                        <p>No shared crew members found.</p>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>
        <% } %>

        <h2 class="title is-4 has-text-white mt-5">Select two movies to compare</h2>

        <form:form method="POST" action="${pageContext.request.contextPath}/analyst/compare/filtrar" modelAttribute="filtro">
            <input type="hidden" name="movieId1" id="filter-form-movieId1" value="<%= movie1 != null ? movie1.getId() : "" %>">
            <input type="hidden" name="movieId2" id="filter-form-movieId2" value="<%= movie2 != null ? movie2.getId() : "" %>">
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
                            <div class="field is-grouped">
                                <p class="control">
                                    <button type="submit" class="button is-info">
                                        <span class="icon"><i class="fas fa-search"></i></span>
                                        <span>Filter</span>
                                    </button>
                                </p>
                                <p class="control">
                                    <button type="button" id="clear-selection-button" class="button is-danger">
                                        <span class="icon"><i class="fas fa-times"></i></span>
                                        <span>Clear</span>
                                    </button>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form:form>

        <%-- Comparison Form and Button --%>
        <form method="POST" action="${pageContext.request.contextPath}/analyst/compare?page=<%= currentPage %>" id="compare-form">
            <input type="hidden" name="movieId1" id="movieId1">
            <input type="hidden" name="movieId2" id="movieId2">
            <button type="submit" class="button is-info" id="compare-button" disabled>Compare Selected Movies</button>
        </form>

        <%-- Movie Grid --%>
        <div class="columns is-multiline mt-4" id="movies-container">
            <%
                if (movies != null && !movies.isEmpty()) {
                    for (MovieDTO movie : movies) {
            %>
            <div class="column is-6-mobile is-4-tablet is-3-desktop is-2-widescreen">
                <div class="movie-card" data-movie-id="<%= movie.getId() %>">
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

        <%-- Pagination --%>
        <%
            String baseUrl = request.getContextPath() + "/analyst/compare";
        %>
        <% if (totalPages != null && totalPages > 1) { %>
        <nav class="pagination is-centered mt-5" role="navigation" aria-label="pagination">
            <%-- Previous Button --%>
            <% if (currentPage > 1) { %>
            <a href="<%= baseUrl %>?page=<%= currentPage - 1 %>" class="pagination-previous">Previous</a>
            <% } else { %>
            <a class="pagination-previous" disabled>Previous</a>
            <% } %>

            <%-- Next Button --%>
            <% if (currentPage < totalPages) { %>
            <a href="<%= baseUrl %>?page=<%= currentPage + 1 %>" class="pagination-next">Next page</a>
            <% } else { %>
            <a class="pagination-next" disabled>Next page</a>
            <% } %>

            <ul class="pagination-list">
                <li>
                    <span class="pagination-link is-current has-background-info has-text-white" aria-current="page">
                        Page <%= currentPage %> of <%= totalPages %>
                    </span>
                </li>
            </ul>
        </nav>
        <% } %>

    </div>
</section>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const movieCards = document.querySelectorAll('.movie-card');
        const movieId1Input = document.getElementById('movieId1');
        const movieId2Input = document.getElementById('movieId2');
        const filterMovieId1Input = document.getElementById('filter-form-movieId1');
        const filterMovieId2Input = document.getElementById('filter-form-movieId2');
        const compareButton = document.getElementById('compare-button');
        const paginationLinks = document.querySelectorAll('a.pagination-previous, a.pagination-next');
        const clearSelectionButton = document.getElementById('clear-selection-button');

        const urlParams = new URLSearchParams(window.location.search);
        let selectedMovies = [];
        if (urlParams.get('movieId1')) {
            selectedMovies.push(urlParams.get('movieId1'));
        }
        if (urlParams.get('movieId2')) {
            selectedMovies.push(urlParams.get('movieId2'));
        }

        function updateState() {
            const movie1Id = selectedMovies.length > 0 ? selectedMovies[0] : '';
            const movie2Id = selectedMovies.length > 1 ? selectedMovies[1] : '';

            movieId1Input.value = movie1Id;
            movieId2Input.value = movie2Id;
            if (filterMovieId1Input) {
                filterMovieId1Input.value = movie1Id;
            }
            if (filterMovieId2Input) {
                filterMovieId2Input.value = movie2Id;
            }

            compareButton.disabled = selectedMovies.length !== 2;

            movieCards.forEach(card => {
                const movieId = card.dataset.movieId;
                if (selectedMovies.includes(movieId)) {
                    card.classList.add('is-selected');
                } else {
                    card.classList.remove('is-selected');
                }
            });
            updatePaginationLinks();
        }

        function updatePaginationLinks() {
            paginationLinks.forEach(link => {
                const href = link.getAttribute('href');
                if (href) {
                    const url = new URL(href, window.location.origin);

                    if (selectedMovies.length > 0) {
                        url.searchParams.set('movieId1', selectedMovies[0]);
                    } else {
                        url.searchParams.delete('movieId1');
                    }

                    if (selectedMovies.length > 1) {
                        url.searchParams.set('movieId2', selectedMovies[1]);
                    } else {
                        url.searchParams.delete('movieId2');
                    }

                    link.setAttribute('href', url.pathname + url.search);
                }
            });
        }

        movieCards.forEach(card => {
            card.addEventListener('click', () => {
                const movieId = card.dataset.movieId;
                const index = selectedMovies.indexOf(movieId);

                if (index > -1) {
                    selectedMovies.splice(index, 1);
                } else {
                    if (selectedMovies.length < 2) {
                        selectedMovies.push(movieId);
                    }
                }

                updateState();
            });
        });

        if (clearSelectionButton) {
            clearSelectionButton.addEventListener('click', () => {
                if (confirm('¿Estás seguro de que quieres limpiar la selección y los filtros?')) {
                    selectedMovies = [];
                    updateState();
                    const url = new URL(window.location);
                    url.searchParams.delete('movieId1');
                    url.searchParams.delete('movieId2');
                    window.history.replaceState({}, '', url.toString());

                    document.querySelector('form[action$="/filtrar"] [name="texto"]').value = '';
                    document.querySelector('form[action$="/filtrar"] [name="generoId"]').value = '';
                    document.querySelector('form[action$="/filtrar"] [name="year"]').value = '';
                    document.querySelector('form[action$="/filtrar"] [name="rating"]').value = '';
                }
            });
        }

        updateState();
    });
</script>

</body>
</html>
