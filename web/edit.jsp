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
        <title>
            ${param.id == null ? "Criar" : "Editar"}
        </title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="#">Seleção +praTI</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="index.jsp">Início</a>
                    </li>
                    <li class='${param.id == null ? "nav-item active" : "nav-item"}'>
                        <a class="nav-link" href="edit.jsp">Criar</a>
                    </li>
                </ul>
            </div>
        </nav>
        <form action="PersonProcesser" method="post" onsubmit="return validateForm()">
            <%
                Person person = null;
                if (request.getParameter("id") != null) {
                    person = new PersonDAO().getElementsByFilter("id = ?", request.getParameter("id")).get(0);
                }
                pageContext.setAttribute("person", person);
            %>
            <c:if test="${param.id != null}">
                <input type="hidden" value="${param.id}" name="id"/>
            </c:if>
            <div class="row">
                <div class="form-group col m-2">
                    <label for="name">Nome</label>
                    <input type="text" class="form-control" id="name" name="name" aria-describedby="emailHelp" placeholder="Insira o nome" ${param.id == null ? "required" : ""}
                           value='${param.id == null ? "" : person.name}'>
                </div>
                <div class='form-group col m-2'>
                    <label for="phone">Telefone</label>
                    <small class="text-muted">+XX (XX) XXXX-XXXX</small>
                    <input type="tel" class="form-control ${param.id == null ? 'is-invalid' : 'is-valid'}" id="phone" name="phone" placeholder="Telefone" ${param.id == null ? "required" : ""}
                           value='${param.id == null ? "" : person.getFormattedPhone()}'>
                </div>
            </div>

            <div class="row">
                <div class="form-group col mx-2">
                    <label for="birth">Data de nascimento</label>
                    <input type="date" class="form-control" id="birth" name="birth" placeholder="Data de nascimento" ${param.id == null ? "required" : ""}
                           value='<%=request.getParameter("id") == null ? "" : Instant.ofEpochMilli(person.getBirth()).atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))%>'>
                </div>
                <div class="form-group col mx-2">
                    <label for="age">Idade</label>
                    <small class="text-muted">0 - 127</small>
                    <input type="number" class="form-control ${param.id == null ? 'is-invalid' : 'is-valid'}" id="age" name="age" placeholder="Idade" ${param.id == null ? "required" : ""}
                           value='${param.id == null ? "" : person.age}'>
                </div>
            </div>
            <div class="row">
                <div class="form-group w-50 mx-auto">
                    <label for="score">Nota</label>
                    <small class="text-muted">0 - 10</small>
                    <input type="number" class='form-control <%=person instanceof Student ? "is-valid" : ""%>' id="score" name="score" placeholder="Nota" value='<%=person instanceof Student ? ((Student) person).getScore() : ""%>'>
                </div>
            </div>
            <div class="row">
                <button type="submit" class="btn btn-primary w-50 mx-auto">Enviar</button>
            </div>
        </form>
    </body>
    <script type="text/javascript" src="assets/js/input_validation.js"></script>
</html>
