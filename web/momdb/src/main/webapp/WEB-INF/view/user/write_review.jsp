<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: jorge
  Date: 19/06/2025
  Time: 20:44
  To change this template use File | Settings | File Templates.
--%>
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
                    <a href="/user/movie?id=${movieId}" class="button is-light">Cancel</a>
                </div>
            </div>
        </form:form>
    </div>
</section>
</body>
</html>
