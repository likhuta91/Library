<%@page import="by.lik.service.impl.UserServiceImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.message.welcome" var="message" />
<fmt:message bundle="${loc}" key="local.locbutton.name.ru"
	var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en"
	var="en_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.allBooks"
	var="allBooks_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.logOut"
	var="logOut_button" />
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

	<c:out value="${message}" />
	,
	<c:out value="${myUser.name}"></c:out>
	!

	<c:if test="${not empty sessionScope.myUser}">
		<form action="FrontController" method="post">
			<input type="hidden" name="command" value="logOut" /> <input
				type="submit" value="${logOut_button}" /><br />
		</form>
	</c:if>

	<br />

	<form action="FrontController" method="post">
		<input type="hidden" name="command" value="showAllBooks" /> <input
			type="submit" value="${allBooks_button}" /><br />
	</form>

	<c:out value="${requestScope.message}" />

	<c:if test="${not empty allBooks}">

		<table border="1" cellspacing="0" cellpadding="2">

			<tr>
				<td><c:out value="${allBooks_button}:"></c:out></td>
			</tr>

			<c:forEach items="${allBooks}" var="book">
				<tr>
					<td><c:out value="${book.title}" /></td>
				</tr>
			</c:forEach>

		</table>

	</c:if>

	<br />

	<table border="1" cellspacing="0" cellpadding="2">
		<tr>
			<td>Имя</td>
			<td>Фамилия</td>
			<td>Логин</td>
		</tr>


		<c:forEach items="${allUsers}" var="user">
			<tr>
				<td><c:out value="${user.name}" /></td>
				<td><c:out value="${user.surname}" /></td>
				<td><c:out value="${user.login}" /></td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>