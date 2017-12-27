package by.lik.controller.command.impl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.lik.controller.command.Command;

public class ChangeLocalization implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//System.out.println(request.getSession().getAttribute("url"));
		request.getSession(true).setAttribute("local", request.getParameter("local"));

		String url = request.getSession().getAttribute("url").toString();

		response.sendRedirect(url);

	}

}
