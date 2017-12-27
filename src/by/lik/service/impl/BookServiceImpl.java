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
	public ArrayList<Book> searchByAuthor(String author) throws ServiceException {
	
		ArrayList<Book> bookList = null;

		try {
			bookList = sqlBookDao.searchByAuthor(author);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}

		return bookList;
	}

	@Override
	public ArrayList<Book> searchByTitle(String title) throws ServiceException {
		
		ArrayList<Book> bookList = null;

		try {
			bookList = sqlBookDao.searchByTitle(title);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}

		return bookList;
	}

	@Override
	public ArrayList<Book> showAllBooks() throws ServiceException {
		ArrayList<Book> allBooks = null;
		try {
			allBooks = sqlBookDao.showAllBooks();
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allBooks;
	}

}
