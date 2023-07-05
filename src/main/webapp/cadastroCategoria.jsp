<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/css/cadastrosz.css">
<link rel="stylesheet" href="resources/css/tableUsuarioss.css">
</head>
<body>
	<header>
		<h1>cadastro de categoria</h1>
	</header>

	<a
		style="font-size: 20px; text-decoration: none; padding-right: 15px; padding-left: 10px; color: lime;"
		href="acessoliberado.jsp">Início</a>
	<a style="font-size: 20px; text-decoration: none; color: red;"
		href="index.jsp">Sair</a>

	<form id="form" action="cadastroCategoria" method="post">
		<h3 style="color: green; text-align: center; font-size: 18px">${msg}</h3>
		<h3 style="color: red; text-align: center; font-size: 18px">${error}</h3>

		<input type="text" id="nome" class="floatLabel" required="required"
			placeholder="Nome" name="nome" value="${cat.nome}">
			<input type="hidden" id="id" class="floatLabel" required="required"
			placeholder="Nome" name="id" value="${cat.id}">
		<table>

			<tr>

				<td><input class="button" type="submit" id="cadastrar"
					value="Cadastrar produto"></td>
				<td><input class="button2" type="submit" id="cancelar"
					value="Cancelar"
					onclick="document.getElementById('form').action = 'cadastroCategoria?acao=reset'">
				</td>
			</tr>
		</table>
		<p></p>

		<p></p>

	</form>
	<div class="table-wrapper">
		<table class="fl-table">

			<thead>
				<tr>
					<th>ID</th>
					<th>Nome</th>
					<th>Editar</th>
					<th>Excluir</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${categoria}" var="cat">
					<tr>
						<td><c:out value="${cat.id}" /></td>
						<td><c:out value="${cat.nome}" /></td>

						<td><a href="cadastroCategoria?acao=editar&user=${cat.id}"><img
								title="editar" alt="editar" src="resources/imgs/pen.png"
								width="22px" height="22px"> </a></td>
						<td><a href="cadastroCategoria?acao=delete&user=${cat.id}"><img
								title="exluir" alt="excluir" src="resources/imgs/trash.png"
								width="22px" height="22px"> </a></td>
					</tr>
				</c:forEach>
			<tbody>
		</table>
	</div>
</body>
</html>