package by.lik.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import by.lik.bean.Book;
import by.lik.dao.BookDao;
import by.lik.dao.exception.DAOException;
import by.lik.dao.helper.SQLHelper;

public class SQLBookDao implements BookDao {

	private static final Logger log = Logger.getLogger(SQLBookDao.class);
	private SQLHelper sqlHelper = SQLHelper.getInstance();

	private Book createBook(ResultSet resultSet) throws DAOException {

		Book book = new Book();

		try {
			book.setId(resultSet.getInt(SQLHelper.ID));
			book.setAuthor(resultSet.getString(SQLHelper.AUTHOR));
			book.setTitle(resultSet.getString(SQLHelper.TITLE));
			book.setStatus(resultSet.getString(SQLHelper.STATUS));
			book.setGenre(resultSet.getString(SQLHelper.GENRE));

		} catch (SQLException e) {
			log.log(Level.ERROR, "не получается создать книгу");
			throw new DAOException("error while creating book", e);
		}

		return book;
	}

	@Override
	public ArrayList<Book> takeAllBooks(int pageNumber) throws DAOException {

		ArrayList<Book> allBooks = new ArrayList<>();
		Book book;

		try (Connection connection = sqlHelper.takeConnection()) {

			PreparedStatement preparedStatement = sqlHelper.takePreparedStatement(connection,
					SQLHelper.SELECT_ALL_BOOKS_QUERY);

			ResultSet resultSet = sqlHelper.takeResultSet(preparedStatement);

			while (resultSet.next()) {

				book = createBook(resultSet);
				allBooks.add(book);
			}

		} catch (SQLException e) {
			log.log(Level.ERROR, "ошибка во время получения списка всех книг");
			throw new DAOException("error while retrieving users", e);
		}	

		return allBooks;
	}

	@Override
	public boolean add(Book book) throws DAOException {

		return false;
	}

	@Override
	public ArrayList<Book> searchByAuthor(String author) throws DAOException {
		ArrayList<Book> bookList = null;

		return bookList;
	}

	@Override
	public ArrayList<Book> searchByTitle(String title) throws DAOException {
		ArrayList<Book> bookList = null;

		return bookList;
	}

}
