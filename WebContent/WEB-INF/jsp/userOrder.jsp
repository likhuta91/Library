<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="by.lik.bean.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<style>
body {
	background-image: url(images/bground.jpg);
	background-color: #c7b39b;
}
</style>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.message.welcome" var="message" />
<fmt:message bundle="${loc}" key="local.locbutton.name.ru"
	var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en"
	var="en_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.myOrders"
	var="myOrders_button" />

<fmt:message bundle="${loc}" key="local.message.idOrders" var="idOrders" />
<fmt:message bundle="${loc}" key="local.message.status" var="status" />
<fmt:message bundle="${loc}" key="local.message.author" var="author" />
<fmt:message bundle="${loc}" key="local.message.title" var="title" />
<fmt:message bundle="${loc}" key="local.message.genre" var="genre" />

</head>

<body>

	<table>
		<tr>
			<td>
				<form action="FrontController" method="get">
					<input type="hidden" name="command" value="changeLocalization" />
					<input type="hidden" name="local" value="ru" /> <input
						type="submit" value="${ru_button}" />
				</form>
			</td>

			<td>
				<form action="FrontController" method="get">
					<input type="hidden" name="command" value="changeLocalization" />
					<input type="hidden" name="local" value="en" /> <input
						type="submit" value="${en_button}" />
				</form>
			</td>

		</tr>
	</table>

	<p>
		<font size="14"> <strong><c:out
					value="${myOrders_button}" /></strong></font>
	</p>

	<c:if test="${not empty requestScope.orders}">

		<table border="1" cellspacing="0" cellpadding="5">
			<tr>
				<td><c:out value="${idOrders}:"></c:out></td>
				<td><c:out value="${status}:"></c:out></td>
				<td><c:out value="${title}:"></c:out></td>
				<td><c:out value="${author}:"></c:out></td>
				<td><c:out value="${genre}:"></c:out></td>
			</tr>

			<c:forEach items="${requestScope.orders}" var="order">
				<tr>
					<td><c:out value="${order.id}" /></td>
					<td><c:out value="${order.status}" /></td>

					<td><table border="0" cellspacing="0" cellpadding="0">
							<c:forEach items="${order.books}" var="book">
								<tr>
									<td><c:out value="${book.title}" /></td>
								</tr>
							</c:forEach>
						</table></td>
					<td><table border="0" cellspacing="0" cellpadding="0">
							<c:forEach items="${order.books}" var="book">
								<tr>
									<td><c:out value="${book.author}" /></td>
								</tr>
							</c:forEach>
						</table></td>
					<td><table border="0" cellspacing="0" cellpadding="0">
							<c:forEach items="${order.books}" var="book">
								<tr>
									<td><c:out value="${book.genre}" /></td>
								</tr>
							</c:forEach>
						</table></td>
				</tr>
			</c:forEach>

		</table>

	</c:if>

	<c:if test="${empty requestScope.orders}">
		<c:out value="У вас нет ни одного заказа"></c:out>
	</c:if>

</body>
</html>