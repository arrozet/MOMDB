<%--
author: projectGeorge (Jorge Repullo)
--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Write review | MOMDB</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/user.css">
    <link rel="stylesheet" href="/css/review.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body class="page-background">
<jsp:include page="cabecera_user.jsp" />

<section class="hero is-light is-small page-banner">
    <div class="hero-body">
        <div class="container has-text-centered">
            <h1 class="title">Write your review:</h1>
        </div>
    </div>
</section>

<section class="section">
    <div class="review-box">
        <form:form  method="POST" action="/user/review/save" modelAttribute="reviewDTO">
            <form:hidden path="movieId"/>
            <input type="hidden" name="from" value="${param.from}" />
            <div class="field">
                <label class="label">Rating</label>
                <div class="control">
                    <form:input path="rating" type="number" min="1" max="10" step="0.1" class="input is-info has-background-grey" required="true" />
                </div>
            </div>
            <div class="field">
                <label class="label">Review</label>
                <div class="control">
                    <form:textarea path="content" class="textarea is-info has-background-grey" rows="5" required="true" />
                </div>
            </div>
            <div class="field is-grouped is-grouped-centered">
                <div class="control">
                    <form:button class="button is-info">Submit</form:button>
                </div>
                <div class="control">
                    <%
                        String from = request.getParameter("from");
                        String cancelUrl;

                        if ("myreviews".equals(from)) {
                            cancelUrl = "/user/userReviews";
                        } else {
                            cancelUrl = "/user/movie?id=" + request.getAttribute("movieId");
                        }
                    %>
                    <a href="<%= cancelUrl %>" class="button is-light">Cancel</a>
                </div>
            </div>
        </form:form>
    </div>
</section>
</body>
</html>
