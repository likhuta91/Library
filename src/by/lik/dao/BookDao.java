package by.lik.dao;

import java.util.ArrayList;

import by.lik.bean.Book;
import by.lik.dao.exception.DAOException;

public interface BookDao  {
	boolean add(Book book) throws DAOException;
	ArrayList<Book> searchByAuthor(String author) throws DAOException;
	ArrayList<Book> searchByTitle(String title) throws DAOException;
	ArrayList<Book> showAllBooks() throws DAOException;
}
