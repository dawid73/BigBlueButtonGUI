<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fenice - Meeting in BigBlueButton</title>
</head>
<body>

<form action="create" method="get">
Nazwa pokoju: <input type="text" name="nameRoom"><br>
Nazwa administratora: <input type="text" name="adminName"><br>
Hasło administratora: <input type="password" name="adminPass"><br>
<input type="submit" value="Utwórz">

</form>

${name}

</body>
</html>