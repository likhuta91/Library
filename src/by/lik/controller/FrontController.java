package by.lik.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import by.lik.controller.command.Command;
import by.lik.controller.helper.CommandHelper;

public class FrontController extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(FrontController.class);
	private static final long serialVersionUID = 1L;
	private final CommandProvider provider = new CommandProvider();

	public FrontController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String commandName = request.getParameter(CommandHelper.COMMAND);
		
		Command commandObject = provider.getCommand(commandName);
		
		log.log(Level.TRACE, "url " + request.getQueryString() + " in doGet");
		
		commandObject.execute(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String commandName = request.getParameter(CommandHelper.COMMAND);

		Command commandObject = provider.getCommand(commandName);
		
		log.log(Level.TRACE, "url " + request.getQueryString() + " in doPost");
		
		commandObject.execute(request, response);
		
		
	}

}
