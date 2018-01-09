package by.lik.controller.command.impl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.lik.controller.command.Command;
import by.lik.controller.helper.CommandHelper;

public class ChangeLocalization implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getSession(true).setAttribute(CommandHelper.LOCAL, request.getParameter(CommandHelper.LOCAL));

		String url = request.getSession().getAttribute(CommandHelper.URL).toString();
System.out.println(url + " in changeLocale");
		response.sendRedirect(url);

	}

}
