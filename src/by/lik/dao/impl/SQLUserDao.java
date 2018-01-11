package by.lik.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import by.lik.bean.User;
import by.lik.dao.UserDao;
import by.lik.dao.exception.DAOException;
import by.lik.dao.helper.SQLHelper;

public class SQLUserDao implements UserDao {

	private static final Logger log = Logger.getLogger(SQLUserDao.class);
	private SQLHelper sqlHelper = SQLHelper.getInstance();

	private User createUser(ResultSet resultSet) throws DAOException {

		User user = new User();

		try {

			user.setId(resultSet.getInt(SQLHelper.ID));
			user.setName(resultSet.getString(SQLHelper.NAME));
			user.setSurname(resultSet.getString(SQLHelper.SURNAME));
			user.setCity(resultSet.getString(SQLHelper.CITY));
			user.setCountry(resultSet.getString(SQLHelper.COUNTRY));
			user.setEmail(resultSet.getString(SQLHelper.EMAIL));
			user.setLogin(resultSet.getString(SQLHelper.LOGIN));
			user.setRating(resultSet.getInt(SQLHelper.RATING));
			user.setStatus(resultSet.getString(SQLHelper.STATUS));

		} catch (SQLException e) {
			log.log(Level.ERROR, "не получается создать пользователя");
			throw new DAOException("error while creating user", e);
		}

		return user;
	}

	/*
	 * public int takeUserId(String login) throws DAOException {
	 * 
	 * int id = 0;
	 * 
	 * try (Connection connection = sqlHelper.takeConnection()) {
	 * 
	 * PreparedStatement preparedStatement =
	 * sqlHelper.takePreparedStatement(connection, SQLHelper.SELECT_USER_ID_QUERY);
	 * preparedStatement.setString(1, login); ResultSet resultSet =
	 * sqlHelper.takeResultSet(preparedStatement);
	 * 
	 * if (resultSet.next()) { id = resultSet.getInt(SQLHelper.ID); }
	 * 
	 * } catch (SQLException exception) {
	 * 
	 * log.log(Level.ERROR, "не получается взять Id пользователя из БД"); throw new
	 * DAOException("error when retrieving the user id from the database",
	 * exception); }
	 * 
	 * return id; }
	 */

	private boolean isArgumentFree(String argument, String sqlQuery) throws DAOException {

		try (Connection connection = sqlHelper.takeConnection()) {

			PreparedStatement preparedStatement = sqlHelper.takePreparedStatement(connection, sqlQuery);
			preparedStatement.setString(1, argument);
			ResultSet resultSet = sqlHelper.takeResultSet(preparedStatement);

			if (resultSet.next()) {
				return false;
			}

		} catch (SQLException exception) {
			log.log(Level.ERROR, "не получается проверить, занят ли логин или email в БД");
			throw new DAOException("error while checking for an argument in the database", exception);
		}

		return true;
	}

	@Override
	public User logination(String login, String password) throws DAOException {

		User user = null;

		try (Connection connection = sqlHelper.takeConnection()) {

			PreparedStatement preparedStatement = sqlHelper.takePreparedStatement(connection,
					SQLHelper.SELECT_USER_QUERY);
			preparedStatement.setString(1, login);
			preparedStatement.setString(2, password);
			ResultSet resultSet = sqlHelper.takeResultSet(preparedStatement);

			if (resultSet.next()) {

				user = createUser(resultSet);
			}

		} catch (SQLException exception) {

			log.log(Level.ERROR, "ошибка во время логинации пользователя");
			throw new DAOException("error while retrieving users", exception);
		}

		return user;
	}

	@Override
	public String registration(User user) throws DAOException {

		String message = null;

		if (!isArgumentFree(user.getLogin(), SQLHelper.SELECT_USER_LOGIN_QUERY)) {
			return "Пользователь с таким логином уже зарегистрирован";
		}

		if (!isArgumentFree(user.getEmail(), SQLHelper.SELECT_USER_EMAIL_QUERY)) {
			return "Пользователь с таким email уже зарегистрирован";
		}

		Connection connection = sqlHelper.takeConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement preparedStatement = sqlHelper.takePreparedStatement(connection,
					SQLHelper.INSERT_INTO_USERS_QUERY);
			preparedStatement.setString(1, user.getLogin());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setInt(3, user.getRating());
			preparedStatement.setString(4, user.getStatus());
			preparedStatement.executeUpdate();

			preparedStatement = sqlHelper.takePreparedStatement(connection, SQLHelper.SELECT_LAST_INSERT_ID_QUERY);
			ResultSet resultSet = sqlHelper.takeResultSet(preparedStatement);
			resultSet.next();
			int id = Integer.parseInt(resultSet.getString("last_insert_id"));

			preparedStatement = sqlHelper.takePreparedStatement(connection, SQLHelper.INSERT_INTO_USERS_DATA_QUERY);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getSurname());
			preparedStatement.setString(3, user.getCity());
			preparedStatement.setString(4, user.getCountry());
			preparedStatement.setString(5, user.getEmail());
			preparedStatement.setInt(6, id);
			preparedStatement.executeUpdate();

			connection.commit();

		} catch (SQLException exception) {

			try {
				connection.rollback();
			} catch (SQLException e) {

				log.log(Level.ERROR, "Ошибка во время отката изменений в БД");
				throw new DAOException("error during rollback of changes in database", exception);
			}

			log.log(Level.ERROR, "Ошибка во время регистрации пользователя");
			throw new DAOException("error while retrieving users", exception);

		} finally {

			if (connection != null) {

				try {
					connection.close();
				} catch (SQLException e) {
					log.log(Level.ERROR, "Ошибка во время закрытия соединения");
					throw new DAOException("Error while closing connection", e);
				}
			}
		}

		return message;
	}

	@Override
	public ArrayList<User> takeAllUsers() throws DAOException {

		User user = null;
		ArrayList<User> allUsers = new ArrayList<>();

		try (Connection connection = sqlHelper.takeConnection()) {
			PreparedStatement preparedStatement = sqlHelper.takePreparedStatement(connection,
					SQLHelper.SELECT_ALL_USERS_QUERY);
			ResultSet resultSet = sqlHelper.takeResultSet(preparedStatement);

			while (resultSet.next()) {
				user = createUser(resultSet);
				allUsers.add(user);
			}

		} catch (SQLException exception) {

			log.log(Level.ERROR, "Ошибка во время получения списка всех пользователей");
			throw new DAOException("error while retrieving users", exception);
		}

		return allUsers;
	}

	@Override
	public String сhangePassword(String newPassword, int id) throws DAOException {

		String message = null;
		Connection connection = null;

		try {
			connection = sqlHelper.takeConnection();
			connection.setAutoCommit(false);

			PreparedStatement preparedStatement = sqlHelper.takePreparedStatement(connection,
					SQLHelper.UPDATE_USER_PASSWORD_QUERY);
			preparedStatement.setString(1, newPassword);
			preparedStatement.setInt(2, id);

			preparedStatement.executeUpdate();

			connection.commit();

		} catch (SQLException exception) {

			try {
				connection.rollback();

			} catch (SQLException e) {

				log.log(Level.ERROR, "Ошибка во время отката изменений в БД");
				throw new DAOException("error during rollback of changes in database", exception);
			}

			log.log(Level.ERROR, "Ошибка во время изменения пароля пользователя");
			throw new DAOException("error while retrieving users", exception);

		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					log.log(Level.ERROR, "Ошибка во время закрытия соединения");
					throw new DAOException("Error while closing connection", e);
				}
			}
		}
		
		return message;
	}

	@Override
	public String deleteUserAccount(ArrayList<Integer> idDeletedUsers) throws DAOException {

		String message = null;

		Connection connection = sqlHelper.takeConnection();
		PreparedStatement preparedStatement;

		try {
			connection.setAutoCommit(false);

			for (int i = 0; i < idDeletedUsers.size(); i++) {
				preparedStatement = sqlHelper.takePreparedStatement(connection,
						SQLHelper.DELETE_FROM_DATA_USERS_BY_USER_ID_QUERY);
				preparedStatement.setInt(1, idDeletedUsers.get(i));
				preparedStatement.executeUpdate();
			}

			for (int i = 0; i < idDeletedUsers.size(); i++) {
				preparedStatement = sqlHelper.takePreparedStatement(connection,
						SQLHelper.DELETE_FROM_BOOKS_ORDERS_BY_USER_ID_QUERY);
				preparedStatement.setInt(1, idDeletedUsers.get(i));
				preparedStatement.executeUpdate();
			}

			for (int i = 0; i < idDeletedUsers.size(); i++) {
				preparedStatement = sqlHelper.takePreparedStatement(connection,
						SQLHelper.DELETE_FROM_ORDERS_BY_USER_ID_QUERY);
				preparedStatement.setInt(1, idDeletedUsers.get(i));
				preparedStatement.executeUpdate();
			}

			for (int i = 0; i < idDeletedUsers.size(); i++) {
				preparedStatement = sqlHelper.takePreparedStatement(connection,
						SQLHelper.DELETE_FROM_USERS_BY_ID_QUERY);
				preparedStatement.setInt(1, idDeletedUsers.get(i));
				preparedStatement.executeUpdate();
			}

			connection.commit();

		} catch (SQLException exception) {

			message = "Ошибка при удалении аккаунта пользователя";

			try {

				connection.rollback();

			} catch (SQLException e) {

				log.log(Level.ERROR, "Ошибка во время отката изменений в БД");
				throw new DAOException("error during rollback of changes in database", exception);
			}

			log.log(Level.ERROR, "Ошибка во время удаления пользователя");
			throw new DAOException("error while deleted users", exception);

		} finally {

			if (connection != null) {

				try {
					connection.close();
				} catch (SQLException e) {
					log.log(Level.ERROR, "Ошибка во время закрытия соединения");
					throw new DAOException("Error while closing connection", e);
				}
			}
		}

		return message;
	}

}
