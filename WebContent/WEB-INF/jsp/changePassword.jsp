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
<fmt:message bundle="${loc}" key="local.message.welcome" var="message" />
<fmt:message bundle="${loc}" key="local.locbutton.name.ru"
	var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en"
	var="en_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.enter"
	var="enter_button" />

<fmt:message bundle="${loc}" key="local.message.newPassword"
	var="newPassword" />

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
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />

	<center>
		<c:out value="${newPassword}" />
		: <br />
		<form action="FrontController" method="post">
			<input type="hidden" name="command" value="changeUserPassword" /> <input
				required type="text" name="password" value="" /> <input
				type="submit" value="${enter_button}" />
		</form>
		<c:out value="${sessionScope.message}" />
		<%
			request.getSession().removeAttribute("message");
		%>
	</center>
</body>
</html>