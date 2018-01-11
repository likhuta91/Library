package by.lik.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.lik.controller.command.Command;
import by.lik.controller.helper.CommandHelper;

public class LogOutUserCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CommandHelper commandHelper = CommandHelper.getInstance();
		
		request.getSession().removeAttribute(CommandHelper.MY_USER);
		request.getSession().removeAttribute(CommandHelper.BASKET);
		
		String goToPage = CommandHelper.INDEX_PATH;
		
		String url = commandHelper.takeURL(request);
		
		request.getSession().setAttribute(CommandHelper.URL, url);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);
		
	}

}
