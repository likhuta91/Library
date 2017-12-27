package by.lik.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.lik.bean.Book;
import by.lik.service.BookService;
import by.lik.service.ServiceFactory;
import by.lik.service.exception.ServiceException;
import by.lik.controller.command.Command;

public class AddBookCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		Book book = new Book();

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		BookService bookService = serviceFactory.getBookService();

		String title = request.getParameter("title");
		String cardBook = request.getParameter("cardBook");
		String content = request.getParameter("content");
		String author = request.getParameter("author");

		book.setTitle(title);
		book.setCardBook(cardBook);
		book.setContent(content);
		book.setAuthor(author);
		boolean bookIsExist = false;

		try {
			bookIsExist = bookService.add(book);
			if (bookIsExist) {
				
			} else {
				
			}

		} catch (ServiceException e) {
			
		}
		
	}

}
