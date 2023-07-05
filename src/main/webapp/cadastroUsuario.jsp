<%@page import="beans.LoginBeans"%>
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

<!-- Adicionando JQuery -->
<script src="https://code.jquery.com/jquery-3.6.4.min.js"
	integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8="
	crossorigin="anonymous">
	
</script>
</head>
<body>
	<header>
		<h1>cadastro Usuario</h1>
	</header>
	<a
		style="font-size: 20px; text-decoration: none; padding-right: 15px; padding-left: 10px; color: lime;"
		href="acessoliberado.jsp">Início</a>
	<a style="font-size: 20px; text-decoration: none; color: red;"
		href="index.jsp">Sair</a>

	<form id="form" action="cadastroUsuario" method="post"
		onsubmit="return validarCampo() ? true : false" enctype="multipart/form-data" >
		<h3 style="color: green; text-align: center; font-size: 18px">${msg}</h3>
		<h3 style="color: red; text-align: center; font-size: 18px">${error}</h3>
		<table>
			<tr>
				<td><input type="text" id="username" required="required"
					placeholder="username: *" name="username" value="${user.nome}"></td>
				<td><input type="text" id="cep" size="10" maxlength="9" class=" floatLabel" name="cep"
					placeholder="Cep:" onblur="validacep()" value="${user.cep}"></td>
			</tr>

			<tr>
				<td><input type="text" id="login" placeholder="Login: *"
					required="required" name="login" value="${user.login}"></td>
				<td><input type="text" id="rua" class="floatLabel" name="rua"
					placeholder="Rua:" maxlength="50" size="50" value="${user.rua}"></td>
			</tr>

			<tr>
				<td><input type="password" id="senha" class="floatLabel"
					required="required" placeholder="Senha: *" name="senha"
					value="${user.senha}"></td>
				<td><input type="text" id="bairro" class="floatLabel"
					name="bairro" size="40" placeholder="Bairro:" value="${user.bairro}"></td>
			</tr>

			<tr>
				<td><input type="text" id="uf" size="2" class="floatLabel" name="uf"
					placeholder="Estado:" value="${user.uf}"></td>
				<td>
					
					<input type="text" id="cidade" class="floatLabel"
					name="cidade" maxlength="50" size="40"placeholder="Cidade:" value="${user.cidade}">
				</td>
			</tr>

			<tr>
				<td><input type="text" id="ibge" class="floatLabel" name="ibge"
					placeholder="IBGE:" size="8" value="${user.ibge}"></td>
					
				<td><input type="text" id="complemento" class="floatLabel" name="complemento"
					placeholder="Complemento: " size="8" value="${user.complemento}"></td>
			</tr>
			
			<tr>

				<td>
					<h3>Foto:</h3>
					<input type="file" id="foto" class="floatLabel" name="foto"
					value="foto"> <input type="hidden" name="fotoTemp" value="${user.fotoBase64}"></td>
					<td><input type="hidden" name="contentTemp" value="${user.contentType}"></td>
			</tr>
			<tr>
				<td>
					<h3>Curriculo</h3>
					<input type="file" id="curriculo" class="floatLabel" name="curriculo"
					value="curriculo"></td>
					
					
					
					<td>
					<h3 style="text-align: center; font-size: 15px">Ativo</h3>
				 	<input type="checkbox" class="floatLabel" id="ativo" name="ativo" 
				 	 
				 		<%
				 			if(request.getAttribute("user") != null){
					 			LoginBeans loginBeans = (LoginBeans) request.getAttribute("user");
					 			if(loginBeans.isAtivo()){
					 				out.print(" ");
					 				out.print("checked=\"checked\"");
					 				out.print(" ");
					 			}
							
				 			}
				 		%>				 	
				 	/> 
				 </td>
			</tr>
			
			<tr>
				<td>	
					<h3 style="font-size: 13px;text-align: center; margin-top: 10px">Perfil: </h3>
					<select style="margin-left: 33%; margin-top: 10px; width: 150px" name="perfil">
						<option value="nao_informado"
							<%
								if(request.getAttribute("user") != null){
									LoginBeans loginBeans = (LoginBeans) request.getAttribute("user");
									
									if(loginBeans.getPerfil().equalsIgnoreCase("nao_informado")){
										out.print(" ");
										out.print("selected=\"selected\"");
										out.print(" ");
									}
									
								}
							
							%>
						
						>[----SELECIONE----]</option>
						<option value="programador" 
							<%
								if(request.getAttribute("user") != null){
									LoginBeans loginBeans = (LoginBeans) request.getAttribute("user");
									
									if(loginBeans.getPerfil().equalsIgnoreCase("programador")){
										out.print(" ");
										out.print("selected=\"selected\"");
										out.print(" ");
									}
									
								}
							
							%>
						
						>Programador</option>
						<option value="administrador" 
							<%
								if(request.getAttribute("user") != null){
									LoginBeans loginBeans = (LoginBeans) request.getAttribute("user");
									
									if(loginBeans.getPerfil().equalsIgnoreCase("administrador")){
										out.print(" ");
										out.print("selected=\"selected\"");
										out.print(" ");
									}
									
								}
							
							%>
						
						>Administrador</option>
						<option value="gerente" 
							<%
								if(request.getAttribute("user") != null){
									LoginBeans loginBeans = (LoginBeans) request.getAttribute("user");
									
									if(loginBeans.getPerfil().equalsIgnoreCase("gerente")){
										out.print(" ");
										out.print("selected=\"selected\"");
										out.print(" ");
									}
									
								}
							
							%>
						
						>Gerente</option>
						<option value="cliente"
							<%
								if(request.getAttribute("user") != null){
									LoginBeans loginBeans = (LoginBeans) request.getAttribute("user");
									
									if(loginBeans.getPerfil().equalsIgnoreCase("cliente")){
										out.print(" ");
										out.print("selected=\"selected\"");
										out.print(" ");
									}
									
								}
							
							%>
						
						>Cliente</option>
						<option value="analista"
							<%
								if(request.getAttribute("user") != null){
									LoginBeans loginBeans = (LoginBeans) request.getAttribute("user");
									
									if(loginBeans.getPerfil().equalsIgnoreCase("analista")){
										out.print(" ");
										out.print("selected=\"selected\"");
										out.print(" ");
									}
									
								}
							
							%>
						>Análista</option>

					</select>
				</td>
			</tr>
			
			<tr>
				<td>
				 	<input  type="hidden"/>
				 </td>
				 	<td>
						<h3 style="text-align: center; font-size: 13px; margin-top: -30px">Masculino:</h3>
				 		<input style="margin-top: 5px"  type="radio" name="sexo" value="Masculino" 
				 			<% 
				 				if(request.getAttribute("user") != null){
				 					LoginBeans loginBeans = (LoginBeans) request.getAttribute("user");
				 					
				 					if(loginBeans.getSexo().equalsIgnoreCase("Masculino")){
				 						out.print("checked=\"checked\"");					
				 					}
				 				}
				 			%>
				 		
				 		/>
				 		<h3 style="text-align: center; font-size: 13px; margin-top: 10px">Feminino:</h3>
				 		<input style="margin-top: 5px" type="radio" name="sexo" value="Feminino"
				 		
				 			<% 
				 				if(request.getAttribute("user") != null){
				 					LoginBeans loginBeans = (LoginBeans) request.getAttribute("user");
				 					
				 					if(loginBeans.getSexo().equalsIgnoreCase("Feminino")){
				 						out.print("checked=\"checked\"");					
				 					}
				 				}
				 			%>
				 		
				 		/>
				 	</td>
	
			</tr>

			<tr>
				<td>
				
				<input class="button"  type="submit" id="cadastrar"
					value="Cadastrar minha conta"></td>
				<td><input class="button2" type="submit" id="submit"
					value="Cancelar"
					onclick="document.getElementById('form').action = 'cadastroUsuario?acao=reset'">
					<input type="hidden" id="id" readonly="readonly" class="floatLabel"
					name="id" value="${user.id}"></td>
			</tr>
		</table>

	</form>
	
	<form id="form2" method="post" action="pesquisarUsuario">
	
		<h3 style="text-align: center; font-size: 17px">Buscar usuario:</h3>
		<input class="pesquisa" type="text" placeholder="Pesquisar: " class="floatLabel" name="pesquisa">
		<h3 style="text-align: center; font-size: 17px">Buscar Por:</h3>
		<select style="margin-left: 33%; margin-top: 10px; width: 150px" name="selecao">
			<option style="text-align: center;" name="nome" >Nome</option>
			<option style="text-align: center;"name="cep">Cep</option>
			<option style="text-align: center;"name="perfil">Perfil</option>
		
		</select>
		<input type="submit" class="button" name="enivar" value="Buscar">
	
	</form>
	

	<div class="table-wrapper">
		<table class="fl-table">
			<thead>
				<tr>
					<th>ID</th>
					<th>Nome</th>
					<th>Login</th>
					<th>Imagem</th>
					<th>Arquivo</th>
					<th>CEP</th>
					<th>UF</th>
					<th>Sexo</th>
					<th>Perfil</th>
					<th>Editar</th>
					<th>Delete</th>
					<th>Telefones</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${usuarios}" var="users">
					<tr>
						<td><c:out value="${users.id}" /></td>
						<td><c:out value="${users.nome}" /></td>
						<td><c:out value="${users.login}" /></td>
			
						<c:if test="${users.fotoBase64Miniatura.isEmpty() == false}">
							<td><a href="cadastroUsuario?acao=download&tipo=imagem&user=${users.id}"><img width="35px" height="35px" alt="Imagem user" title="Imagem do Usuario" src="<c:out value="${users.fotoBase64Miniatura}"/>"></a></td>
						</c:if>
						
						<c:if test="${users.fotoBase64Miniatura == null}">
							<td><img width="35px" height="35px" alt="Imagem user" title="Imagem do Usuario" src="<c:out value="resources/imgs/blumenau.jpg"/>"></td>
						</c:if>
						
						<c:if test="${users.curriculoBase64.isEmpty() == false}">
							<td> <a href="cadastroUsuario?acao=download&tipo=arquivo&user=${users.id}"><img width="35px" height="35px" alt="Imagem user" title="Imagem do Usuario" src="<c:out value="resources/imgs/pdfIcon.png"/>"></a></td>
						</c:if>
						
						<c:if test="${users.curriculoBase64 == null}">
							<td><img width="35px" height="35px" alt="Imagem user" title="Imagem do Usuario" src="<c:out value="resources/imgs/blumenau.jpg"/>"></td>
						</c:if>
						<td><c:out value="${users.cep}" /></td>
						<td><c:out value="${users.uf}" /></td>
						<td><c:out value="${users.sexo}" /></td>
						<td><c:out value="${users.perfil}" /></td>
						<td><a href="cadastroUsuario?acao=editar&user=${users.id}">
								<img title="editar" alt="editar" src="resources/imgs/pen.png"
								width="22px" height="22px">
						</a></td>
						<td><a onclick="return confirm('Deseja realizar a exclusao deste usuario?')" href="cadastroUsuario?acao=delete&user=${users.id}"><img
								title="exluir" alt="excluir" src="resources/imgs/trash.png"
								width="22px" height="22px"> </a></td>
						<td><a href="cadastroTelefones?acao=addFone&user=${users.id}"><img
								title="Cadastro de telefones" alt="cadastro Telefones"
								src="resources/imgs/fonegreen.png" width="22px" height="22px">
						</a></td>
					</tr>
				</c:forEach>
			<tbody>
		</table>
	</div>

	<script type="text/javascript">

		function validacep() {
			var cep = $("#cep").val();
			$.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?",
			function(dados) {
				if (!("erro" in dados)) {
					$("#rua").val(dados.logradouro);
					$("#bairro").val(dados.bairro);
					$("#cidade").val(dados.localidade);
					$("#uf").val(dados.uf);
					$("#ibge").val(dados.ibge);
				} 
				else {
					$("#rua").val('');
					$("#bairro").val('');
					$("#cidade").val('');
					$("#uf").val('');
					$("#ibge").val('');
					alert("CEP não encontrado.");
				}
			});
		}
	</script>
</body>
</html>