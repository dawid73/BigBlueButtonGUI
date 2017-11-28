<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<a href="${link}" target="_blank"><button>Polacz</button></a>
	
	<form action="close" method="get">
		<input type="hidden" name="idmeeting" value="${idFromXml}">
		<input type="submit" value="zakoncz">
	</form>
</body>
</html>