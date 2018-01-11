package by.lik.controller.command.impl;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.lik.bean.Book;
import by.lik.controller.command.Command;
import by.lik.controller.helper.CommandHelper;
import by.lik.controller.paginator.Paginator;
import by.lik.service.BookService;
import by.lik.service.ServiceFactory;
import by.lik.service.exception.ServiceException;

public class TakeAllBooks implements Command {

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
		
		try {			
			allBooks = bookService.takeAllBooks();
			
			numberOfAllPages = commandHelper.takeNumberOfAllPages(allBooks.size());
			
			Paginator<Book> paginator = new Paginator<>();
			
			listOfReturnedBooks = paginator.returnSelectedListOfValues(allBooks, currentPageNumber,
					CommandHelper.NUMBER_OF_ITEMS_DISPLAYED_ON_THE_PAGE);

			request.setAttribute(CommandHelper.ALL_BOOKS, listOfReturnedBooks);
			
			commandHelper.putAttributeInSession(request, currentPageNumber, numberOfAllPages, CommandHelper.ALL_BOOKS);

		} catch (ServiceException e) {

			request.getSession().setAttribute(CommandHelper.MESSAGE, "Произошла ошибка при загрузке списка книг");
		}
		
		String url = commandHelper.takeURL(request);
		request.getSession().setAttribute(CommandHelper.URL, url);

		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);

	}

}
