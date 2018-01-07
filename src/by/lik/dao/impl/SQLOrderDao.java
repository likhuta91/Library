package by.lik.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import by.lik.bean.Book;
import by.lik.bean.Order;
import by.lik.dao.OrderDao;
import by.lik.dao.exception.DAOException;
import by.lik.dao.helper.SQLHelper;

public class SQLOrderDao implements OrderDao {

	private static final Logger log = Logger.getLogger(SQLBookDao.class);
	private SQLHelper sqlHelper = SQLHelper.getInstance();

	@Override
	public ArrayList<Order> takeUserOrder(int userId) throws DAOException {
		ArrayList<Order> userOrder = new ArrayList<>();

		try (Connection connection = sqlHelper.takeConnection()) {

			PreparedStatement preparedStatement = sqlHelper.takePreparedStatement(connection,
					SQLHelper.SELECT_ORDER_BY_USER_ID_QUERY);
			preparedStatement.setInt(1, userId);

			ResultSet resultSet = sqlHelper.takeResultSet(preparedStatement);

			Order order;
			Book book;
			int orderId;

			while (resultSet.next()) {

				book = createBook(resultSet);
				
				orderId = resultSet.getInt("orderId");

				for (int i = 0; i < userOrder.size(); i++) {
					if (userOrder.get(i).getId() == orderId) {
						userOrder.get(i).getBooks().add(book);
						break;
					}
					if (i == userOrder.size() - 1) {
						order = createOrder(resultSet, book, userId);
						userOrder.add(order);
					}
				}
			}

			if (userOrder.size() == 0) {
				userOrder = null;
			}

		} catch (SQLException e) {
			log.log(Level.ERROR, "Ошибка во время получения списка заказов пользователя");
			throw new DAOException("error while retrieving users", e);
		}

		return userOrder;
	}

	private Book createBook(ResultSet resultSet) throws DAOException {

		Book book=null;
		int bookId;
		String title;
		String bookStatus;
		String genre;
		String author;

		try {
			bookId = resultSet.getInt("bookId");
			title = resultSet.getString("title");
			bookStatus = resultSet.getString("bookStatus");
			genre = resultSet.getString("genre");
			author = resultSet.getString("author");
			book = new Book();
			book.setId(bookId);
			book.setTitle(title);
			book.setAuthor(author);
			book.setGenre(genre);
			book.setStatus(bookStatus);
			
		} catch (SQLException e) {
			log.log(Level.ERROR, "Не получается создать книгу");
			throw new DAOException("error while creating book", e);
		}

		return book;
	}
	
	private Order createOrder(ResultSet resultSet, Book book, int userId) throws DAOException {

		Order order = null;
		ArrayList<Book> books;
		int orderId;
		Date date;
		String orderStatus;
		
		try {
			orderId = resultSet.getInt("orderId");
			date = resultSet.getDate("date");
			orderStatus = resultSet.getString("orderStatus");
			order = new Order();
			order.setId(orderId);
			order.setUserId(userId);
			order.setStatus(orderStatus);
			order.setDate(date);
			books = new ArrayList<>();
			books.add(book);
			
		} catch (SQLException e) {
			log.log(Level.ERROR, "Не получается создать книгу");
			throw new DAOException("error while creating book", e);
		}

		return order;
	}

}
