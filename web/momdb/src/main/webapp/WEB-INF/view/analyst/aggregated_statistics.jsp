<%--
author: edugbau (Eduardo González)
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Aggregated Statistics | MOMDB</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/analyst.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body class="page-background">
<jsp:include page="../analyst/cabecera_analyst.jsp" />
<jsp:include page="../banner.jsp" />

<section class="section">
    <div class="container">
        <h2 class="title is-4 has-text-white mt-5">Aggregated Statistics</h2>

        <c:choose>
            <c:when test="${not empty statistics}">
                <div class="columns is-multiline">
                    <c:forEach items="${statistics}" var="stat" varStatus="status">
                        <div class="column is-one-third">
                            <c:set var="value" value="${stat.value}" />
                            <c:set var="valueClass" value="${value.getClass().simpleName}" />
                            <c:set var="isChartable" value="${valueClass.contains('List') || valueClass.contains('Map')}" />
                            <div class="card statistic-card ${isChartable ? 'chartable' : ''}"
                                    <c:if test="${isChartable}">
                                        data-chart-id="chart-${status.index}"
                                        data-chart-type="${valueClass.contains('Map') ? 'map' : 'list'}"
                                        data-stat-name="${stat.name}"
                                    </c:if>
                            >
                                <div class="card-content">
                                    <div class="content">
                                        <p class="subtitle is-6 has-text-grey-light">${stat.name}</p>
                                        <c:choose>
                                            <c:when test="${isChartable}">
                                                <div class="statistic-list-container">
                                                    <c:set var="listSize" value="${fn:length(value)}" />
                                                    <ul class="statistic-list ${listSize > 4 ? 'multi-column' : ''}">
                                                        <c:choose>
                                                            <c:when test="${valueClass.contains('List')}">
                                                                <c:forEach items="${value}" var="item">
                                                                    <li>${item}</li>
                                                                </c:forEach>
                                                            </c:when>
                                                            <c:when test="${valueClass.contains('Map')}">
                                                                <c:forEach items="${value}" var="entry">
                                                                    <li>
                                                                        <strong>${entry.key}:</strong>
                                                                        <fmt:formatNumber value="${entry.value}" maxFractionDigits="2"/>
                                                                    </li>
                                                                </c:forEach>
                                                            </c:when>
                                                        </c:choose>
                                                    </ul>
                                                </div>
                                                <div class="statistic-chart-container" style="display: none;">
                                                    <canvas id="chart-${status.index}"></canvas>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <p class="title is-4 has-text-white">${value}</p>
                                            </c:otherwise>
                                        </c:choose>
                                        <p class="has-text-grey mt-2">${stat.description}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="notification is-info is-light">
                    <p class="has-text-centered">Could not calculate statistics.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</section>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const chartableCards = document.querySelectorAll('.chartable');

        const colors = [
            'rgba(255, 99, 132, 0.2)', 'rgba(54, 162, 235, 0.2)', 'rgba(255, 206, 86, 0.2)',
            'rgba(75, 192, 192, 0.2)', 'rgba(153, 102, 255, 0.2)', 'rgba(255, 159, 64, 0.2)',
            'rgba(199, 199, 199, 0.2)', 'rgba(83, 102, 255, 0.2)', 'rgba(100, 255, 64, 0.2)',
            'rgba(255, 99, 255, 0.2)'
        ];
        const borderColors = colors.map(color => color.replace('0.2', '1'));

        chartableCards.forEach(card => {
            card.style.cursor = 'pointer';
            card.addEventListener('click', function () {
                const chartId = this.dataset.chartId;
                const chartType = this.dataset.chartType;
                const statName = this.dataset.statName;
                const listContainer = this.querySelector('.statistic-list-container');
                const chartContainer = this.querySelector('.statistic-chart-container');

                if (chartContainer.style.display === 'none') {
                    listContainer.style.display = 'none';
                    chartContainer.style.display = 'block';

                    if (Chart.getChart(chartId)) {
                        return;
                    }

                    const labels = [];
                    const data = [];

                    if (chartType === 'map') {
                        const items = this.querySelectorAll('.statistic-list li');
                        items.forEach(item => {
                            const strongEl = item.querySelector('strong');
                            if (strongEl && strongEl.nextSibling) {
                                labels.push(strongEl.innerText.replace(':', ''));
                                let valueString = strongEl.nextSibling.textContent.trim();
                                const lastComma = valueString.lastIndexOf(',');
                                const lastDot = valueString.lastIndexOf('.');

                                if (lastComma > lastDot) { // Format is 1.234,56
                                    valueString = valueString.replace(/\./g, '').replace(',', '.');
                                } else { // Format is 1,234.56
                                    valueString = valueString.replace(/,/g, '');
                                }
                                data.push(parseFloat(valueString));
                            }
                        });
                    } else { // list
                        const items = this.querySelectorAll('.statistic-list li');
                        items.forEach(item => {
                            const text = item.innerText;
                            let parts;

                            // Case 1: Profitability (e.g., "Actor Name (Profitability: $1,234.56M)")
                            parts = text.split(' (Profitability:');
                            if (parts.length === 2) {
                                const name = parts[0];
                                let valueString = parts[1].replace(/[^\d,.-]/g, ''); // Keep only numbers and separators
                                const lastComma = valueString.lastIndexOf(',');
                                const lastDot = valueString.lastIndexOf('.');

                                if (lastComma > lastDot) { // Format is 1.234,56
                                    valueString = valueString.replace(/\./g, '').replace(',', '.');
                                } else { // Format is 1,234.56
                                    valueString = valueString.replace(/,/g, '');
                                }
                                labels.push(name);
                                data.push(parseFloat(valueString));
                            }
                            // Case 2: Runtime Evolution (e.g., "1990s: 105.5 minutes")
                            else {
                                parts = text.split(':');
                                if (parts.length > 1) {
                                    const name = parts[0].trim();
                                    let valueString = parts.slice(1).join(':').trim();
                                    // Remove any text after the number, e.g., " minutes"
                                    valueString = valueString.split(' ')[0];
                                    // Standardize decimal separator to dot for parseFloat
                                    valueString = valueString.replace(',', '.');
                                    labels.push(name);
                                    data.push(parseFloat(valueString));
                                }
                            }
                        });
                    }

                    const ctx = document.getElementById(chartId).getContext('2d');
                    new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: labels,
                            datasets: [{
                                label: statName,
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
                                    beginAtZero: true,
                                    ticks: { color: '#fff' }
                                },
                                x: {
                                    ticks: { color: '#fff' }
                                }
                            }
                        }
                    });

                } else {
                    listContainer.style.display = 'block';
                    chartContainer.style.display = 'none';
                }
            });
        });
    });
</script>

</body>
</html>