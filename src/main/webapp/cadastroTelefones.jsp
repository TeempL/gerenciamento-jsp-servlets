<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/css/cadastrosz.css">
<link rel="stylesheet" href="resources/css/tableUsuarioss.css">
	

</head>
<body>
	<header>
		<h1>cadastro de telefones</h1>
	</header>
	<a
		style="font-size: 20px; text-decoration: none; padding-right: 15px; padding-left: 10px; color: lime;"
		href="acessoliberado.jsp">Início</a>
	<a style="font-size: 20px; text-decoration: none; color: red;"
		href="index.jsp">Sair</a>

	<form id="form" action="cadastroTelefones" method="post">
		<h3 style="color: green; text-align: center; font-size: 18px">${msg}</h3>
		<table>
			<tr>
				<td>
					<h3>ID:</h3> <input type="text" id="id" readonly="readonly"
					placeholder="ID" name="id" value="${usuario.id}">
				</td>
				<td>
					<h3>Usuario:</h3> <input type="text" id="username"
					readonly="readonly" placeholder="username:" name="Usuario"
					value="${usuario.nome}">
				</td>
			</tr>
			<tr>
				<td>
					<h3>Telefone:</h3> <input type="number" id="telefone"
					required="required" placeholder="Telefone:" name="telefone"
					value="${usuario.telefone}">
				</td>
				<td>
					<h3>Tipo:</h3>
					<select class="tipo" id="tipo" name="tipo">
						<option>Fixo</option>
						<option>Celular</option>
						<option>Contato</option>
					</select>
				</td>
				
			</tr>
			<tr>
				<td> <input class="button" type="submit" id="cadastrar"
					value="Cadastrar"></td>
				<td><input class="button2" type="submit" name="voltar" value="Cancelar" onclick="document.getElementById('form').action = 'cadastroTelefones?acao=cancelar'"></td>
			</tr>
		</table>
	</form>

	<div class="table-wrapper">
		<table class="fl-table">
			<thead>
				<tr>
					<th>ID</th>
					<th>Numero</th>
					<th>Tipo</th>
					<th>Excluir</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${telefones}" var="tel">
					<tr>
						<td><c:out value="${tel.id}" /></td>
						<td><c:out value="${tel.numero}" /></td>
						<td><c:out value="${tel.tipo}" /></td>						
						<td><a href="cadastroTelefones?acao=deleteFone&fone=${tel.id}"><img
								title="exluir" alt="excluir" src="resources/imgs/trash.png"
								width="22px" height="22px"> </a></td>
					</tr>
				</c:forEach>
			<tbody>
		</table>
	</div>
</body>
</html>