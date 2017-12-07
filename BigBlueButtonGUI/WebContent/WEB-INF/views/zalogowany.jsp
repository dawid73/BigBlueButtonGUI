<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	<%
		if (request.getParameter("info") != null) {
			String dane = request.getParameter("info");

			if (dane.equals("closeroom")) {
	%>
	<div class="row">
		<div class="col-md-4"></div>
		<div class="alert alert-info col-md-4">Zamknięto pokój</div>
		<div class="col-md-4"></div>
	</div>
	<%
		}
		}
	%>

	<div class="container">
		<div class="row">
			<div class="col-2">
				<header class="masthead"> Witaj <br>
				<b>${imienazwisko}</b>, <br>
				jesteś zalogowany. </header>


				<a href="logout"><button type="button" class="btn btn-warning">Wyloguj</button></a></br>
				</br>

			</div>
			<div class="col-8">

				<form class="form-signin" method="get" action="create">
					<h2 class="form-signin-heading">Tworzenie pokoju</h2>
					<input type="text" id="nameRoom" name="nameRoom"
						class="form-control" placeholder="Nazwa pokoju" required=""
						autofocus=""> <br> <input type="hidden"
						name="imienazwisko" value="${imienazwisko}">
					<button class="btn btn-lg btn-primary btn-block" type="submit">Utwórz</button>
				</form>






<table class="table table-hover">
  <thead>
    <tr>
      <th scope="col">Nazwa pokoju</th>
      <th scope="col">Połącz</th>
      <th scope="col">Wyślij zaproszenie</th>
      <th scope="col">Zamknij</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach items="${requestScope.listRooms}" var="room">
  	<tr>
	<th scope="row"><c:out value="${room.meetingName}"></c:out></th>
	<td>
		<form class="form-signin" method="get" action="connect"
					target="_blank">
					<input type="hidden" name="idmeeting" value="${room.meetingID}">
					<input type="hidden" name="imienazwisko" value="${imienazwisko}">
					<button class="btn btn-primary btn-sm" type="submit">Połącz</button>
			</form>
	</td>
	<td>ZAPROSZNIE</td>
	<td>
		<form class="form-signin" action="close" method="get">
					<input type="hidden" name="idmeeting" value="${room.meetingID}">
					<button class="btn btn-danger btn-sm" type="submit">Zamknij</button>
		</form>
	
	</td>
	</tr>
   </c:forEach>
  
  
  </tbody>
</table>




			</div>
		</div>
	</div>
</body>
</html>