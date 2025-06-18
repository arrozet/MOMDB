<%---
Created by IntelliJ IDEA.
  User: arrozet (Rubén Oliva)
  Date: 15/04/2025
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.taw.momdb.dto.UserDTO" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Welcome back, Admin!</title>
</head>
<%
    UserDTO myUser = (UserDTO) session.getAttribute("user");
    List<String> everyEntity = (List<String>) request.getAttribute("everyEntity");
    List<?> entities = (List<?>) request.getAttribute("entities");
%>
<body>
    <h1>Welcome back, <%= myUser.getUsername() %>!<br></h1>
    <table>
        <tr>
            <!-- TABLA DE PARES <USUARIO, ROL> -->
            <td>
                <form:form method="post" action="/admin/changeUser" modelAttribute="usersForm">
                    <table border="1">
                        <tr>
                            <th>User</th>
                            <th>Role</th>
                        </tr>
                        <!--usersForm.users accede directamente al modelo. Lo mismo con userRoles.
                        El $ {} hace el request.getAttribute directamente -->
                        <c:forEach var="user" items="${usersForm.users}" varStatus="status">
                            <tr>
                                <td>
                                    ${user.username}
                                    <form:hidden path="users[${status.index}].userId" />
                                </td>
                                <td>
                                    <form:select path="users[${status.index}].roleId" items="${userRoles}" itemValue="id" itemLabel="name"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <br>
                    <form:button>Save changes</form:button>
                </form:form>
            </td>

            <!-- TABLA DE ENTIDADES SELECCIONADAS -->
            <td>
                <form:form method="post" action="/admin/showEntities" modelAttribute="genericEntity">
                    <form:select path="selectedEntity" items="${everyEntity}" onchange="this.form.submit()"/>
                </form:form>
                <table border="1">
                    <tr>
                        <th>Id</th>
                        <th>Value</th>
                    </tr>
                    <c:forEach var="entity" items="${entities}">
                        <tr>
                            <td>${entity.id}</td>
                            <td>${entity.name}</td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </table>


</body>
</html>
