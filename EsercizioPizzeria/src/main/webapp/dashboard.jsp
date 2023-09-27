<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String messaggio = (String) request.getAttribute("messaggio");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>
</head>
<body>
	
	<c:if test="${not empty sessionScope.authenticatedUser}">
		<h1>Benvenuto, ${sessionScope.authenticatedUser.getUsername()}!</h1>
	</c:if>

	<c:if test="${not empty sessionScope.pizze}">
		<p>Elenco delle pizze:</p>
		<table border="1">
			<thead>
				<tr>
					<th>Nome Pizza</th>
					<th>Ingredienti</th>
					<th>Impasto</th>
					<th colspan="2">Azioni</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${pizze}" var="pizza">
					<tr>
						<td>${pizza.getNome()}</td>
						<td><c:forEach items="${pizza.getIngredienti()}"
								var="ingrediente">
							${ingrediente.getNome()}<c:if test="${loop.last}">; </c:if>
							</c:forEach></td>
						<td>${pizza.getImpasto().getNome()}</td>
						<td>
							<form method="post" action="dashboardServlet">
								<!-- Assumi "eliminaPizza" come il tuo URL di gestione dell'eliminazione -->
								<input type="hidden" name="pizzaId" value="${pizza.id}" /> <input
									type="submit" value="Elimina" />
							</form>
						</td>
						<td>
							<form method="post" action="dashboardServlet">
								<!-- Assumi "eliminaPizza" come il tuo URL di gestione dell'eliminazione -->
								<input type="hidden" name="modPizzaId" value="${pizza.id}" /> <input
									type="submit" value="Modifica" />
							</form>					
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
<a href="<% request.getContextPath(); %>creaPizza.jsp"> Crea uova pizza </a>
	<script type="text/javascript">
		setTimeout(function() {
			var div = document.getElementById('messaggio');
			if (div) {
				div.innerHTML = '';
			}
		}, 5000)
	</script>
</body>
</html>