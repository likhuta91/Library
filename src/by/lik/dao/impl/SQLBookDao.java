package by.lik.dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import by.lik.bean.Book;
import by.lik.dao.BookDao;
import by.lik.dao.exception.DAOException;

public class SQLBookDao implements BookDao {
	
	private String urlBase = "jdbc:mysql://127.0.0.1/Library?useSSL=false";
	String root = "root";
	String passwordBase = "86231612";
	
	private String selectAllBooksQuery() {
		return "SELECT title FROM books";
	}
	
private Connection takeConnection() {
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(urlBase, root, passwordBase);
		} catch (SQLException e) {
			e.printStackTrace(); //stub
			new DAOException("error while starting connection", e);
		}
		
		return connection;
	}

	private PreparedStatement takePreparedStatement(Connection connection, String sqlQuery) {
		
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sqlQuery);
		} catch (SQLException e) {
			e.printStackTrace(); //stub
			new DAOException("error while creating PreparedStatement", e);
		}
		
		return preparedStatement;
	}

	private ResultSet takeResultSet(PreparedStatement preparedStatement) {

		ResultSet resultSet = null;
		try {
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace(); //stub
			new DAOException("error while creating ResultSet", e);
		}

		return resultSet;
	}
	
	private Book createBook(ResultSet resultSet) throws DAOException {

		Book book = new Book();

		try {
			book.setTitle(resultSet.getString("title"));
			
		} catch (SQLException e) {
			e.printStackTrace(); //stub
			new DAOException("error while creating book", e);
		}

		return book;
	}
	
	private static final String booksFileName = "resourсes/books.txt";
	private BufferedReader reader;
	
	@Override
	public ArrayList<Book> showAllBooks() throws DAOException {
		
		ArrayList<Book> allBooks = new ArrayList<>();
		Book book;
		Connection connection = null;
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		

		try {
			connection = takeConnection();
			preparedStatement = takePreparedStatement(connection, selectAllBooksQuery());
			resultSet = takeResultSet(preparedStatement);

			while (resultSet.next()) {
				
				book = createBook(resultSet);
				allBooks.add(book);
			}

		} catch (SQLException e) {
			e.printStackTrace(); //stub
			new DAOException("error while retrieving users", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace(); //stub
					new DAOException("error while closing connection", e);
				}
			}
		}

		return allBooks;
	}

	@Override
	public boolean add(Book book) throws DAOException {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(booksFileName)));

			String bookId = "0";

			boolean bookExistInBase = false;

			while (reader.ready()) {

				bookId = reader.readLine();
				String bookAuthor = reader.readLine();
				String bookTitle = reader.readLine();
				reader.readLine(); // считывание строки с адресом карточки книги
				reader.readLine(); // считывание строки с адресом содержимого книги
				reader.readLine(); // считывание пустой строки

				if (bookTitle.equals(book.getTitle()) && bookAuthor.equals(book.getAuthor())) {
					bookExistInBase = true;
				}
			}

			reader.close();

			if (!bookExistInBase) {
				int nextBookId = Integer.parseInt(bookId) + 1;

				BufferedWriter writer = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(booksFileName, true)));

				writer.write("\n\n" + nextBookId);
				writer.write("\n" + book.getAuthor());
				writer.write("\n" + book.getTitle());
				writer.write("\n" + book.getCardBook());
				writer.write("\n" + book.getContent());
				writer.close();
				return true;
			}

		} catch (FileNotFoundException e) {
			throw new DAOException("smth happended", e);
		} catch (IOException e) {
			throw new DAOException("smth happended", e);
		}

		return false;

	}

	@Override
	public ArrayList<Book> searchByAuthor(String author) throws DAOException {
		ArrayList<Book> bookList = null;

		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(booksFileName)));

			Book book = null;

			while (reader.ready()) {

				book = readBookFromFile();

				if (book.getAuthor().equals(author)) {

					if (bookList == null) {
						bookList = new ArrayList<>();
					}

					bookList.add(book);
				}
			}

		} catch (FileNotFoundException e) {
			throw new DAOException("smth happended", e);
		} catch (IOException e) {
			throw new DAOException("smth happended", e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				throw new DAOException("smth happended", e);
			}
		}
		return bookList;
	}

	@Override
	public ArrayList<Book> searchByTitle(String title) throws DAOException {
		ArrayList<Book> bookList = null;

		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(booksFileName)));

			Book book = null;

			while (reader.ready()) {

				book = readBookFromFile();

				if (book.getTitle().equals(title)) {

					if (bookList == null) {
						bookList = new ArrayList<>();
					}

					bookList.add(book);
				}
			}

		} catch (FileNotFoundException e) {
			throw new DAOException("smth happended", e);
		} catch (IOException e) {
			throw new DAOException("smth happended", e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				throw new DAOException("smth happended", e);
			}
		}
		return bookList;
	}

	private Book readBookFromFile() throws DAOException {
		Book book = null;
		try {
			reader.readLine(); // считывание id книги
			String bookAuthor = reader.readLine();
			String bookTitle = reader.readLine();
			String bookCard = reader.readLine();
			String bookContent = reader.readLine();
			reader.readLine(); // считывание пустой строки

			book = new Book();
			book.setAuthor(bookAuthor);
			book.setTitle(bookTitle);
			book.setCardBook(bookCard);
			book.setContent(bookContent);

		} catch (IOException e) {
			throw new DAOException("smth happended", e);
		}

		return book;
	}

	

}
