<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Entity</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/admin.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body class="page-background">

<jsp:include page="cabecera_admin.jsp" />

<section class="section">
    <div class="container">
        <h1 class="title admin-title">Edit ${entityType}</h1>
        <div class="box">
            <form:form method="post" action="/admin/updateEntity" modelAttribute="entity">
                <form:hidden path="id" />
                <input type="hidden" name="entityType" value="${entityType}">

                <div class="field">
                    <label class="label" for="name">Name</label>
                    <div class="control">
                        <form:input path="name" cssClass="input" id="name"/>
                    </div>
                </div>

                <div class="field">
                    <div class="control">
                        <button type="submit" class="button is-primary">Save</button>
                        <a href="/admin/entities" class="button is-light">Cancel</a>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</section>

</body>
</html> 