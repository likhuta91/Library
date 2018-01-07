package by.lik.dao;

import java.util.ArrayList;

import by.lik.bean.Book;
import by.lik.dao.exception.DAOException;

public interface BookDao  {
	boolean add(Book book) throws DAOException;
	ArrayList<Book> searchBook(String value) throws DAOException;
	ArrayList<Book> takeAllBooks() throws DAOException;
	ArrayList<Book> takeBooksById(String[] booksId) throws DAOException;
}
