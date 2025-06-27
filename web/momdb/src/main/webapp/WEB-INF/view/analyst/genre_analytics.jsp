<%--
author: edugbau (Eduardo González)
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Genre Analytics | MOMDB</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/analyst.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body class="page-background">
<jsp:include page="../analyst/cabecera_analyst.jsp" />
<jsp:include page="../banner.jsp" />

<section class="section">
    <div class="container">
        <h1 class="title has-text-white">Genre Analytics</h1>

        <div class="columns is-multiline">
            <div class="column is-half">
                <div class="card has-background-dark">
                    <header class="card-header">
                        <p class="card-header-title has-text-white">Average Revenue by Genre</p>
                    </header>
                    <div class="card-content">
                        <div class="content">
                            <canvas id="revenueChart"></canvas>
                        </div>
                    </div>
                </div>
            </div>
            <div class="column is-half">
                <div class="card has-background-dark">
                    <header class="card-header">
                        <p class="card-header-title has-text-white">Average Budget by Genre</p>
                    </header>
                    <div class="card-content">
                        <div class="content">
                            <canvas id="budgetChart"></canvas>
                        </div>
                    </div>
                </div>
            </div>
            <div class="column is-half">
                <div class="card has-background-dark">
                    <header class="card-header">
                        <p class="card-header-title has-text-white">Average Runtime by Genre</p>
                    </header>
                    <div class="card-content">
                        <div class="content">
                            <canvas id="runtimeChart"></canvas>
                        </div>
                    </div>
                </div>
            </div>
            <div class="column is-half">
                <div class="card has-background-dark">
                    <header class="card-header">
                        <p class="card-header-title has-text-white">Average Popularity by Genre</p>
                    </header>
                    <div class="card-content">
                        <div class="content">
                            <canvas id="popularityChart"></canvas>
                        </div>
                    </div>
                </div>
            </div>
            <div class="column is-half">
                <div class="card has-background-dark">
                    <header class="card-header">
                        <p class="card-header-title has-text-white">Average Vote Count by Genre</p>
                    </header>
                    <div class="card-content">
                        <div class="content">
                            <canvas id="voteCountChart"></canvas>
                        </div>
                    </div>
                </div>
            </div>
            <div class="column is-half">
                <div class="card has-background-dark">
                    <header class="card-header">
                        <p class="card-header-title has-text-white">Favorites by Genre</p>
                    </header>
                    <div class="card-content">
                        <div class="content">
                            <canvas id="favoriteChart"></canvas>
                        </div>
                    </div>
                </div>
            </div>
            <div class="column is-half">
                <div class="card has-background-dark">
                    <header class="card-header">
                        <p class="card-header-title has-text-white">Watchlist by Genre</p>
                    </header>
                    <div class="card-content">
                        <div class="content">
                            <canvas id="watchlistChart"></canvas>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const genreNames = [];
        const revenueData = [];
        const budgetData = [];
        const runtimeData = [];
        const popularityData = [];
        const voteCountData = [];
        const favoriteData = [];
        const watchlistData = [];

        <c:forEach var="analytics" items="${genreAnalytics}">
        genreNames.push("${analytics.genreName}");
        revenueData.push(${analytics.averageRevenue != null ? analytics.averageRevenue : 0});
        budgetData.push(${analytics.averageBudget != null ? analytics.averageBudget : 0});
        runtimeData.push(${analytics.averageRuntime != null ? analytics.averageRuntime : 0});
        popularityData.push(${analytics.averagePopularity != null ? analytics.averagePopularity : 0});
        voteCountData.push(${analytics.averageVoteCount != null ? analytics.averageVoteCount : 0});
        favoriteData.push(${analytics.favoriteCount != null ? analytics.favoriteCount : 0});
        watchlistData.push(${analytics.watchlistCount != null ? analytics.watchlistCount : 0});
        </c:forEach>

        const colors = [
            'rgba(255, 99, 132, 0.2)', 'rgba(54, 162, 235, 0.2)', 'rgba(255, 206, 86, 0.2)',
            'rgba(75, 192, 192, 0.2)', 'rgba(153, 102, 255, 0.2)', 'rgba(255, 159, 64, 0.2)',
            'rgba(199, 199, 199, 0.2)', 'rgba(83, 102, 255, 0.2)', 'rgba(100, 255, 64, 0.2)',
            'rgba(255, 99, 255, 0.2)', 'rgba(255, 64, 100, 0.2)', 'rgba(64, 255, 255, 0.2)',
            'rgba(128, 128, 0, 0.2)', 'rgba(0, 128, 128, 0.2)', 'rgba(128, 0, 128, 0.2)',
            'rgba(255, 0, 0, 0.2)', 'rgba(0, 255, 0, 0.2)', 'rgba(0, 0, 255, 0.2)',
            'rgba(210, 105, 30, 0.2)', 'rgba(220, 20, 60, 0.2)'
        ];
        const borderColors = colors.map(color => color.replace('0.2', '1'));

        function createChart(canvasId, label, data) {
            const ctx = document.getElementById(canvasId).getContext('2d');
            new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: genreNames,
                    datasets: [{
                        label: label,
                        data: data,
                        backgroundColor: colors,
                        borderColor: borderColors,
                        borderWidth: 1
                    }]
                },
                options: {
                    plugins: {
                        legend: {
                            display: false
                        }
                    },
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        }

        createChart('revenueChart', 'Average Revenue', revenueData);
        createChart('budgetChart', 'Average Budget', budgetData);
        createChart('runtimeChart', 'Average Runtime', runtimeData);
        createChart('popularityChart', 'Average Popularity', popularityData);
        createChart('voteCountChart', 'Average Vote Count', voteCountData);
        createChart('favoriteChart', 'Favorites', favoriteData);
        createChart('watchlistChart', 'Watchlist', watchlistData);
    });
</script>

</body>
</html>
