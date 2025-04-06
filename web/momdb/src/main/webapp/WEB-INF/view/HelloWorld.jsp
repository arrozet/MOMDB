<%@ page import="es.uma.taw.momdb.entity.Movie" %><%--
  Created by IntelliJ IDEA.
  User: roz
  Date: 06/04/2025
  Time: 11:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
Movie primeraPeli = (Movie) request.getAttribute("primeraPeli");
%>

<head>
    <title>MOMDB</title>
</head>
<body>
Hola Mundo<br/><br/>

Esta es mi primera peli:<br/>
<%=primeraPeli.getOriginalTitle()%>
</body>
</html>
