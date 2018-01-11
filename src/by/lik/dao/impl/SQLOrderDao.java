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

	private static final Logger log = Logger.getLogger(SQLOrderDao.class);
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

				orderId = resultSet.getInt(SQLHelper.ORDER_ID);

				if (userOrder.size() == 0) {
					
					order = createOrder(resultSet, book, userId);
					userOrder.add(order);
					
				} else {
					for (int i = 0; i < userOrder.size(); i++) {

						if (userOrder.get(i).getId() == orderId) {
	
							userOrder.get(i).getBooks().add(book);
							break;
						}else if (i == userOrder.size() - 1) {
							
							order = createOrder(resultSet, book, userId);
							userOrder.add(order);
							break;
						}
					}
				}
				
			}
			
			if (userOrder.size() == 0) {
				userOrder = null;
				log.log(Level.DEBUG, "Cписок заказов пуст");
			}

		} catch (SQLException e) {
			log.log(Level.ERROR, "Ошибка во время получения списка заказов пользователя");
			throw new DAOException("error while retrieving users", e);
		}

		return userOrder;
	}

	private Book createBook(ResultSet resultSet) throws DAOException {

		Book book = null;
		int bookId;
		String title;
		String bookStatus;
		String genre;
		String author;

		try {
			bookId = resultSet.getInt(SQLHelper.ORDER_ID);
			title = resultSet.getString(SQLHelper.TITLE);
			bookStatus = resultSet.getString(SQLHelper.BOOK_STATUS);
			genre = resultSet.getString(SQLHelper.GENRE);
			author = resultSet.getString(SQLHelper.AUTHOR);
			book = new Book();
			book.setId(bookId);
			book.setTitle(title);
			book.setAuthor(author);
			book.setGenre(genre);
			book.setStatus(bookStatus);

			log.log(Level.DEBUG, "Книга создана");

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

			books = new ArrayList<>();
			orderId = resultSet.getInt(SQLHelper.ORDER_ID);
			date = resultSet.getDate(SQLHelper.DATE);
			orderStatus = resultSet.getString(SQLHelper.ORDER_STATUS);

			order = new Order();
			log.log(Level.DEBUG, "Заказ создан");
			order.setId(orderId);
			order.setUserId(userId);
			order.setStatus(orderStatus);
			order.setDate(date);
			books.add(book);
			order.setBooks(books);
			
			log.log(Level.DEBUG, "Книга добавлена в заказ");

		} catch (SQLException exception) {
			
			log.log(Level.ERROR, "Не получается создать заказ");
			throw new DAOException("error while creating book", exception);
		}

		return order;
	}

	@Override
	public String addOrder(int userId, String[] bookId) throws DAOException {

		String message = null;

		int defaultOrderStatus = 1; // = new
		int notFreeOrderStatus = 2; // = not free

		Connection connection = null;

		try {
			connection = sqlHelper.takeConnection();
			connection.setAutoCommit(false);

			PreparedStatement preparedStatement = sqlHelper.takePreparedStatement(connection,
					SQLHelper.INSERT_INTO_ORDERS_QUERY);
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, defaultOrderStatus);
			preparedStatement.executeUpdate();

			preparedStatement = sqlHelper.takePreparedStatement(connection, SQLHelper.SELECT_LAST_INSERT_ID_QUERY);
			ResultSet resultSet = sqlHelper.takeResultSet(preparedStatement);
			resultSet.next();
			int orderId = Integer.parseInt(resultSet.getString("last_insert_id"));

			for (String id : bookId) {
				preparedStatement = sqlHelper.takePreparedStatement(connection,
						SQLHelper.INSERT_INTO_BOOKS_ORDERS_QUERY);
				preparedStatement.setInt(1, Integer.parseInt(id));
				preparedStatement.setInt(2, orderId);
				preparedStatement.executeUpdate();
			}

			for (String id : bookId) {
				preparedStatement = sqlHelper.takePreparedStatement(connection,
						SQLHelper.UPDATE_BOOK_STATUS_QUERY);
				preparedStatement.setInt(1, notFreeOrderStatus);
				preparedStatement.setInt(2, Integer.parseInt(id));
				preparedStatement.executeUpdate();
			}
			
			connection.commit();

		} catch (SQLException exception) {

			message = "Произошла непредвиденная ошибка при отправке заказа";

			try {
				connection.rollback();
			} catch (SQLException e) {

				log.log(Level.ERROR, "Ошибка при откате изменений в БД");
				throw new DAOException("error while executing the operation with the database", e);
			}

			log.log(Level.ERROR, "Не получается выполнить запрос по добавлению заказа в БД");
			throw new DAOException("error while executing the operation with the database", exception);

		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException exception) {
					log.log(Level.ERROR, "Ошибка при закртии соединения с БД");
					throw new DAOException("error while executing the operation with the database", exception);
				}
			}
		}

		return message;
	}

}
