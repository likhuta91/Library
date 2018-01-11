package by.lik.controller.command.impl;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.lik.bean.Book;
import by.lik.controller.command.Command;
import by.lik.controller.helper.CommandHelper;
import by.lik.service.BookService;
import by.lik.service.ServiceFactory;
import by.lik.service.exception.ServiceException;

public class AddBookToBasket implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		CommandHelper commandHelper = CommandHelper.getInstance();
		commandHelper.logOutIfUserNotAuthorized(request, response);

		String[] idBooksAddedToTheBasket = request.getParameterValues(CommandHelper.ID);
		ArrayList<Book> booksListAddedToTheBasket = null;

		if (idBooksAddedToTheBasket != null) {

			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			BookService bookService = serviceFactory.getBookService();

			try {

				booksListAddedToTheBasket = bookService.takeBooksById(idBooksAddedToTheBasket);

			} catch (ServiceException e) {

				request.getSession().setAttribute(CommandHelper.MESSAGE, "Не удалось добавить книги в корзину");
			}

			@SuppressWarnings("unchecked")
			ArrayList<Book> bookInBasket = (ArrayList<Book>) request.getSession().getAttribute(CommandHelper.BASKET);

			if (bookInBasket == null) {
				bookInBasket = booksListAddedToTheBasket;

			} else {
				for (Book book : booksListAddedToTheBasket) {
					if (!bookInBasket.contains(book)) {
						bookInBasket.add(book);
					}
				}
			}
			request.getSession().setAttribute(CommandHelper.BASKET, bookInBasket);
		}

		response.sendRedirect(request.getSession().getAttribute(CommandHelper.URL).toString());

	}

}
