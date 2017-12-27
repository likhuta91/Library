package by.lik.controller;

import java.util.HashMap;
import java.util.Map;

import by.lik.controller.command.Command;
import by.lik.controller.command.impl.AddBookCommand;
import by.lik.controller.command.impl.ChangeLocalization;
import by.lik.controller.command.impl.GoToGsp;
import by.lik.controller.command.impl.LogOutUserCommand;
import by.lik.controller.command.impl.LoginationUserCommand;
import by.lik.controller.command.impl.RegistrationUserCommand;
import by.lik.controller.command.impl.SearchBookByAuthorCommand;
import by.lik.controller.command.impl.SearchBookByTitleCommand;
import by.lik.controller.command.impl.ShowAllBooks;

public class CommandProvider {
	Map<String, Command> commands = new HashMap<>();

	public CommandProvider() {
		commands.put("logination", new LoginationUserCommand());
		commands.put("registration", new RegistrationUserCommand());
		commands.put("addBook", new AddBookCommand());
		commands.put("searchBookByAuthor", new SearchBookByAuthorCommand());
		commands.put("searchBookByTitle", new SearchBookByTitleCommand());
		commands.put("changeLocalization", new ChangeLocalization());
		commands.put("goToGsp", new GoToGsp());
		commands.put("showAllBooks", new ShowAllBooks());
		commands.put("logOut", new LogOutUserCommand());
		
	}               

	public Command getCommand(String commandName) {
		return commands.get(commandName);
	}

}
