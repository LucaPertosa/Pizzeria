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
	<h1>Modifica Pizza</h1>
	<a href="<%request.getContextPath();%>creaPizza.jsp"> <%=request.getServletPath()%>,
		<%=request.getContextPath()%></a>
	<c:if test="${pizzaDaModificare != null}">
		<form method="post" action="dashboardServlet">
			<!-- Assumi "salvaModifichePizzaServlet" come il tuo URL per salvare le modifiche -->
			<input type="hidden" name="pizzaModificataId"
				value="${pizzaDaModificare.id}" /> <label for="nomePizza">Nome
				Pizza:</label> <input type="text" id="nomePizza" name="nomePizzaModificata"
				value="${pizzaDaModificare.getNome()}" /><br />
			<br /> <label for="ingredienti">Ingredienti:</label><br>
			<c:forEach items="${ingredienti}" var="ingrediente">
				<input type="checkbox" name="ingredientiModificati"
					value="${ingrediente.id}"
					<c:if test="${pizzaDaModificare.contieneIngrediente(ingrediente)}">checked</c:if>> ${ingrediente.nome}<br>
			</c:forEach>
			<br />
			<br /> <label for="impasto">Impasto:</label> <select id="impasto"
				name="impastoModificato">
				<c:forEach items="${impasti}" var="impasto">
					<option value="${impasto.getId()}"
						<c:if test="${impasto.getId() == pizzaDaModificare.getImpasto().getId()}">selected</c:if>>
						${impasto.getNome()}</option>
				</c:forEach>
			</select><br />
			<br /> <input type="submit" value="Salva Modifiche" />
		</form>
	</c:if>

</body>
</html>