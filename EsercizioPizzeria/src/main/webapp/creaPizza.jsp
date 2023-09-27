<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>Crea una Nuova Pizza</h2>
	<form action="dashboardServlet/creaPizza" method="post">
		<label for="nomePizza">Nome Pizza:</label> <input type="text"
			id="nomePizza" name="nomePizza" required><br> <br>
		<label for="impasto">Tipo di Impasto:</label> <select id="impasto"
			name="impasto" required>
			<c:forEach items="${sessionScope.impasti}" var="impasto">
				<option value="${impasto.getId()}">${impasto.getNome()}</option>
			</c:forEach>
		</select><br> <br> <label>Ingredienti:</label><br>
		<c:forEach items="${ingredienti}" var="ingrediente">
			<input type="checkbox" name="ingredienti" value="${ingrediente.id}"> ${ingrediente.nome}<br>
		</c:forEach>

		<br> <input type="submit" value="Crea Pizza">
	</form>
</body>
</html>