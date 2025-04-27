<%@ page import="es.uma.taw.momdb.entity.User" %>
<%@ page import="java.util.Date" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%
    User myUser = (User) session.getAttribute("user");
%>

<table width="100%">
    <tr>
        <td><b>Esto debería ser una barra lateral</b></td>
        <td><a href="/user/">My Movies</a></td>
        <td>My shows</td>
        <td>My reviews</td>
        <td>My recommendations</td>
        <td>Welcome back, <b><%= myUser.getUsername()%></b><br/>
            sessionid: <%= session.getId() %> <br/>
            date: <%= new Date(session.getCreationTime()) %> <br/>
            (<a href="/logout">Log out</a>)</td>
        <td><%=myUser.getProfilePic()%></td>
    </tr>
</table>

</html>