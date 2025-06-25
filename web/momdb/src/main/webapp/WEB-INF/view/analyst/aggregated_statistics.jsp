<%--
  Created by IntelliJ IDEA.
  User: edugbau (Eduardo González)
  Date: 25/06/2025
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Estadísticas Agregadas | MOMDB</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/analyst.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body class="page-background">
<jsp:include page="../analyst/cabecera_analyst.jsp" />
<jsp:include page="../banner.jsp" />

<section class="section">
    <div class="container">
        <h2 class="title is-4 has-text-white mt-5">Estadísticas Agregadas</h2>

        <c:choose>
            <c:when test="${not empty statistics}">
                <div class="columns is-multiline">
                    <c:forEach items="${statistics}" var="stat">
                        <div class="column is-one-third">
                            <div class="card statistic-card">
                                <div class="card-content">
                                    <div class="content">
                                        <p class="subtitle is-6 has-text-grey-light">${stat.name}</p>
                                        <c:set var="value" value="${stat.value}" />
                                        <c:set var="valueClass" value="${value.getClass().simpleName}" />
                                        <c:choose>
                                            <c:when test="${valueClass.contains('List')}">
                                                <div class="statistic-list-container">
                                                    <c:set var="listSize" value="${fn:length(value)}" />
                                                    <ul class="statistic-list ${listSize > 4 ? 'multi-column' : ''}">
                                                        <c:forEach items="${value}" var="item">
                                                            <li>${item}</li>
                                                        </c:forEach>
                                                    </ul>
                                                </div>
                                            </c:when>
                                            <c:when test="${valueClass.contains('Map')}">
                                                <div class="statistic-list-container">
                                                    <c:set var="listSize" value="${fn:length(value)}" />
                                                    <ul class="statistic-list ${listSize > 4 ? 'multi-column' : ''}">
                                                        <c:forEach items="${value}" var="entry">
                                                            <li>
                                                                <strong>${entry.key}:</strong>
                                                                <fmt:formatNumber value="${entry.value}" maxFractionDigits="2"/>
                                                            </li>
                                                        </c:forEach>
                                                    </ul>
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
                    <p class="has-text-centered">No se pudieron calcular las estadísticas.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</section>

</body>
</html>
