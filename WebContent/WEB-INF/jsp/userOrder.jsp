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

	<p>
		<font size="14"> <strong><c:out
					value="${myOrders_button}" /></strong></font>
	</p>
	
	<c:if test="${empty sessionScope.allBooks}">
		<c:out value="У вас нет ни одного заказа"></c:out>
	</c:if>

	<c:out value="${requestScope.allBooks}"></c:out>
</body>
</html>