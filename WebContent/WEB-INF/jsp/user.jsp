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
<fmt:message bundle="${loc}" key="local.locbutton.name.allBooks"
	var="allBooks_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.allUsers"
	var="allUsers_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.logOut"
	var="logOut_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.enter"
	var="enter_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.changePassword"
	var="changePassword_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.next"
	var="next_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.previous"
	var="previous_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.search"
	var="search_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.basket"
	var="basket_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.addToBasket"
	var="addToBasket_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.delete"
	var="delete_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.myOrders"
	var="myOrders_button" />

<fmt:message bundle="${loc}" key="local.message.id" var="id" />
<fmt:message bundle="${loc}" key="local.message.login" var="login" />
<fmt:message bundle="${loc}" key="local.message.name" var="name" />
<fmt:message bundle="${loc}" key="local.message.surname" var="surname" />
<fmt:message bundle="${loc}" key="local.message.city" var="city" />
<fmt:message bundle="${loc}" key="local.message.country" var="country" />
<fmt:message bundle="${loc}" key="local.message.email" var="email" />
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


	<form action="FrontController" method="get">
		<input type="hidden" name="command" value="goToGsp" /> <input
			type="hidden" name="gspName" value="/WEB-INF/jsp/basket.jsp" /> <input
			type="submit" value="${basket_button}" />
	</form>
	<form action="FrontController" method="get">
		<input type="hidden" name="command" value="takeUserOrders" /><input
			type="submit" value="${myOrders_button}" />
	</form>


	<br />

	<c:out value="${message}" />
	,

	<c:out value="${myUser.name}"></c:out>
	!

	<br />

	<form action="FrontController" method="get">

		<input type="hidden" name="gspName"
			value="/WEB-INF/jsp/changePassword.jsp" /> <select name="command"
			size="1">

			<option value="logOut">
				<c:out value="${logOut_button}"></c:out>
			</option>

			<option value="goToGsp">
				<c:out value="${changePassword_button}"></c:out>
			</option>

		</select> <input type="submit" value="${enter_button}" /><br />
	</form>

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

	<center>

		<form action="FrontController" method="get">
			<input type="hidden" name="command" value="searchBook" /> <input
				type="text" name="searchBook" value="" /> <input type="submit"
				value="${search_button}" />
		</form>

		<c:choose>
			<c:when test="${sessionScope.myUser.status=='admin'}">
				<form action="FrontController" method="get">
					<select name="command" size="1">
						<option value="allBooks">

							<c:out value="${allBooks_button}"></c:out>
						</option>
						<option value="allUsers">
							<c:out value="${allUsers_button}"></c:out>
						</option>
					</select> <input type="submit" value="${enter_button}" /><br />
				</form>
			</c:when>

			<c:when test="${sessionScope.myUser.status=='user'}">
				<form action="FrontController" method="get">
					<select name="command" size="1">
						<option value="allBooks">
							<c:out value="${allBooks_button}"></c:out>
						</option>
					</select> <input type="submit" value="${enter_button}" /><br />
				</form>
			</c:when>
		</c:choose>
	</center>

	<c:if test="${not empty requestScope.allBooks}">
		<form action="FrontController" method="get">
			<table border="0,5" cellspacing="0" cellpadding="5">

				<tr>
					<td><c:out value="${id}:"></c:out></td>
					<td><c:out value="${title}:"></c:out></td>
					<td><c:out value="${author}:"></c:out></td>
					<td><c:out value="${genre}:"></c:out></td>
					<td><c:out value="${statuss}:"></c:out></td>
					<td><c:out value=""></c:out></td>
				</tr>

				<c:forEach items="${requestScope.allBooks}" var="book">
					<tr>
						<td><c:out value="${book.id}" /></td>
						<td><c:out value="${book.title}" /></td>
						<td><c:out value="${book.author}" /></td>
						<td><c:out value="${book.genre}" /></td>
						<td><c:out value="${book.status}" /></td>
						<c:if test="${book.status=='free'}">
							<td><input type="checkbox" name="id" value="${book.id}"></td>
						</c:if>
					</tr>
				</c:forEach>

			</table>
			<input type="hidden" name="command" value="addBookToBasket" /> <input
				type="submit" value="${addToBasket_button}" />
		</form>
	</c:if>

	<c:if test="${not empty requestScope.allUsers}">
		<form action="FrontController" method="get">
			<table border="0" cellspacing="0" cellpadding="5">

				<tr>
					<td><c:out value="${id}:"></c:out></td>
					<td><c:out value="${login}:"></c:out></td>
					<td><c:out value="${name}:"></c:out></td>
					<td><c:out value="${surname}:"></c:out></td>
					<td><c:out value="${city}:"></c:out></td>
					<td><c:out value="${country}:"></c:out></td>
					<td><c:out value="${email}:"></c:out></td>
					<td><c:out value="${statuss}:"></c:out></td>
					<td><c:out value=""></c:out></td>
				</tr>

				<c:forEach items="${requestScope.allUsers}" var="user">
					<tr>
						<td><c:out value="${user.id}" /></td>
						<td><c:out value="${user.login}" /></td>
						<td><c:out value="${user.name}" /></td>
						<td><c:out value="${user.surname}" /></td>
						<td><c:out value="${user.city}" /></td>
						<td><c:out value="${user.country}" /></td>
						<td><c:out value="${user.email}" /></td>
						<td><c:out value="${user.status}" /></td>
						<td><input type="checkbox" name="id" value="${user.id}"></td>
					</tr>
				</c:forEach>
			</table>
			<input type="hidden" name="command" value="deleteUserAccount" /> <input
				type="submit" value="${delete_button}" />
		</form>
	</c:if>

	<center>
		<c:if test="${not empty sessionScope.currentPageNumber}">
			<table>
				<tr>

					<c:if test="${sessionScope.currentPageNumber>1}">
						<td>
							<form action="FrontController" method="get">
								<input type="hidden" name="command"
									value="${sessionScope.command}" /> <input type="hidden"
									name="currentPageNumber"
									value="${sessionScope.currentPageNumber-1}" /> <input
									type="submit" value="${previous_button}" />
							</form>
						</td>
					</c:if>
					<td><c:out value="${sessionScope.currentPageNumber}"></c:out></td>

					<c:if
						test="${sessionScope.currentPageNumber<sessionScope.numberOfAllPages}">
						<td>
							<form action="FrontController" method="get">
								<input type="hidden" name="command"
									value="${sessionScope.command}" /> <input type="hidden"
									name="currentPageNumber"
									value="${sessionScope.currentPageNumber+1}" /> <input
									type="submit" value="${next_button}" />
							</form>
						</td>
					</c:if>

				</tr>
			</table>

			<%
				request.getSession().removeAttribute("command");
					request.getSession().removeAttribute("currentPageNumber");
					request.getSession().removeAttribute("numberOfAllPages");
			%>

		</c:if>
	</center>
</body>
</html>