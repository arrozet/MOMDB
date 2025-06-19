<%@ page import="es.uma.taw.momdb.dto.PersonDTO" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Personas</title>
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/user.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        .people-grid {
            display: flex;
            flex-wrap: wrap;
            gap: 2rem;
            justify-content: center;
            margin-top: 2rem;
        }
        .person-card {
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            padding: 1.5rem 2rem;
            min-width: 220px;
            max-width: 260px;
            text-align: center;
            transition: box-shadow 0.2s;
        }
        .person-card:hover {
            box-shadow: 0 4px 16px rgba(0,0,0,0.16);
        }
        .person-icon {
            font-size: 3rem;
            color: #3273dc;
            margin-bottom: 0.5rem;
        }
        .person-name {
            font-size: 1.2rem;
            font-weight: bold;
            color: #363636;
        }
    </style>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<%
    List<PersonDTO> people = (List<PersonDTO>) request.getAttribute("people");
%>
<body class="has-background-white-ter">
<jsp:include page="cabecera_editor.jsp" />
<div class="container">
    <h1 class="title is-3 has-text-centered" style="margin-top:2.5rem;">People</h1>
    <div class="section">
        <div class="container">
            <div class="columns is-multiline">
                <div class="column is-10">
                    <form:form method="GET" action="/editor/people" modelAttribute="filtro">
                        <div class="field has-addons">
                            <div class="control is-expanded">
                                <form:input path="texto" class="input is-info has-background-grey" placeholder="Search people..."/>
                            </div>
                            <div class="control">
                                <form:button class="button is-info">
                                    <span class="icon">
                                        <i class="fas fa-search"></i>
                                    </span>
                                    <span>Filtrar</span>
                                </form:button>
                            </div>
                        </div>
                    </form:form>
                </div>
                <div class="column is-2 has-text-right">
                    <a href="/editor/person/add" class="button is-success">
                        <span class="icon">
                            <i class="fas fa-plus"></i>
                        </span>
                        <span>New person</span>
                    </a>
                </div>
            </div>
        </div>
    </div>
    <div class="people-grid">
        <% 
            if (people != null) {
                for (PersonDTO person : people) {
        %>
            <div class="person-card" style="position:relative;">
                <a href="/editor/person/delete?id=<%= person.getId() %>" class="has-text-danger" style="position: absolute; top: 0.1rem; right: 0.1rem; z-index: 10;">
                    <span class="icon">
                        <i class="fas fa-trash"></i>
                    </span>
                </a>
                <span class="person-icon"><i class="fas fa-user"></i></span>
                <div class="person-name">
                    <a href="/editor/person/edit?id=<%= person.getId() %>"><%= person.getName() %></a>
                </div>
            </div>
        <%      }
            } else {
        %>
            There are no people
        <%  } %>
    </div>
</div>
</body>
</html> 