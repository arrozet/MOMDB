<%--
author: arrozet (Rubén Oliva)
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.uma.taw.momdb.dto.UserDTO" %>
<%@ page import="java.util.Date" %>
<%
    UserDTO myUser = (UserDTO) session.getAttribute("user");
%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/css/common.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
</head>
<body>
<nav class="navbar is-info" role="navigation" aria-label="main navigation"> <%-- Barra horizontal, color azul claro--%>

    <div id="navbarMenu" class="navbar-menu">
        <div class="navbar-start">
            <a class="navbar-item" href="/admin/users">
                <span class="icon">
                    <i class="fas fa-users-cog"></i>
                </span>
                <span>User management</span>
            </a>

            <a class="navbar-item" href="/admin/entities">
                <span class="icon">
                    <i class="fas fa-database"></i>
                </span>
                <span>Entity management</span>
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
</body>
</html> 