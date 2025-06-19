<%@ page import="es.uma.taw.momdb.dto.PersonDTO" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Person</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/user.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<%
    PersonDTO person = (PersonDTO) request.getAttribute("person");
%>
<body class="has-background-white-ter">
<jsp:include page="cabecera_editor.jsp" />
<div class="container">

    <h1 class="title is-3 has-text-centered" style="margin-top:2.5rem;"><%= person.getId() == -1 ? "New Person" : "Edit Person" %></h1>

    <div class="section">
        <div class="container" style="max-width: 400px;">
            <form:form method="POST" action="/editor/person/save" modelAttribute="person">
                <form:hidden path="id" value="<%=person.getId()%>"/>
                <div class="field">
                    <label class="label">Name</label>
                    <div class="control">
                        <form:input path="name" class="input"/>
                    </div>
                </div>
                <div class="field is-grouped is-grouped-right">
                    <div class="control">
                        <button type="submit" class="button is-success">Save</button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>
</body>
</html> 