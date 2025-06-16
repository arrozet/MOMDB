<%--
  Created by IntelliJ IDEA.
  User: Artur797 (Artur Vargas)
  Date: 11/05/2025
  Time: 20:22
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.taw.momdb.entity.Movie" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.momdb.dto.MovieDTO" %>
<%@ page import="es.uma.taw.momdb.entity.User" %>
<%@ page import="es.uma.taw.momdb.dto.CrewDTO" %>
<%@ page import="es.uma.taw.momdb.dto.CharacterDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Editor</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/user.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body class="has-background-white-ter">
<jsp:include page="cabecera_editor.jsp" />

    <%
    List<CrewDTO> actores = (List<CrewDTO>) request.getAttribute("actores");
%>
<body>
<%
    for(CrewDTO actor:actores){
%>
<%=actor.getPersona()%>: <%for(CharacterDTO personaje: actor.getPersonajes()){%>
<%=personaje.getCharacterName()%> <br/>
<%
    }
    %>
<br/>
<%
}
%>
</body>
</html>

