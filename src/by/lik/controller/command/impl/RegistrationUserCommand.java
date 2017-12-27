package by.lik.controller.command.impl;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.lik.bean.User;
import by.lik.service.ServiceFactory;
import by.lik.service.UserService;
import by.lik.service.exception.ServiceException;
import by.lik.controller.command.Command;

public class RegistrationUserCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		User user = new User();
		String defaultUserStatus = "1";
		int defaultUserRating = 0;

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();

		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String city = request.getParameter("city");
		String country = request.getParameter("country");
		String email = request.getParameter("email");
		
		user.setLogin(login);
		user.setPassword(password);
		user.setName(name);
		user.setSurname(surname);
		user.setCity(city);
		user.setCountry(country);
		user.setEmail(email);
				
		user.setRating(defaultUserRating);
		user.setStatus(defaultUserStatus);

		String registeredMessage;
		String goToPage = null;

		try {
			registeredMessage = userService.registration(user);

			if (registeredMessage.equals("true")) {
				request.setAttribute("message", "Регистрация прошла успешно");
				goToPage = "/WEB-INF/jsp/logination.jsp";
			} else {
				request.setAttribute("regMessage", registeredMessage);
				goToPage = "/WEB-INF/jsp/registration.jsp";	
			}
			/*"Пользователь с таким логином существует, либо не верно введены регистрационные данные"*/
		} catch (ServiceException e) {
			//отправить юзеру сообщение, что не зарегилось
			
			e.printStackTrace();
			goToPage = "error.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);

	}

}
