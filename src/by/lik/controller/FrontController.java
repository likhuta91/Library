package by.lik.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.lik.controller.command.Command;

public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final CommandProvider provider = new CommandProvider();

	public FrontController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String commandName = request.getParameter("command");
		
		Command commandObject = provider.getCommand(commandName);
		
		commandObject.execute(request, response);

		// response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String commandName = request.getParameter("command");

		Command commandObject = provider.getCommand(commandName);

		commandObject.execute(request, response);

	}

}
