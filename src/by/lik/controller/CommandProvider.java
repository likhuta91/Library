package by.lik.controller;

import java.util.HashMap;
import java.util.Map;

import by.lik.controller.command.Command;
import by.lik.controller.command.impl.AddBookCommand;
import by.lik.controller.command.impl.AddBookToBasket;
import by.lik.controller.command.impl.ChangeLocalization;
import by.lik.controller.command.impl.ChangeUserPasswordCommand;
import by.lik.controller.command.impl.GoToGsp;
import by.lik.controller.command.impl.LogOutUserCommand;
import by.lik.controller.command.impl.LoginationUserCommand;
import by.lik.controller.command.impl.RegistrationUserCommand;
import by.lik.controller.command.impl.SearchBookCommand;
import by.lik.controller.command.impl.TakeAllBooks;
import by.lik.controller.command.impl.TakeAllUsers;
import by.lik.controller.command.impl.TakeUserOrders;
import by.lik.controller.helper.CommandHelper;

public class CommandProvider {
	Map<String, Command> commands = new HashMap<>();

	public CommandProvider() {
		commands.put(CommandHelper.LOGINATION, new LoginationUserCommand());
		commands.put(CommandHelper.REGISTRATION, new RegistrationUserCommand());
		commands.put(CommandHelper.ADD_BOOK, new AddBookCommand());
		commands.put(CommandHelper.SEARCH_BOOK, new SearchBookCommand());		
		commands.put(CommandHelper.CHANGE_LOCALIZATION, new ChangeLocalization());
		commands.put(CommandHelper.GO_TO_GSP, new GoToGsp());
		commands.put(CommandHelper.ALL_BOOKS, new TakeAllBooks());
		commands.put(CommandHelper.ALL_USERS, new TakeAllUsers());
		commands.put(CommandHelper.LOG_OUT, new LogOutUserCommand());
		commands.put(CommandHelper.CHANGE_USER_PASSWORD, new ChangeUserPasswordCommand());
		commands.put(CommandHelper.ADD_BOOK_TO_BASKET, new AddBookToBasket());
		commands.put(CommandHelper.TAKE_USER_ORDERS, new TakeUserOrders());
		
	}               

	public Command getCommand(String commandName) {
		return commands.get(commandName);
	}

}
