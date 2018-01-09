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
import by.lik.controller.paginator.Paginator;
import by.lik.service.ServiceFactory;
import by.lik.service.UserService;
import by.lik.service.exception.ServiceException;

public class TakeAllUsers implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CommandHelper commandHelper = CommandHelper.getInstance();

		commandHelper.logOutIfUserNotAuthorized(request, response);

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		
		String goToPage = CommandHelper.USER_PATH;
		
		ArrayList<User> allUsers = null;
		ArrayList<User> listOfReturnedUsers = null;

		int currentPageNumber = commandHelper.takeCurrentPageNumber(request);
		int numberOfAllPages;

		try {
			allUsers = userService.takeAllUsers();

			numberOfAllPages = commandHelper.takeNumberOfAllPages(allUsers.size());

			Paginator<User> paginator = new Paginator<>();
			
			listOfReturnedUsers = paginator.returnSelectedListOfValues(allUsers, currentPageNumber,
					CommandHelper.NUMBER_OF_ITEMS_DISPLAYED_ON_THE_PAGE);

			request.setAttribute(CommandHelper.ALL_USERS, listOfReturnedUsers);
			
			commandHelper.putAttributeInSession(request, currentPageNumber, numberOfAllPages, CommandHelper.ALL_USERS);

		} catch (ServiceException e) {

			request.getSession().setAttribute(CommandHelper.MESSAGE,
					"Произошла ошибка при загрузке списка пользователей");
		}
		
		String url = commandHelper.takeURL(request);
		request.getSession().setAttribute(CommandHelper.URL, url);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);

	}

}
