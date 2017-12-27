package by.lik.view.request.impl;

import by.lik.view.ConsoleHelper;
import by.lik.view.request.ShapingRequest;

public class LoginationUserConsole implements ShapingRequest {

	@Override
	public String shape() {
		
		ConsoleHelper consoleHelper = new ConsoleHelper();

		System.out.println("Введи логин:");
		String login = consoleHelper.inputString();

		System.out.println("Введи пароль:");
		String password = consoleHelper.inputString();

		String request = "logination " + login + " " + password;

		return request;
	}

}