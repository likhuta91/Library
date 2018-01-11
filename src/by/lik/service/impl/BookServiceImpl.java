package by.lik.service.impl;

import java.util.ArrayList;
import by.lik.bean.Book;
import by.lik.dao.BookDao;
import by.lik.dao.DAOFactory;
import by.lik.dao.exception.DAOException;
import by.lik.service.BookService;
import by.lik.service.exception.ServiceException;

public class BookServiceImpl implements BookService {
	
	private DAOFactory daoFactory = DAOFactory.getDaoFactory();
	private BookDao sqlBookDao = daoFactory.getBookDao();

	@Override
	public boolean add(Book book) throws ServiceException {

		boolean isExsist = false;

		try {
			isExsist = sqlBookDao.add(book);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}

		return isExsist;
	}

	@Override
	public ArrayList<Book> searchBook(String value) throws ServiceException {

		ArrayList<Book> bookList = null;

		value = value.replaceAll("[\\s]{2,}", " "); //удаление повторяющихся пробелов
		
		try {
			bookList = sqlBookDao.searchBook(value);

		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}

		return bookList;
	}

	@Override
	public ArrayList<Book> takeAllBooks() throws ServiceException {

		ArrayList<Book> allBooks = null;

		try {
			allBooks = sqlBookDao.takeAllBooks();

		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}

		return allBooks;
	}

	@Override
	public ArrayList<Book> takeBooksById(String[] booksId) throws ServiceException {
		
		ArrayList<Book> allBooks = null;

		try {
			allBooks = sqlBookDao.takeBooksById(booksId);

		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}

		return allBooks;
	}

}
