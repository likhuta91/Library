package by.lik.controller.command.impl;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.lik.bean.Book;
import by.lik.service.BookService;
import by.lik.service.ServiceFactory;
import by.lik.service.exception.ServiceException;
import by.lik.controller.command.Command;
import by.lik.controller.helper.CommandHelper;
import by.lik.controller.paginator.Paginator;

public class SearchBookCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		CommandHelper commandHelper = CommandHelper.getInstance();
		commandHelper.logOutIfUserNotAuthorized(request, response);

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		BookService bookService = serviceFactory.getBookService();

		String goToPage = CommandHelper.USER_PATH;

		ArrayList<Book> allBooks = null;
		ArrayList<Book> listOfReturnedBooks = null;

		int currentPageNumber = commandHelper.takeCurrentPageNumber(request);
		int numberOfAllPages;

		String value = commandHelper.takeAttributeFromRequestOrSession(request, CommandHelper.SEARCH_BOOK);

		try {
			allBooks = bookService.searchBook(value);

		} catch (ServiceException e) {
			request.getSession().setAttribute(CommandHelper.MESSAGE, "Произошла ошибка при загрузке списка книг");
		}

		if (allBooks == null) {
			request.getSession().setAttribute(CommandHelper.MESSAGE, "Книги не найдены");

		} else {
			numberOfAllPages = commandHelper.takeNumberOfAllPages(allBooks.size());

			Paginator<Book> paginator = new Paginator<>();

			listOfReturnedBooks = paginator.returnSelectedListOfValues(allBooks, currentPageNumber,
					CommandHelper.NUMBER_OF_ITEMS_DISPLAYED_ON_THE_PAGE);

			request.setAttribute(CommandHelper.ALL_BOOKS, listOfReturnedBooks);

			commandHelper.putAttributeInSession(request, currentPageNumber, numberOfAllPages, CommandHelper.SEARCH_BOOK,
					value);
		}
		System.out
				.println(request.getSession().getAttribute(CommandHelper.URL) + " in searchBookCommand до смены урла");
		String url = commandHelper.takeURL(request);

		System.out.println(url + " in searchBookCommand STring url;");

		request.getSession().setAttribute(CommandHelper.URL, url);
		System.out.println(
				request.getSession().getAttribute(CommandHelper.URL) + " in searchBookCommand после смены урла");

		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);

	}

}
