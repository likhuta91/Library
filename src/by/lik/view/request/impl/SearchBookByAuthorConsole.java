package by.lik.view.request.impl;

import by.lik.view.ConsoleHelper;
import by.lik.view.request.ShapingRequest;

public class SearchBookByAuthorConsole implements ShapingRequest {

	@Override
	public String shape() {
		
		ConsoleHelper consoleHelper = new ConsoleHelper();

		System.out.println("Введи имя автора:");
		String author = consoleHelper.inputString();

		String request = "searchBookByAuthor" + " author=" + author;

		return request;
	}
	
}