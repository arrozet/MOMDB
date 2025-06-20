<%@ page import="es.uma.taw.momdb.dto.ReviewDTO" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: amcgiluma (Juan Manuel Valenzuela)
  Date: 20/06/2025
  Time: 16:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>My Reviews | MOMDB</title>
  <link rel="stylesheet" href="/css/common.css">
  <link rel="stylesheet" href="/css/movie_details.css">
  <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body class="page-background">
<jsp:include page="cabecera_recommender.jsp" />

<section class="hero is-small page-banner">
  <div class="hero-body">
    <div class="container has-text-centered">
      <h1 class="title">Your reviews</h1>
    </div>
  </div>
</section>

<section class="section">
  <div class="container">
    <%
      List<ReviewDTO> myReviews = (List<ReviewDTO>) request.getAttribute("reviews");
      if (myReviews != null && !myReviews.isEmpty()) {
        for (ReviewDTO review : myReviews) {
    %>
    <div class="review-box">
      <p class="review-author">
        <strong><%= review.getUsername() %></strong> reviewed
        <span style="color:#ffd700;"><%= review.getMovieTitle() %></span>
      </p>
      <p class="review-rating">
        ⭐ <%= review.getRating() != null ? review.getRating() : "-" %>/10
      </p>
      <p class="review-content">
        <%= review.getContent() != null ? review.getContent() : "" %>
      </p>
      <div class="review-actions">
        <a href="/recommender/review/write?id=<%= review.getMovieId() %>&from=myreviews" title="Edit" class="button is-small is-warning">
          <span class="icon"><i class="fas fa-edit"></i></span>
        </a>
        <form action="/recommender/movie/review/delete" method="get" style="display:inline;">
          <input type="hidden" name="movieId" value="<%= review.getMovieId() %>" />
          <input type="hidden" name="userId" value="<%= review.getUserId() %>" />
          <input type="hidden" name="from" value="myreviews" />
          <button type="submit" class="button is-small is-danger" title="Delete" onclick="return confirm('Are you sure you want to delete this review?');">
            <span class="icon"><i class="fas fa-trash"></i></span>
          </button>
        </form>
      </div>
    </div>
    <%   }
    } else { %>
    <div class="notification is-warning is-light has-text-centered">
      You haven't written any reviews yet.
    </div>
    <% } %>
  </div>
</section>
</body>
</html>