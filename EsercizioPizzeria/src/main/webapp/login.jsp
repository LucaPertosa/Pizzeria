<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Hello JSP and Servlet!</h1>
	<form action="loginServlet" method="post">
		Enter your name: <input type="text" name="username" size="20"> <br>
		Enter your password: <input type="password" name="password" size="20"> <br>
		<input type="submit" value="Call Servlet" />
	</form>
</body>
</html>