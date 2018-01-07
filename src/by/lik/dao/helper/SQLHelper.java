package by.lik.dao.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import by.lik.dao.exception.DAOException;

public class SQLHelper {

	private static final Logger log = Logger.getLogger(SQLHelper.class);

	private static volatile SQLHelper sqlHelper;

	private SQLHelper() {

	}

	public static SQLHelper getInstance() {
		SQLHelper localInstance = sqlHelper;
		if (localInstance == null) {
			synchronized (SQLHelper.class) {
				localInstance = sqlHelper;
				if (localInstance == null) {
					sqlHelper = localInstance = new SQLHelper();
				}
			}
		}
		return localInstance;
	}

	public static final String ID = "id";
	public static final String AUTHOR = "author";
	public static final String TITLE = "title";
	public static final String STATUS = "status";
	public static final String GENRE = "genre";

	public static final String NAME = "name";
	public static final String SURNAME = "surname";
	public static final String CITY = "city";
	public static final String COUNTRY = "country";
	public static final String EMAIL = "email";
	public static final String LOGIN = "login";
	public static final String RATING = "rating";

	public static final String SELECT_ALL_BOOKS_QUERY = "SELECT books.id AS id,books.title AS title,group_concat(DISTINCT genre.genre) AS genre,"
			+ "group_concat(DISTINCT authors.author) AS author, books_status.status AS status FROM books "
			+ "INNER JOIN books_genre AS BG2 ON BG2.books_id=books.id INNER JOIN genre ON BG2.genre_id=genre.id "
			+ "INNER JOIN books_authors ON books_authors.books_id=books.id "
			+ "INNER JOIN authors ON authors.id=books_authors.authors_id "
			+ "INNER JOIN books_status ON books_status.id=books.books_status_id group by books.id";

	public static final String SELECT_BOOKS_BY_ID_QUERY = "SELECT books.id AS id,books.title AS title,group_concat(DISTINCT genre.genre) AS genre,"
			+ "group_concat(DISTINCT authors.author) AS author,books_status.status AS status FROM books "
			+ "INNER JOIN books_genre AS BG2 ON BG2.books_id=books.id INNER JOIN genre ON BG2.genre_id=genre.id "
			+ "INNER JOIN books_authors ON books_authors.books_id=books.id "
			+ "INNER JOIN authors ON authors.id=books_authors.authors_id "
			+ "INNER JOIN books_status ON books_status.id=books.books_status_id WHERE books.id = ? group by books.id";

	public static final String SELECT_ORDER_BY_USER_ID_QUERY = "SELECT orders.id AS orderId, orders.date AS date,"
			+ "order_status.status AS orderStatus,"
			+ "books.id AS bookId, books.title AS title, group_concat(DISTINCT genre.genre) AS genre,"
			+ "group_concat(DISTINCT authors.author) AS author, books_status.status AS bookStatus"
			+ "FROM orders INNER JOIN order_status ON order_status.id=orders.order_status_id "
			+ "INNER JOIN books_orders ON books_orders.order_id=orders.id "
			+ "INNER JOIN books ON books.id=books_orders.book_id "
			+ "INNER JOIN books_genre AS BG2 ON BG2.books_id=books.id INNER JOIN genre ON BG2.genre_id=genre.id "
			+ "INNER JOIN books_authors ON books_authors.books_id=books.id "
			+ "INNER JOIN authors ON authors.id=books_authors.authors_id "
			+ "INNER JOIN books_status ON books_status.id=books.books_status_id "
			+ "WHERE orders.users_id = ? group by books.id";

	/*
	 * "SELECT orders.id AS id,orders.date AS date," +
	 * "order_status.status AS status group_concat(DISTINCT books.id) AS books," +
	 * "FROM orders " +
	 * "INNER JOIN order_status ON order_status.id=orders.order_status_id " +
	 * "INNER JOIN books ON books.id=books_orders.books_id " +
	 * "INNER JOIN books_orders ON books_orders.orders_id=orders.id " +
	 * "WHERE orders.users_id = ?";
	 */

	public static final String SELECT_BOOKS_BY_VALUE_QUERY = "SELECT orders.id AS id, orders.date AS date,"
			+ "order_status.status AS status, group_concat(DISTINCT books.id) AS book FROM orders "
			+ "INNER JOIN order_status ON order_status.id = orders.order_status_id "
			+ "INNER JOIN books_orders ON books_orders.order_id = orders.id "
			+ "INNER JOIN books ON books.id = books_orders.book_id WHERE orders.users_id = ? group by orders.id";

	public static final String SELECT_ALL_USERS_QUERY = "SELECT data_users.name AS name,data_users.surname AS surname,data_users.city AS city,"
			+ "data_users.country AS country,data_users.email AS email,users.login AS login,"
			+ "users.rating AS rating,users.id AS id,user_status.status AS status FROM users "
			+ "INNER JOIN data_users ON data_users.users_id = users.id "
			+ "INNER JOIN user_status ON user_status.id = user_status_id";

	public static final String SELECT_USER_QUERY = "SELECT data_users.name AS name,data_users.surname AS surname,data_users.city AS city,"
			+ "data_users.country AS country,data_users.email AS email,users.login AS login,"
			+ "users.rating AS rating,user_status.status AS status, users.id AS id FROM users "
			+ "INNER JOIN data_users ON data_users.users_id = users.id "
			+ "INNER JOIN user_status ON user_status.id = user_status_id WHERE login= ? AND password= ?";

	public static final String SELECT_USER_LOGIN_QUERY = "SELECT login FROM users WHERE login= ?";

	public static final String SELECT_USER_EMAIL_QUERY = "SELECT email FROM data_users WHERE email= ?";

	public static final String SELECT_USER_ID_QUERY = "SELECT id FROM users WHERE login= ?";

	public static final String INSERT_INTO_USERS_QUERY = "INSERT INTO users (login,password,rating,user_status_id) value (?,?,?,?)";

	public static final String INSERT_INTO_USERS_DATA_QUERY = "INSERT INTO data_users (name,surname,city,country,email,users_id) value (?,?,?,?,?,?)";

	public static final String UPDATE_USER_PASSWORD_QUERY = "UPDATE users SET password = ? where id = ?";

	public static final String DELETE_USER_QUERY = "DELETE FROM users WHERE login = ?";

	public Connection takeConnection() throws DAOException {

		Connection connection = null;
		InitialContext initContext;

		try {

			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/appname");
			connection = ds.getConnection();

		} catch (NamingException exception) {

			log.log(Level.ERROR, "Не обружен файл context.xml");
			throw new DAOException("error while read context ", exception);

		} catch (SQLException exception) {

			log.log(Level.ERROR, "Не получается взять connection из connection pool");
			throw new DAOException("error while take connection from connection pool", exception);
		}

		return connection;
	}

	public PreparedStatement takePreparedStatement(Connection connection, String sqlQuery) throws DAOException {

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sqlQuery);

		} catch (SQLException exception) {

			log.log(Level.ERROR, "Ошибка в sql запросе");
			throw new DAOException("error while creating PreparedStatement", exception);
		}

		return preparedStatement;
	}

	public ResultSet takeResultSet(PreparedStatement preparedStatement) throws DAOException {

		ResultSet resultSet = null;
		try {
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			log.log(Level.ERROR, "Не получется взять ResultSet");
			throw new DAOException("error while creating ResultSet", e);
		}

		return resultSet;
	}
}
