package by.lik.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.lik.bean.User;
import by.lik.controller.command.Command;
import by.lik.controller.helper.CommandHelper;
import by.lik.service.ServiceFactory;
import by.lik.service.UserService;
import by.lik.service.exception.ServiceException;

public class ChangeUserPasswordCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CommandHelper commandHelper = CommandHelper.getInstance();
		commandHelper.logOutIfUserNotAuthorized(request, response);

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();

		String message;
		String goToPage = CommandHelper.USER_PATH;

		String password = request.getParameter(CommandHelper.PASSWORD);
		User user = (User) request.getSession().getAttribute(CommandHelper.MY_USER);
		
		int id = user.getId();

		try {
			message = userService.сhangePassword(password, id);

			if (message == null) {

				request.getSession().setAttribute(CommandHelper.MESSAGE, "Пароль успешно изменен");
			} else {

				request.getSession().setAttribute(CommandHelper.MESSAGE, message);

				goToPage = CommandHelper.CHANGE_PASSWORD_PATH;
			}

		} catch (ServiceException e) {

			request.getSession().setAttribute(CommandHelper.MESSAGE,
					"Произошла непредвиденная ошибка, повторите ввод нового пароля");

			goToPage = CommandHelper.CHANGE_PASSWORD_PATH;
		}

		String url = request.getRequestURL().toString() + CommandHelper.GO_TO_GSP_COMMAND + goToPage;

		response.sendRedirect(url);
	}

}
