package by.lik.controller.command.impl;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.lik.bean.Book;
import by.lik.service.BookService;
import by.lik.service.ServiceFactory;
import by.lik.service.exception.ServiceException;
import by.lik.controller.command.Command;

public class SearchBookByAuthorCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		ArrayList<Book> bookList = new ArrayList<>();

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		BookService bookService = serviceFactory.getBookService();

		String author = request.getParameter("author");

		try {
			bookList = bookService.searchByAuthor(author);

			if (bookList.size() > 0) {
			//	"Author - " + author + "\n";
			}

			for (int i = 0; i < bookList.size(); i++) {

				 //"Book " + (i + 1) + " - " + bookList.get(i).getTitle() + "\n";
			}
		} catch (ServiceException e) {
			//"Sorry, ....";
		}
		
	}

}
