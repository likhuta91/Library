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

public class LoginationUserCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String goToPage;
		RequestDispatcher dispatcher;

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();

		String login = request.getParameter("login");
		String password = request.getParameter("password");
		
		try {
			User user = userService.logination(login, password);

			if (user != null) {

				String url = request.getRequestURL().toString() + "?";
				request.getSession().setAttribute("url", url + "command=goToGsp&gspName=/WEB-INF/jsp/userStart.jsp");

				request.setAttribute("myUser", user);
				request.getSession().setAttribute("myUser", user);
				
				if (user.getStatus().equals("admin")) {
					goToPage = "/WEB-INF/jsp/adminStart.jsp";
				} else {
					goToPage = "/WEB-INF/jsp/userStart.jsp";
				}
				
			} else {
				request.setAttribute("message", "Неверно введен логин или пароль");
				goToPage = "/WEB-INF/jsp/logination.jsp";
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			goToPage = "error.jsp";
		}
		//request.setAttribute("gspName", url);

		dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);

	}

}
