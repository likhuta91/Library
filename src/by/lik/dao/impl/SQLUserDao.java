package by.lik.dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import by.lik.bean.User;
import by.lik.dao.UserDao;
import by.lik.dao.exception.DAOException;

public class SQLUserDao implements UserDao {

	private String selectAllUsersQuery() {
		return "SELECT data_users.name AS name, data_users.surname AS surname, data_users.city AS city, data_users.country AS country,"
				+ "data_users.email AS email, users.login AS login, users.rating AS rating, user_status.status AS status "
				+ " FROM users INNER JOIN data_users ON data_users.users_id = users.id "
				+ "INNER JOIN user_status ON user_status.id = user_status_id";
	}

	private String selectUserQuery() {
		return "SELECT data_users.name AS name, data_users.surname AS surname, data_users.city AS city, data_users.country AS country,"
				+ "data_users.email AS email, users.login AS login, users.rating AS rating, user_status.status AS status "
				+ "FROM users INNER JOIN data_users ON data_users.users_id = users.id "
				+ "INNER JOIN user_status ON user_status.id = user_status_id WHERE login= ? AND password= ? ";
	}

	private String selectUserLoginQuery() {
		return "SELECT login FROM users WHERE login= ?";
	}

	private String selectUserEmailQuery() {
		return "SELECT email FROM data_users WHERE email= ?";
	}

	private String selectUserIdQuery() {
		return "SELECT id FROM users WHERE login= ?";
	}

	private String insertUserQuery() {
		return "INSERT INTO users (login,password,rating,user_status_id) value (?,?,?,?)";
	}

	private String insertUserDataQuery() {
		return "INSERT INTO data_users (name,surname,city,country,email,users_id) value (?,?,?,?,?,?)";
	}

	private String deleteUserQuery() {
		return "DELETE FROM users WHERE login = ?";
	}

	private Connection takeConnection() {

/*		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("/property/loginationDatabase.properties");

		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException exception) {
			new DAOException("error while read Database.properties", exception);
		}
		String url = properties.getProperty("URL");
		String root = properties.getProperty("ROOT");
		String password = properties.getProperty("PASSWORD");

		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, root, password);
		} catch (SQLException exception) {
			exception.printStackTrace(); // stub
			new DAOException("error while starting connection", exception);
		}
		*/
		Connection connection = null;
		InitialContext initContext;
		try {
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/appname");
		    connection = ds.getConnection();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} /*finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}*/
		

		return connection;
	}

	private PreparedStatement takePreparedStatement(Connection connection, String sqlQuery) {

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sqlQuery);
		} catch (SQLException exception) {
			exception.printStackTrace(); // stub
			new DAOException("error while creating PreparedStatement", exception);
		}

		return preparedStatement;
	}

	private ResultSet takeResultSet(PreparedStatement preparedStatement) {

		ResultSet resultSet = null;
		try {
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace(); // stub
			new DAOException("error while creating ResultSet", e);
		}

		return resultSet;
	}

	private User createUser(ResultSet resultSet) throws DAOException {

		User user = new User();

		try {
			user.setName(resultSet.getString("name"));
			user.setSurname(resultSet.getString("surname"));
			user.setCity(resultSet.getString("city"));
			user.setCountry(resultSet.getString("country"));
			user.setEmail(resultSet.getString("email"));
			user.setLogin(resultSet.getString("login"));
			user.setRating(resultSet.getInt("rating"));
			user.setStatus(resultSet.getString("status"));

		} catch (SQLException e) {
			e.printStackTrace(); // stub
			new DAOException("error while creating user", e);
		}

		return user;
	}

	public int takeUserId(String login) throws DAOException {

		int id = 0;
		Connection connection = takeConnection();
		PreparedStatement preparedStatement = takePreparedStatement(connection, selectUserIdQuery());
		try {
			preparedStatement.setString(1, login);
			ResultSet resultSet = takeResultSet(preparedStatement);

			if (resultSet.next()) {
				id = resultSet.getInt("id");
			}
		} catch (SQLException exception) {
			new DAOException("error when retrieving the user id from the database", exception);
		}

		return id;
	}

	private boolean isArgumentFree(String argument, String sqlQuery) throws DAOException {

		try (Connection connection = takeConnection();) {

			PreparedStatement preparedStatement = takePreparedStatement(connection, sqlQuery);
			preparedStatement.setString(1, argument);
			ResultSet resultSet = takeResultSet(preparedStatement);

			if (resultSet.next()) {
				return false;
			}

		} catch (SQLException exception) {
			new DAOException("error while checking for an argument in the database", exception);
		}

		return true;
	}

	private boolean executeQuery(String argument, String sqlQuery) throws DAOException {

		try (Connection connection = takeConnection();) {

			PreparedStatement preparedStatement = takePreparedStatement(connection, sqlQuery);
			preparedStatement.setString(1, argument);
			ResultSet resultSet = takeResultSet(preparedStatement);

			if (resultSet.next()) {
				return false;
			}

		} catch (SQLException exception) {
			new DAOException("error while executing the operation with the database", exception);
		}

		return true;
	}

	private boolean executeQuery(String argument1, String argument2, int argument3, String argument4, String sqlQuery)
			throws DAOException {

		try (Connection connection = takeConnection();) {

			PreparedStatement preparedStatement = takePreparedStatement(connection, sqlQuery);
			preparedStatement.setString(1, argument1);
			preparedStatement.setString(2, argument2);
			preparedStatement.setInt(3, argument3);
			preparedStatement.setString(4, argument4);

			preparedStatement.executeUpdate();

		} catch (SQLException exception) {
			new DAOException("error while executing the operation with the database", exception);
		}

		return true;
	}

	private boolean executeQuery(String argument1, String argument2, String argument3, String argument4,
			String argument5, int argument6, String sqlQuery) throws DAOException {

		try (Connection connection = takeConnection();) {

			PreparedStatement preparedStatement = takePreparedStatement(connection, sqlQuery);
			preparedStatement.setString(1, argument1);
			preparedStatement.setString(2, argument2);
			preparedStatement.setString(3, argument3);
			preparedStatement.setString(4, argument4);
			preparedStatement.setString(5, argument5);
			preparedStatement.setInt(6, argument6);

			preparedStatement.executeUpdate();

		} catch (SQLException exception) {
			new DAOException("error while executing the operation with the database", exception);
		}

		return true;
	}

	@Override
	public User logination(String login, String password) throws DAOException {

		User user = null;
		Connection connection = null;
		PreparedStatement preparedStatement;
		ResultSet resultSet;

		try {
			connection = takeConnection();
			preparedStatement = takePreparedStatement(connection, selectUserQuery());
			preparedStatement.setString(1, login);
			preparedStatement.setString(2, password);
			resultSet = takeResultSet(preparedStatement);

			if (resultSet.next()) {
				user = createUser(resultSet);
			}

		} catch (SQLException e) {
			e.printStackTrace(); // stub
			new DAOException("error while retrieving users", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace(); // stub
					new DAOException("error while closing connection", e);
				}
			}
		}

		return user;
	}

	@Override
	public String registration(User user) throws DAOException {

		if (!isArgumentFree(user.getLogin(), selectUserLoginQuery())) {
			return "Пользователь с таким логином уже зарегистрирован";
		}

		if (!isArgumentFree(user.getEmail(), selectUserEmailQuery())) {
			return "Пользователь с таким email уже зарегистрирован";
		}

		if (executeQuery(user.getLogin(), user.getPassword(), user.getRating(), user.getStatus(), insertUserQuery())) {
			int id = takeUserId(user.getLogin());

			if (executeQuery(user.getName(), user.getSurname(), user.getCity(), user.getCountry(), user.getEmail(), id,
					insertUserDataQuery())) {
				return "true";
			} else {
				executeQuery(user.getLogin(), deleteUserQuery());
			}
		}

		return "По неизвестной причине пользователь не зарегистрирован";
	}

	@Override
	public ArrayList<User> takeAllUsers() throws DAOException {

		User user = null;
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		ArrayList<User> allUsers = new ArrayList<>();

		try (Connection connection = takeConnection();) {
			preparedStatement = takePreparedStatement(connection, selectAllUsersQuery());
			resultSet = takeResultSet(preparedStatement);

			while (resultSet.next()) {
				user = createUser(resultSet);
				allUsers.add(user);
			}

		} catch (SQLException e) {
			new DAOException("error while retrieving users", e);
		}

		return allUsers;
	}

}
