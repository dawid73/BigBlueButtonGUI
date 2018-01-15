<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/signin.css"
	rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Fenice - Meeting in BigBlueButton</title>
</head>
<body>

	<div class="container">



	<%if(request.getParameter("info")!=null){ 
		String dane = request.getParameter("info"); 
	 
		if(dane.equals("wylogowany")){
	%>
		<div class="row">
			<div class="col-md-4"></div>
			<div class="alert alert-info col-md-4">Zostałeś wylogowany</div>
			<div class="col-md-4"></div>
		</div>
	<% 
		}
	}
%>

	<%if(request.getParameter("info")!=null){ 
		String dane = request.getParameter("info"); 
	 
		if(dane.equals("brakuprawnien")){
	%>
		<div class="row">
			<div class="col-md-4"></div>
			<div class="alert alert-danger col-md-4">Brak uprawnień !!</div>
			<div class="col-md-4"></div>
		</div>
	<% 
		}
	}
%>

	<%if(request.getParameter("info")!=null){ 
		String dane = request.getParameter("info"); 
	 
		if(dane.equals("zlehaslo")){
	%>
		<div class="row">
			<div class="col-md-4"></div>
			<div class="alert alert-danger col-md-4">Błędne hasło !!</div>
			<div class="col-md-4"></div>
		</div>
	<% 
		}
	}
%>

		<div class="row">
			<form class="form-signin" method="post" action="login">

				<h2 class="form-signin-heading">Zaloguj się !!</h2>
				<label for="inputEmail" class="sr-only">Login</label> <input
					type="text" id="username" name="username" class="form-control"
					placeholder="Login" required="" autofocus=""> <label
					for="inputPassword" class="sr-only">Hasło</label> <input
					type="password" id="inputPassword" class="form-control"
					placeholder="Hasło" required="" name="password">
				<div class="checkbox">
					<!-- <label> <input type="checkbox" value="remember-me" checked>
						Jestem użytkonikiem domenowym -->
					</label> <label>UWAGA!! Jeżeli nie masz konta w domenie, podaj
						login i hasło otrzymane od organizatora spotkania. </label>
				</div>
				<button class="btn btn-lg btn-primary btn-block" type="submit">Zaloguj</button>
			</form>
		</div>
	</div>
</body>
</html>