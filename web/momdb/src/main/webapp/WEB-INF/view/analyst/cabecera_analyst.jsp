<%--Autor: Eduardo González--%>

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
            <a class="navbar-item" href="/analyst/">
                <span class="icon">
                    <i class="fas fa-film"></i>
                </span>
                <span>Main Page</span>
            </a>

            <a href="${pageContext.request.contextPath}/analyst/compare" class="navbar-item">
                <span class="icon">
                    <i class="fas fa-balance-scale"></i>
                </span>
                <span>Compare movies</span>
            </a>
            <a href="${pageContext.request.contextPath}/analyst/genre-analytics" class="navbar-item">
                <span class="icon">
                    <i class="fas fa-chart-line"></i>
                </span>
                <span>Genre Analytics</span>
            </a>
            <a href="${pageContext.request.contextPath}/analyst/aggregated-statistics" class="navbar-item">
                <span class="icon">
                    <i class="fas fa-chart-bar"></i>
                </span>
                <span>Aggregated Statistics</span>
            </a>
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
                <figure class="image is-32x32">
                    <% if(myUser.getProfilePic() != null) { %>
                    <img class="is-rounded" src="<%= myUser.getProfilePic() %>" alt="Profile picture">
                    <% } else { %>
                    <span class="icon">
                            <i class="fas fa-user-circle fa-2x"></i>
                        </span>
                    <% } %>
                </figure>
            </div>
        </div>
    </div>
</nav>


</html>