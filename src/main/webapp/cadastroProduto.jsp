<%@page import="beans.ProdutoBeans"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/css/cadastrosz.css">
<link rel="stylesheet" href="resources/css/tableUsuarioss.css">
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js" type="text/javascript"></script>
  <script src="resources/javascript/jquery.maskMoney.min.js" type="text/javascript"></script>
</head>
<body>

	<header>
		<h1>cadastro produto</h1>
	</header>
	
	<a
		style="font-size: 20px; text-decoration: none; padding-right: 15px; padding-left: 10px; color: lime;"
		href="acessoliberado.jsp">Início</a>
	<a style="font-size: 20px; text-decoration: none; color: red;"
		href="index.jsp">Sair</a>

	<form id="form" action="cadastroProduto" method="post">

		<p>
			<input type="text" id="nome" class="floatLabel" required="required"
				placeholder="Nome" name="nome" value="${user.nome}">
		</p>
		<p>

			<input type="text" id="quantidade" class="floatLabel"
				required="required" placeholder="Quantidade" name="quantidade"
				value="${user.quantidade}" maxlength="9" >
		</p>
		<p>
			<input type="text" id="valor" data-thousands="." data-decimal="," data-precision="2" class="floatLabel" required="required"
				maxlength="10" placeholder="Valor R$" name="valor" value="${user.valorEmTexto}">
		</p>
		<p>
			<h3>Escolher categoria:</h3>
			<select style="margin-top: 2px; width: 150px; height: 30px" id="categorias" name="categoria_id">
				<c:forEach items="${categorias}" var="cat">
					<option value="${cat.id}" id="${cat.id}"
						<c:if test="${cat.id == user.categoria_id}">
							<c:out value="selected=selected"/>
						</c:if>>
						${cat.nome}
					</option>
				</c:forEach>
			</select>
			<a href="cadastroCategoria?acao=cadastrar"><img width="22px" height="22px" alt="Adicionar Categoria" title="Adicionar Categoria" src="resources/imgs/add.png"></a>
		</p>
		<p>
			<input type="hidden" id="id" class="floatLabel" name="id"
				value="${user.id}">
		</p>

		<input type="hidden" id="redirect">
		<p>
		
			<input class="button" type="submit" id="cadastrar" value="Cadastrar produto"> 
			<input class="button2"type="submit" id="cancelar" value="Cancelar" onclick="document.getElementById('form').action = 'cadastroProduto?acao=reset'">
		</p>

	</form>

	<div class="table-wrapper">
		<table class="fl-table">

			<caption>Produtos cadastrados</caption>
			<thead>
				<tr>
					<th>ID</th>
					<th>Nome</th>
					<th>Quantidade</th>
					<th>Valor</th>
					<th>Categoria</th>
					<th>Editar</th>
					<th>Delete</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${usuarios}" var="users">
					<tr>
						<td><c:out value="${users.id}" /></td>
						<td><c:out value="${users.nome}" /></td>
						<td><c:out value="${users.quantidade}" /></td>
						<td><fmt:formatNumber type="number" maxFractionDigits="2"  value="${users.valor}"></fmt:formatNumber></td>
						<td><c:out value="${users.categoria_id}" /></td>
						
						<td><a href="cadastroProduto?acao=editar&user=${users.id}"><img
								title="editar" alt="editar" src="resources/imgs/pen.png"
								width="22px" height="22px"> </a></td>
						<td><a href="cadastroProduto?acao=delete&user=${users.id}"><img
								title="exluir" alt="excluir" src="resources/imgs/trash.png"
								width="22px" height="22px"> </a></td>
					</tr>
				</c:forEach>
			<tbody>
		</table>
	</div>
</body>

<script type="text/javascript">
	$(function() {
	    $('#valor').maskMoney();
	  })
</script>


</html>