package by.lik.controller.command.impl;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.lik.bean.Book;
import by.lik.service.BookService;
import by.lik.service.exception.ServiceException;
import by.lik.service.ServiceFactory;
import by.lik.controller.command.Command;

public class SearchBookByTitleCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		ArrayList<Book> bookList = new ArrayList<>();

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		BookService bookService = serviceFactory.getBookService();

		String title = request.getParameter("title");

		try {
			bookList = bookService.searchByTitle(title);

			if (bookList!=null) {
				//response = "Title - " + title + "\n";
				for (int i = 0; i < bookList.size(); i++) {

				//	response = response + "Author " + (i + 1) + " - " + bookList.get(i).getAuthor() + "\n";
				}
			}

			
		} catch (ServiceException e) {
			//response = "Sorry, ....";
		}
		
	}

}
