package by.lik.controller.command.impl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.lik.bean.User;
import by.lik.service.ServiceFactory;
import by.lik.service.UserService;
import by.lik.service.exception.ServiceException;
import by.lik.controller.command.Command;
import by.lik.controller.helper.CommandHelper;

public class RegistrationUserCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = new User();
		String defaultUserStatus = "1";
		int defaultUserRating = 0;

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();

		String login = request.getParameter(CommandHelper.LOGIN);
		String password = request.getParameter(CommandHelper.PASSWORD);
		String name = request.getParameter(CommandHelper.NAME);
		String surname = request.getParameter(CommandHelper.SURNAME);
		String city = request.getParameter(CommandHelper.CITY);
		String country = request.getParameter(CommandHelper.COUNTRY);
		String email = request.getParameter(CommandHelper.EMAIL);

		user.setLogin(login);
		user.setPassword(password);
		user.setName(name);
		user.setSurname(surname);
		user.setCity(city);
		user.setCountry(country);
		user.setEmail(email);

		user.setRating(defaultUserRating);
		user.setStatus(defaultUserStatus);

		String message;
		String goToPage = null;

		try {
			message = userService.registration(user);

			if (message==null) {

				request.getSession().setAttribute(CommandHelper.MESSAGE, "Регистрация прошла успешно");
				goToPage = CommandHelper.LOGINATION_PATH;

			} else {

				request.getSession().setAttribute(CommandHelper.MESSAGE, message);
				goToPage = CommandHelper.REGISTRATION_PATH;
			}

		} catch (ServiceException e) {

			request.getSession().setAttribute(CommandHelper.MESSAGE,
					"Произошла непредвиденная ошибка, повторите ввод регистрационных данных");
			goToPage = CommandHelper.REGISTRATION_PATH;
		}

		String url = request.getRequestURL().toString() + CommandHelper.GO_TO_GSP_COMMAND + goToPage;

		response.sendRedirect(url);

	}

}
