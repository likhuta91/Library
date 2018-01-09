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

	public int takeUserId(String login) throws DAOException {

		int id = 0;

		try (Connection connection = sqlHelper.takeConnection()) {

			PreparedStatement preparedStatement = sqlHelper.takePreparedStatement(connection,
					SQLHelper.SELECT_USER_ID_QUERY);
			preparedStatement.setString(1, login);
			ResultSet resultSet = sqlHelper.takeResultSet(preparedStatement);

			if (resultSet.next()) {
				id = resultSet.getInt(SQLHelper.ID);
			}

		} catch (SQLException exception) {

			log.log(Level.ERROR, "не получается взять Id пользователя из БД");
			throw new DAOException("error when retrieving the user id from the database", exception);
		}

		return id;
	}

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
	
	private boolean executeQuery(String argument1, int argument2, String sqlQuery)
			throws DAOException, SQLException {

		Connection connection = sqlHelper.takeConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = sqlHelper.takePreparedStatement(connection, sqlQuery);
			preparedStatement.setString(1, argument1);
			preparedStatement.setInt(2, argument2);

			preparedStatement.executeUpdate();
			connection.commit();

		} catch (SQLException exception) {

			connection.rollback();

			log.log(Level.ERROR, "не получается выполнить запрос " + sqlQuery);
			throw new DAOException("error while executing the operation with the database", exception);

		} finally {
			if (connection != null) {
				connection.close();
			}
		}

		return true;
	}

	private boolean executeQuery(String argument1, String argument2, int argument3, String argument4, String sqlQuery)
			throws DAOException, SQLException {

		Connection connection = sqlHelper.takeConnection();

		try {
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = sqlHelper.takePreparedStatement(connection, sqlQuery);
			preparedStatement.setString(1, argument1);
			preparedStatement.setString(2, argument2);
			preparedStatement.setInt(3, argument3);
			preparedStatement.setString(4, argument4);

			preparedStatement.executeUpdate();
			connection.commit();

		} catch (SQLException exception) {

			connection.rollback();

			log.log(Level.ERROR, "не получается выполнить запрос " + sqlQuery);
			throw new DAOException("error while executing the operation with the database", exception);

		} finally {
			if (connection != null) {
				connection.close();
			}
		}

		return true;
	}

	private boolean executeQuery(String argument1, String argument2, String argument3, String argument4,
			String argument5, int argument6, String sqlQuery) throws DAOException, SQLException {

		Connection connection = sqlHelper.takeConnection();
		
		try {
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = sqlHelper.takePreparedStatement(connection, sqlQuery);
			preparedStatement.setString(1, argument1);
			preparedStatement.setString(2, argument2);
			preparedStatement.setString(3, argument3);
			preparedStatement.setString(4, argument4);
			preparedStatement.setString(5, argument5);
			preparedStatement.setInt(6, argument6);

			preparedStatement.executeUpdate();

			connection.commit();

		} catch (SQLException exception) {

			connection.rollback();

			log.log(Level.ERROR, "не получается выполнить запрос " + sqlQuery); 
			throw new DAOException("error while executing the operation with the database", exception);

		} finally {
			if (connection != null) {
				connection.close();
			}
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

		try {

			executeQuery(user.getLogin(), user.getPassword(), user.getRating(), user.getStatus(),
					SQLHelper.INSERT_INTO_USERS_QUERY);

			int id = takeUserId(user.getLogin());

			executeQuery(user.getName(), user.getSurname(), user.getCity(), user.getCountry(), user.getEmail(), id,
					SQLHelper.INSERT_INTO_USERS_DATA_QUERY);

		} catch (SQLException exception) {
			
			log.log(Level.ERROR, "ошибка во время регистрации пользователя");
			throw new DAOException("error while retrieving users", exception);
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
			log.log(Level.ERROR, "ошибка во время получения списка всех пользователей");
			throw new DAOException("error while retrieving users", exception);
		}

		return allUsers;
	}

	@Override
	public String сhangePassword(String newPassword, int id) throws DAOException {
		
		String message = null;
		try {

			executeQuery(newPassword, id, SQLHelper.UPDATE_USER_PASSWORD_QUERY);

		} catch (SQLException exception) {

			log.log(Level.ERROR, "ошибка во время изменения пароля пользователя");
			throw new DAOException("error while retrieving users", exception);
		} 
		return message;
	}

	@Override
	public String deleteUserAccount(String[] idDeletedUsers) throws DAOException {
	
		String message = null;
		
Connection connection = sqlHelper.takeConnection();
PreparedStatement preparedStatement;
		
		try {
			connection.setAutoCommit(false);
			/*preparedStatement = sqlHelper.takePreparedStatement(connection, sqlQuery);
			preparedStatement.setString(1, argument1);
			preparedStatement.executeUpdate();*/

			connection.commit();

		} catch (SQLException exception) {

			//connection.rollback();

			log.log(Level.ERROR, "Ошибка во время удаления пользователя");
			throw new DAOException("error while deleted users", exception);

		} finally {
			if (connection != null) {
				//connection.close();
			}
		}


		
		
		
		return message;
	}

}
