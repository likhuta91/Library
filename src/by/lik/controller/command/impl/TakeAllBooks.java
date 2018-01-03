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
		String goToPage = CommandHelper.USER_PATH;
		RequestDispatcher dispatcher;

		if (request.getSession().getAttribute(CommandHelper.MY_USER) == null) {

			goToPage = CommandHelper.INDEX_PATH;

		} else {

			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			BookService bookService = serviceFactory.getBookService();

			ArrayList<Book> bookList = null;
			
			int currentPageNumber=1;
			
			if(request.getParameter(CommandHelper.CURRENT_PAGE_NUMBER)!=null) {
				currentPageNumber = Integer.parseInt(request.getParameter(CommandHelper.CURRENT_PAGE_NUMBER));
			}
			
			try {
				bookList = bookService.takeAllBooks(currentPageNumber);
				
				int numberOfAllPages = (int)Math.ceil(bookList.size()/10.0);
				
				request.getSession().setAttribute(CommandHelper.NUMBER_OF_ALL_PAGES, numberOfAllPages);
				
				Paginator<Book> paginator = new Paginator<>();
				bookList = paginator.returnSelectedListOfValues(bookList, currentPageNumber, CommandHelper.NUMBER_OF_ITEMS_DISPLAYED_ON_THE_PAGE);
				request.setAttribute(CommandHelper.ALL_BOOKS, bookList);
				
			} catch (ServiceException e) {
				
				request.getSession().setAttribute(CommandHelper.MESSAGE, "Произошла ошибка при загрузке списка книг");
			}

			CommandHelper commandHelper = new CommandHelper();
			String url = commandHelper.takeURL(request);

			request.getSession().setAttribute(CommandHelper.URL, url);
			request.getSession().setAttribute(CommandHelper.COMMAND, CommandHelper.ALL_BOOKS);;
			request.getSession().setAttribute(CommandHelper.CURRENT_PAGE_NUMBER, currentPageNumber);
		}
		
		dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);

	}

}
