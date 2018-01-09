package by.lik.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.lik.controller.command.Command;
import by.lik.controller.helper.CommandHelper;

public class DeleteBookFromBasket implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		CommandHelper commandHelper = CommandHelper.getInstance();
		commandHelper.logOutIfUserNotAuthorized(request, response);

		if (request.getParameterValues(CommandHelper.ID) != null) {

			String[] booksInBasket = request.getParameterValues(CommandHelper.ID);

			commandHelper.deleteBookFromBasket(request, booksInBasket);

		}
		response.sendRedirect(request.getSession().getAttribute(CommandHelper.URL).toString());

	}

}
