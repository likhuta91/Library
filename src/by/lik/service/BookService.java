package by.lik.service;

import java.util.ArrayList;
import by.lik.bean.Book;
import by.lik.service.exception.ServiceException;

public interface BookService {
	
	boolean add(Book book) throws ServiceException;
	ArrayList<Book> searchBook(String value) throws ServiceException;
	ArrayList<Book> takeAllBooks()throws ServiceException;
	ArrayList<Book> takeBooksById(String[] booksId)throws ServiceException;

}
