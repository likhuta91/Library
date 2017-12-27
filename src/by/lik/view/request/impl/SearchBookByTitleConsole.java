package by.lik.view.request.impl;

import by.lik.view.ConsoleHelper;
import by.lik.view.request.ShapingRequest;

public class SearchBookByTitleConsole implements ShapingRequest {

	@Override
	public String shape() {

		ConsoleHelper consoleHelper = new ConsoleHelper();

		System.out.println("Введи имя книги:");
		String title = consoleHelper.inputString();

		String request = "searchBookByTitle" + " title=" + title;

		return request;
	}

}