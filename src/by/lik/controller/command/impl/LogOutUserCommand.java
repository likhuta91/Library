package by.lik.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.lik.controller.command.Command;

public class LogOutUserCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getSession().removeAttribute("myUser");
		String goToPage = "index.jsp";
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);
		
	}

}
