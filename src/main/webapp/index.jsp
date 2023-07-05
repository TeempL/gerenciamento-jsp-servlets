<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/css/styles.css">
</head>
<body>
	<header>
		<h1>Portal de Login</h1>
	</header>
	<main>
		<form action="login" class="form_class" method="post">
			<span style="color: red;">${invalido}</span>
			<div class="form_div">
				<label>Login:</label> <input type="text"
					placeholder="Insira seu login" class="field_class"
					required="required" name="login"> <label>Senha:</label> <input
					type="password" placeholder="Insira sua senha" class="field_class"
					required="required" name="senha">
				<button type="submit" class="submit_class">Entrar</button>
			</div>

			<!--    
			<div class="info_div">
				<p>
					Ainda não é um usuário registrado? <a href="register/reg-form.php">Cadastre-se!</a>
				</p>
			</div>
			-->
		</form>
	</main>
	<footer> </footer>
</body>
</html>