package by.lik.controller.command.impl;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.lik.bean.User;
import by.lik.controller.command.Command;
import by.lik.controller.helper.CommandHelper;
import by.lik.service.ServiceFactory;
import by.lik.service.UserService;
import by.lik.service.exception.ServiceException;

public class TakeAllUsers implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String goToPage = CommandHelper.USER_PATH;
		RequestDispatcher dispatcher;

		if (request.getSession().getAttribute(CommandHelper.MY_USER) == null) {

			goToPage = CommandHelper.INDEX_PATH;

		} else {

			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			UserService userService = serviceFactory.getUserService();

			ArrayList<User> userList = null;

			try {
				userList = userService.takeAllUsers();
				request.setAttribute(CommandHelper.ALL_USERS, userList);
				
			} catch (ServiceException e) {
				
				request.getSession().setAttribute(CommandHelper.MESSAGE, "Произошла ошибка при загрузке списка пользователей");
			}

			CommandHelper commandHelper = new CommandHelper();
			String url = commandHelper.takeURL(request);

			request.getSession().setAttribute(CommandHelper.URL, url);
		}
		
		dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);

	}

}

