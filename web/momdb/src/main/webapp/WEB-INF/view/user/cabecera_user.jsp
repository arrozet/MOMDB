<%--
author: projectGeorge (Jorge Repullo - 85.1%), amcgiluma (Juan Manuel Valenzuela - 8.9%), arrozet (Rubén Oliva - 4.0%), Artur797 (Artur Vargas - 2.0%)
--%>

<%@ page import="java.util.Date" %>
<%@ page import="es.uma.taw.momdb.dto.UserDTO" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="stylesheet" href="/css/common.css">
<meta name="viewport" content="width=device-width, initial-scale=1">

<%
    UserDTO myUser = (UserDTO) session.getAttribute("user");
%>

<nav class="navbar is-info" role="navigation" aria-label="main navigation"> <%-- Barra horizontal, color azul claro--%>

    <div id="navbarMenu" class="navbar-menu">
        <div class="navbar-start">
            <a class="navbar-item" href="/user/">
                <span class="icon">
                    <i class="fas fa-film"></i>
                </span>
                <span>My Movies</span>
            </a>

            <a class="navbar-item" href="/user/userReviews">
                <span class="icon">
                    <i class="fas fa-star"></i>
                </span>
                <span>My Reviews</span>
            </a>

            <a class="navbar-item" href="/user/favorites">
                <span class="icon">
                    <i class="fas fa-thumbs-up"></i>
                </span>
                <span>My Favorites</span>
            </a>
            <a class="navbar-item" href="/user/watchlist">
                <span class="icon">
                    <i class="far fa-bookmark"></i>
                </span>
                <span>Watch later</span>
            </a>
            <% if ("usuario".equals(myUser.getRolename())) { %>
            <a class="navbar-item" href="/user/upgrade">
                <span class="icon has-text-warning">
                    <i class="fas fa-crown"></i>
                </span>
                <span>Upgrade Pro</span>
            </a>
            <% } %>
        </div>

        <div class="navbar-end">
            <div class="navbar-item has-dropdown is-hoverable">
                <a class="navbar-link">
                    <span class="icon">
                        <i class="fas fa-user"></i>
                    </span>
                    <span><%= myUser.getUsername() %></span>
                </a>

                <div class="navbar-dropdown is-right">
                    <div class="navbar-item">
                        <small>Session ID: <%= session.getId() %></small>
                    </div>
                    <div class="navbar-item">
                        <small>Date: <%= new Date(session.getCreationTime()) %></small>
                    </div>
                    <hr class="navbar-divider">
                    <a href="/logout" class="navbar-item">
                        <span class="icon">
                            <i class="fas fa-sign-out-alt"></i>
                        </span>
                        <span>Log out</span>
                    </a>
                </div>
            </div>

            <div class="navbar-item">
                <a href="/user/profile">
                    <figure class="image is-32x32" style="cursor: pointer;">
                        <% if(myUser.getProfilePic() != null) { %>
                        <img class="is-rounded" src="<%= myUser.getProfilePic() %>" alt="Profile picture">
                        <% } else { %>
                        <span class="icon">
                                <i class="fas fa-user-circle fa-2x"></i>
                            </span>
                        <% } %>
                    </figure>
                </a>
            </div>
        </div>
    </div>
</nav>

</html>