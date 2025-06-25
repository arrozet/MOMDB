<%--
  Created by IntelliJ IDEA.
  User: edugbau (Eduardo González)
  Date: 25/06/2025
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
                                        <p class="title is-4 has-text-white">${stat.value}</p>
                                        <p class="has-text-grey">${stat.description}</p>
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
