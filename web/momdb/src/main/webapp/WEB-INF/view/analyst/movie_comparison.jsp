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
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<jsp:include page="../user/cabecera_user.jsp" />

<section class="section">
    <div class="container">
        <h1 class="title">Comparisons for <%= movie.getTitulo() %></h1>
        <a href="/analyst/movie/<%= movie.getId() %>" class="button is-link is-light mb-4">Back to Details</a>

        <div class="grid-container">
            <% for (MovieComparisonDTO comparison : comparisons) { %>
            <% if (comparison.getMovieValue() != null) { %>
            <div class="card">
                <header class="card-header has-background-primary">
                    <p class="card-header-title has-text-white"><%= comparison.getMetricName() %></p>
                </header>
                <div class="card-content">
                    <div class="content">
                        <p><strong>Movie Value:</strong>
                            <%
                                Object movieValue = comparison.getMovieValue();
                                if (movieValue instanceof Number) {
                                    out.print(df.format(movieValue));
                                } else {
                                    out.print(movieValue);
                                }
                            %>
                        </p>
                        <p><strong>Overall Average:</strong>
                            <%
                                Object overallAverage = comparison.getOverallAverage();
                                if (overallAverage instanceof Number) {
                                    out.print(df.format(overallAverage));
                                } else {
                                    out.print(overallAverage != null ? overallAverage : "N/A");
                                }
                            %>
                        </p>
                        <p><strong>Genre Average:</strong>
                            <%
                                Object genreAverage = comparison.getGenreAverage();
                                if (genreAverage instanceof Number) {
                                    out.print(df.format(genreAverage));
                                } else {
                                    out.print(genreAverage != null ? genreAverage : "N/A");
                                }
                            %>
                        </p>
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
