<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<fmt:message bundle="${loc}" key="local.message.registration"
	var="registration" />
<fmt:message bundle="${loc}" key="local.message.login" var="login" />
<fmt:message bundle="${loc}" key="local.message.password" var="password" />
<fmt:message bundle="${loc}" key="local.message.name" var="name" />
<fmt:message bundle="${loc}" key="local.message.surname" var="surname" />
<fmt:message bundle="${loc}" key="local.message.city" var="city" />
<fmt:message bundle="${loc}" key="local.message.country" var="country" />
<fmt:message bundle="${loc}" key="local.message.email" var="email" />
<fmt:message bundle="${loc}" key="local.locbutton.name.ru"
	var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en"
	var="en_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.reg"
	var="reg_button" />

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
		<c:out value="${sessionScope.message}" />
		<%
			request.getSession().removeAttribute("message");
		%>

		<form action="FrontController" method="post">
			<input type="hidden" name="command" value="registration" />

			<c:out value="${login}" />
			: <br /> <input required type="text" name="login" value="" />*<br />

			<c:out value="${password}" />
			: <br /> <input required type="password" name="password" value="" />*<br />

			<c:out value="${name}" />
			: <br /> <input required type="text" name="name" value="" />*<br />
			<c:out value="${surname}" />
			: <br /> <input required type="text" name="surname" value="" />*<br />
			<c:out value="${country}" />
			: <br /> <input type="text" name="country" value="" /><br />
			<c:out value="${city}" />
			: <br /> <input type="text" name="city" value="" /><br />
			<c:out value="${email}" />
			: <br /> <input required type="text" name="email" value="" />*<br />
			<c:out value="* - ${registration}" />
			<br /> <input type="submit" value="${reg_button}" />
		</form>
	</center>

</body>
</html>