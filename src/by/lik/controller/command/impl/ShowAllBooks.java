package by.lik.controller.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.lik.bean.Book;
import by.lik.controller.command.Command;
import by.lik.service.BookService;
import by.lik.service.ServiceFactory;
import by.lik.service.exception.ServiceException;

public class ShowAllBooks implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String goToPage;
		RequestDispatcher dispatcher;

		if (request.getSession().getAttribute("myUser") == null) {

			goToPage = "index.jsp";

		} else {

			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			BookService bookService = serviceFactory.getBookService();

			ArrayList<Book> bookList = null;
			goToPage = "/WEB-INF/jsp/userStart.jsp";

			try {
				bookList = bookService.showAllBooks();
				request.setAttribute("allBooks", bookList);
			} catch (ServiceException e) {
				request.setAttribute("message", "Произошла ошибка при загрузке списка книг");
			}

			CommandHelper commandHelper = new CommandHelper();
			String url = commandHelper.takeURL(request);

			request.getSession().setAttribute("url", url);
		}
		
		dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);

	}

}
