<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="by.lik.bean.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />

<fmt:message bundle="${loc}" key="local.locbutton.name.ru"
	var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en"
	var="en_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.order"
	var="order_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.basket"
	var="basket_button" />

<fmt:message bundle="${loc}" key="local.message.basket" var="basket" />
<fmt:message bundle="${loc}" key="local.message.id" var="id" />
<fmt:message bundle="${loc}" key="local.message.status" var="statuss" />
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

	<br />
	<p>
		<font size="20"> <strong><c:out
					value="${basket_button}" /></strong></font>
	</p>

	<c:if test="${empty sessionScope.allBooks}">
		<c:out value="Ваша корзина пуста"></c:out>
	</c:if>

	<c:out value="${sessionScope.message}" />

	<%
		request.getSession().removeAttribute("message");
	%>

	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />

	<c:if test="${not empty sessionScope.allBooks}">
		<form action="FrontController" method="get">
			<table border="0" cellspacing="0" cellpadding="5">

				<tr>
					<td><c:out value="${id}:"></c:out></td>
					<td><c:out value="${title}:"></c:out></td>
					<td><c:out value="${author}:"></c:out></td>
					<td><c:out value="${genre}:"></c:out></td>
					<td><c:out value="${statuss}:"></c:out></td>
					<td><c:out value=""></c:out></td>
				</tr>

				<c:forEach items="${sessionScope.allBooks}" var="book">
					<tr>
						<td><c:out value="${book.id}" /></td>
						<td><c:out value="${book.title}" /></td>
						<td><c:out value="${book.author}" /></td>
						<td><c:out value="${book.genre}" /></td>
						<td><c:out value="${book.status}" /></td>
						<td><input type="checkbox" name="checkbox" value="newsletter"></td>
					</tr>
				</c:forEach>

			</table>
			<input type="hidden" name="command" value="orderBooks" /> <input
				type="submit" value="${order_button}" />
		</form>
	</c:if>

</body>
</html>