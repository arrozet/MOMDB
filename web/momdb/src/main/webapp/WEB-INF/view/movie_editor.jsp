<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Artur797 (Artur Vargas)
  Date: 16/06/2025
  Time: 12:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="/css/common.css">
<link rel="stylesheet" href="/css/user.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<html>
<head>
    <title>Movie Editor</title>
</head>
<body class="has-background-white-ter">
<jsp:include page="cabecera_editor.jsp" />

<div class="section">
    <div class="container box">
        <h1 class="title is-4">Edit movie</h1>

        <form:form method="post" action="/editor/saveMovie" modelAttribute="movie">
            <form:hidden path="id"/>

            <div class="field">
                <label class="label">Title</label>
                <div class="control">
                    <form:input path="titulo" cssClass="input"/>
                </div>
            </div>

            <div class="field">
                <label class="label">Language</label>
                <div class="control">
                    <form:input path="idiomaOriginal" cssClass="input"/>
                </div>
            </div>

            <div class="field">
                <label class="label">Release date</label>
                <div class="control">
                    <form:input type="date" path="fechaDeSalida" cssClass="input"/>
                </div>
            </div>

            <div class="field">
                <label class="label">Revenues</label>
                <div class="control">
                    <form:input path="ingresos" cssClass="input"/>
                </div>
            </div>

            <div class="field">
                <label class="label">Genres</label>
                <div class="control">
                    <form:checkboxes path="generoIds" items="${generos}" itemValue="id" itemLabel="genero" cssClass="mr-3"/>
                </div>
            </div>

            <div class="field">
                <label class="label">Description</label>
                <div class="control">
                    <form:textarea path="descripcion" rows="8" cssClass="textarea"/>
                </div>
            </div>

            <div class="field">
                <label class="label">Image link</label>
                <div class="control">
                    <form:input path="imageLink" cssClass="input"/>
                </div>
            </div>

            <div class="field is-grouped mt-4">
                <div class="control">
                    <button class="button is-link">Save</button>
                </div>
            </div>
        </form:form>
    </div>
</div>

</body>
</html>