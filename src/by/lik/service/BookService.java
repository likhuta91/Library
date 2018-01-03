package by.lik.service;

import java.util.ArrayList;
import by.lik.bean.Book;
import by.lik.service.exception.ServiceException;

public interface BookService {
	
	boolean add(Book book) throws ServiceException;
	ArrayList<Book> searchByAuthor(String author) throws ServiceException;
	ArrayList<Book> searchByTitle(String title) throws ServiceException;
	ArrayList<Book> takeAllBooks(int pageNumber)throws ServiceException;

}
