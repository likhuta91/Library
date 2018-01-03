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
import by.lik.controller.helper.CommandHelper;

public class LoginationUserCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String goToPage;
		RequestDispatcher dispatcher;

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();

		String login = request.getParameter(CommandHelper.LOGIN);
		String password = request.getParameter(CommandHelper.PASSWORD);

		try {
			User user = userService.logination(login, password);

			if (user != null) {

				String url = request.getRequestURL().toString() + CommandHelper.GO_TO_GSP_COMMAND+CommandHelper.USER_PATH;
				request.getSession().setAttribute(CommandHelper.URL, url);

				request.getSession().setAttribute(CommandHelper.MY_USER, user);

				goToPage = CommandHelper.USER_PATH;

			} else {
				
				request.getSession().setAttribute(CommandHelper.MESSAGE, "Неверно введен логин или пароль");
				goToPage = "/WEB-INF/jsp/logination.jsp";
			}
		} catch (ServiceException e) {
			
			request.getSession().setAttribute(CommandHelper.MESSAGE, "Произошла непредвиденная ошибка, повторите ввод регистрационных данных");
			goToPage = CommandHelper.LOGINATION_PATH;
		}

		dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);

	}

}
