package by.lik.service;

import by.lik.service.BookService;
import by.lik.service.ServiceFactory;
import by.lik.service.UserService;
import by.lik.service.impl.BookServiceImpl;
import by.lik.service.impl.UserServiceImpl;

public class ServiceFactory {
private static final ServiceFactory instance = new ServiceFactory();
	
	private final UserService userService = new UserServiceImpl();
	private final BookService bookService = new BookServiceImpl();
	
	private ServiceFactory() {}
	
	public UserService getUserService() {
		return userService;
	}
	
	public BookService getBookService() {
		return bookService;
	}
	
	
	public static ServiceFactory getInstance() {
		return instance;
	}
}
