package by.lik.view.request.impl;

import by.lik.view.ConsoleHelper;
import by.lik.view.request.ShapingRequest;

public class AddBookConsole implements ShapingRequest {

	@Override
	public String shape() {
		
		ConsoleHelper consoleHelper = new ConsoleHelper();

		System.out.println("Введи название книги:");
		String title = consoleHelper.inputString();
		
		System.out.println("Введи автора книги:");
		String author = consoleHelper.inputString();
		
		System.out.println("Введи адресс карточки книги:");
		String cardBook = consoleHelper.inputString();
		
		System.out.println("Введи адресс содержимого книги:");
		String content = consoleHelper.inputString();
		
		String request = "addBook" + " title=" + title + " cardBook=" + cardBook + " content=" + content + " author=" + author;

		return request;
	}

}