package by.lik.view.request.impl;

import by.lik.view.ConsoleHelper;
import by.lik.view.request.ShapingRequest;

public class RegistrationUserConsole implements ShapingRequest {

	@Override
	public String shape() {

		ConsoleHelper consoleHelper = new ConsoleHelper();

		System.out.println("Введи имя:");
		String name = consoleHelper.inputString();
	
		System.out.println("Введи фамилию:");
		String surname = consoleHelper.inputString();
	
		System.out.println("Введи логин:");
		String login = consoleHelper.inputString();
	
		System.out.println("Введи пароль:");
		String password = consoleHelper.inputString();

		System.out.println("Введи адрес электронной почты:");
		String email = consoleHelper.inputString();
	
		String request = "registration" + " name=" + name + " surname=" + surname + " " + login + " " + password + " " + email;

		return request;
	}

}