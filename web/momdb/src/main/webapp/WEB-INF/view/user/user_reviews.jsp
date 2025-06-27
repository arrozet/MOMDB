<%--
author: projectGeorge (Jorge Repullo)
--%>

<%@ page import="es.uma.taw.momdb.dto.ReviewDTO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>My Reviews | MOMDB</title>
  <link rel="stylesheet" href="/css/common.css">
  <link rel="stylesheet" href="/css/movie_details.css">
  <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<jsp:include page="cabecera_user.jsp" />

<%
  List<ReviewDTO> myReviews = (List<ReviewDTO>) request.getAttribute("reviews");
%>

<section class="hero is-light is-small page-banner">
  <div class="hero-body">
    <div class="container has-text-centered">
      <h1 class="title">Your reviews</h1>
    </div>
  </div>
</section>

<section class="section">
  <div class="container">
    <%
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
        <a href="/user/review/write?id=<%= review.getMovieId() %>&from=myreviews" title="Edit" class="button is-small is-info">
          <span class="icon"><i class="fas fa-edit"></i></span>
        </a>
        <form action="/user/movie/review/delete" method="get" style="display:inline;">
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
    <div class="notification is-light has-text-centered">
      You haven't written any reviews yet.
    </div>
    <% } %>
  </div>
</section>
</body>
</html>
