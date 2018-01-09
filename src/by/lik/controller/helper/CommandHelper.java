package by.lik.controller.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.lik.bean.Book;

public class CommandHelper {

	private static volatile CommandHelper commandHelper;

	private CommandHelper() {

	}

	public static CommandHelper getInstance() {
		CommandHelper localInstance = commandHelper;
		if (localInstance == null) {
			synchronized (CommandHelper.class) {
				localInstance = commandHelper;
				if (localInstance == null) {
					commandHelper = localInstance = new CommandHelper();
				}
			}
		}
		return localInstance;
	}

	public static final int NUMBER_OF_ITEMS_DISPLAYED_ON_THE_PAGE = 10;

	public static final String URL = "url";

	public static final String GSP_NAME = "gspName";
	public static final String LOCAL = "local";
	public static final String MY_USER = "myUser";
	public static final String ALL_BOOKS = "allBooks";
	public static final String ALL_USERS = "allUsers";
	public static final String COMMAND = "command";
	public static final String ADD_BOOK_TO_BASKET = "addBookToBasket";

	public static final String ID = "id";
	public static final String LOGIN = "login";
	public static final String PASSWORD = "password";
	public static final String NAME = "name";
	public static final String SURNAME = "surname";
	public static final String CITY = "city";
	public static final String COUNTRY = "country";
	public static final String EMAIL = "email";

	public static final String SEARCH_BOOK = "searchBook";
	public static final String LOGINATION = "logination";
	public static final String REGISTRATION = "registration";
	public static final String ADD_BOOK = "addBook";
	public static final String CHANGE_LOCALIZATION = "changeLocalization";
	public static final String GO_TO_GSP = "goToGsp";
	public static final String LOG_OUT = "logOut";
	public static final String CHANGE_USER_PASSWORD = "changeUserPassword";
	public static final String TAKE_USER_ORDERS = "takeUserOrders";
	public static final String ADD_ORDER = "addOrder";
	public static final String DELETE_BOOK_FROM_BASKET = "deleteBookFromBasket";
	public static final String DELETE_USER_ACCOUNT = "deleteUserAccount";

	public static final String MESSAGE = "message";
	public static final String CURRENT_PAGE_NUMBER = "currentPageNumber";
	public static final String NUMBER_OF_ALL_PAGES = "numberOfAllPages";

	public static final String INDEX_PATH = "index.jsp";
	public static final String USER_PATH = "/WEB-INF/jsp/user.jsp";
	public static final String USER_ORDER_PATH = "/WEB-INF/jsp/userOrder.jsp";
	public static final String BASKET_PATH = "/WEB-INF/jsp/basket.jsp";
	public static final String LOGINATION_PATH = "/WEB-INF/jsp/logination.jsp";
	public static final String REGISTRATION_PATH = "/WEB-INF/jsp/registration.jsp";
	public static final String CHANGE_PASSWORD_PATH = "/WEB-INF/jsp/changePassword.jsp";
	public static final String GO_TO_GSP_COMMAND = "?command=goToGsp&gspName=";

	public String takeURL(HttpServletRequest request) {

		String url = request.getRequestURL().toString() + "?";

		Map<String, String[]> parameterMap = request.getParameterMap();
		int sizeMap = parameterMap.size();

		if (parameterMap != null) {
			for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
				sizeMap--;

				for (int i = 0; i < entry.getValue().length; i++) {

					url = url + entry.getKey() + "=" + entry.getValue()[i];
					if (sizeMap != 0 || i < entry.getValue().length - 1) {
						url = url + "&";
					}
				}

			}
		}

		return url;
	}

	public int takeNumberOfAllPages(int listSize) {

		int numberOfAllPages = (int) Math.ceil(listSize / (double) NUMBER_OF_ITEMS_DISPLAYED_ON_THE_PAGE);

		return numberOfAllPages;
	}

	public int takeCurrentPageNumber(HttpServletRequest request) {

		int currentPageNumber = 1;

		if (request.getParameter(CURRENT_PAGE_NUMBER) != null) {

			currentPageNumber = Integer.parseInt(request.getParameter(CURRENT_PAGE_NUMBER));
		}

		return currentPageNumber;
	}

	public void putAttributeInSession(HttpServletRequest request, int currentPageNumber, int numberOfAllPages,
			String command) {

		request.getSession().setAttribute(CommandHelper.COMMAND, command);
		request.getSession().setAttribute(CommandHelper.CURRENT_PAGE_NUMBER, currentPageNumber);
		request.getSession().setAttribute(CommandHelper.NUMBER_OF_ALL_PAGES, numberOfAllPages);

	}

	public void putAttributeInSession(HttpServletRequest request, int currentPageNumber, int numberOfAllPages,
			String command, String value) {

		request.getSession().setAttribute(CommandHelper.COMMAND, command);
		request.getSession().setAttribute(CommandHelper.CURRENT_PAGE_NUMBER, currentPageNumber);
		request.getSession().setAttribute(CommandHelper.NUMBER_OF_ALL_PAGES, numberOfAllPages);
		request.getSession().setAttribute(command, value);

	}

	public void logOutIfUserNotAuthorized(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getSession().getAttribute(CommandHelper.MY_USER) == null) {

			String goToPage = CommandHelper.INDEX_PATH;
			request.getSession().setAttribute(MESSAGE, "Вы не авторизованы");
			RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
			dispatcher.forward(request, response);
		}

	}

	public String takeAttributeFromRequestOrSession(HttpServletRequest request, String parametr) {
		String value = request.getParameter(parametr);

		if (value == null) {
			value = String.valueOf(request.getSession().getAttribute(parametr));
		}

		return value;
	}

	public void deleteBookFromBasket(HttpServletRequest request, String[] idBooksInBasket) {

		@SuppressWarnings("unchecked")
		ArrayList<Book> booksInBasket = (ArrayList<Book>) request.getSession().getAttribute(CommandHelper.ALL_BOOKS);
		@SuppressWarnings("unchecked")
		ArrayList<Book> booksInOrder = (ArrayList<Book>)booksInBasket.clone();
		
		for (Book book : booksInBasket) {
			for (String deletedIdBook : idBooksInBasket) {
				if (Integer.parseInt(deletedIdBook) == book.getId()) {
					booksInOrder.remove(book);
				}
			}
		}
		
		if (booksInOrder.size() != 0) {
			request.getSession().setAttribute(CommandHelper.ALL_BOOKS, booksInOrder);
		} else {
			request.getSession().removeAttribute(CommandHelper.ALL_BOOKS);
		}
		

	}

}
