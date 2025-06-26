<%--
  Created by IntelliJ IDEA.
  User: edugbau (Eduardo González)
  Date: 20/06/2025
  Time: 10:00
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.uma.taw.momdb.dto.UserDTO" %>
<%@ page import="es.uma.taw.momdb.dto.MovieDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.momdb.dto.MovieComparisonDTO" %>
<%@ page import="java.text.DecimalFormat" %>

<%-- Configuración de la página --%>
<% UserDTO myUser = (UserDTO) session.getAttribute("user"); %>
<% MovieDTO movie = (MovieDTO) request.getAttribute("movie"); %>
<% List<MovieComparisonDTO> comparisons = (List<MovieComparisonDTO>) request.getAttribute("comparisons"); %>
<% DecimalFormat df = new DecimalFormat("#,##0.00"); %>

<html>
<head>
    <title>Movie Comparison | MOMDB</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/analyst.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/movie_details.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<jsp:include page="cabecera_analyst.jsp" />

<section class="section">
    <div class="container">
        <div class="level">
            <div class="level-left">
                <div>
                    <h1 class="title has-text-white">Comparisons for <%= movie.getTitulo() %></h1>
                    <a href="/analyst/movie/<%= movie.getId() %>" class="button is-link is-light">
                        <span class="icon"><i class="fas fa-arrow-left"></i></span>
                        <span>Back to Details</span>
                    </a>
                </div>
            </div>
        </div>

        <div class="columns is-multiline is-centered mt-5">
            <% for (MovieComparisonDTO comparison : comparisons) { %>
            <% if (comparison.getMovieValue() != null) { %>
            <div class="column is-one-third-desktop is-half-tablet">
                <div class="box movie-details-box has-text-centered">
                    <h3 class="title is-4 has-text-white"><%= comparison.getMetricName() %></h3>
                    <hr class="is-divider" style="background-color: #444;">
                    <div class="content">
                        <div class="tags has-addons is-centered are-medium">
                            <span class="tag movie-info-tag">Movie</span>
                            <span class="tag is-primary">
                                <%
                                    Object movieValue = comparison.getMovieValue();
                                    if (movieValue instanceof Number) {
                                        out.print(df.format(movieValue));
                                    } else {
                                        out.print(movieValue);
                                    }
                                %>
                            </span>
                        </div>
                        <div class="tags has-addons is-centered are-medium mt-3">
                            <span class="tag movie-info-tag">Overall Avg.</span>
                            <span class="tag is-info">
                                <%
                                    Object overallAverage = comparison.getOverallAverage();
                                    if (overallAverage instanceof Number) {
                                        out.print(df.format(overallAverage));
                                    } else {
                                        out.print(overallAverage != null ? overallAverage : "N/A");
                                    }
                                %>
                            </span>
                        </div>
                        <div class="tags has-addons is-centered are-medium mt-3">
                            <span class="tag movie-info-tag">Genre Avg.</span>
                            <span class="tag is-info">
                                <%
                                    Object genreAverage = comparison.getGenreAverage();
                                    if (genreAverage instanceof Number) {
                                        out.print(df.format(genreAverage));
                                    } else {
                                        out.print(genreAverage != null ? genreAverage : "N/A");
                                    }
                                %>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
            <% } %>
            <% } %>
        </div>
    </div>
</section>

</body>
</html>
