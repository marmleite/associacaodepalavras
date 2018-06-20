<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="css/bootstrap-4/bootstrap.css"></link>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="css/bootstrap-4/bootstrap.js"></script>
	<title>Detector de Mentiras</title>
</head>

<body>
	
	<div class="container">
	<br/>
	<br/>
	<h1>Acesso visualiação de dados: </h1>
		<form action="login" method="POST">
			<div class="form-group">
				<label>Login</label>
				<input type="text" name="login" class="form-control" />
			</div>
			<div class="form-group">
				<label>Senha</label>
				<input type="password" name="senha" class="form-control" />
			</div>
			
			<input type="submit" class="btn btn-primary" value="Acessar"/>
		</form>
		
		
	</div>
</body>
</html>