<%-- 
    Document   : index
    Created on : Dec 13, 2021, 11:29:55 AM
    Author     : vinix
--%>

<%@page import="java.time.ZoneId"%>
<%@page import="java.time.Instant"%>
<%@page import="me.dannly.maispratiadvanced.data.repository.PersonDAO"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="me.dannly.maispratiadvanced.domain.model.Student"%>
<%@page import="me.dannly.maispratiadvanced.domain.model.Person"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
        <title>Início</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="#">Seleção +praTI</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="index.jsp">Início</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="edit.jsp">Criar</a>
                    </li>
                </ul>
                <form class="form-inline my-2 my-lg-0" action='index.jsp' method='get'>
                    <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" name='q'>
                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Pesquisar</button>
                </form>
            </div>
        </nav>
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Nome</th>
                    <th scope="col">Telefone</th>
                    <th scope="col">Idade</th>
                    <th scope="col">Data de nascimento</th>
                    <th scope="col">Última vez modificado</th>
                    <th scope="col">Data de registro</th>
                    <th scope="col">Nota</th>
                    <th scope="col">Ações</th>
                </tr>
            </thead>
            <tbody>
                <jsp:useBean class="me.dannly.maispratiadvanced.data.repository.PersonDAO" id="personDAO"/>
                <c:set var="query" value="%${param.q}%"/>
                <c:forEach items='${param.q == null ? personDAO.elements : personDAO.getElementsByFilter("name LIKE ?", query)}' var="person">
                    <tr>
                        <th scope="row">${person.id}</th>
                        <td>${person.name}</td>
                        <td>${person.phone}</td>
                        <td>${person.age}</td>
                        <td><%=Instant.ofEpochMilli(((Person) pageContext.getAttribute("person")).getBirth()).atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
                        <td><%=Instant.ofEpochMilli(((Person) pageContext.getAttribute("person")).getLastModified()).atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"))%></td>
                        <td><%=Instant.ofEpochMilli(((Person) pageContext.getAttribute("person")).getCreatedAt()).atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"))%></td>
                        <td><%=((Person) pageContext.getAttribute("person")) instanceof Student ? ((Student) pageContext.getAttribute("person")).getScore() : "-"%></td>
                        <td>
                            <form action="PersonProcesser" method="post" id="delete${person.id}">
                                <input type="hidden" value="${person.id}" name="delete"/>
                                <a href="edit.jsp?id=${person.id}">
                                    <i class="bi-pencil-fill"></i>
                                </a>
                                <a href="javascript:confirmDelete('${person.name}', ${person.id})">
                                    <i class='bi-trash-fill'></i>
                                </a>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
    <script type="text/javascript" src="assets/js/delete_confirmation.js"></script>
</html>
