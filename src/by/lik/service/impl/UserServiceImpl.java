package by.lik.service.impl;

import java.util.ArrayList;
import java.util.regex.Pattern;
import by.lik.bean.User;
import by.lik.dao.DAOFactory;
import by.lik.dao.UserDao;
import by.lik.dao.exception.DAOException;
import by.lik.service.UserService;
import by.lik.service.exception.ServiceException;

public class UserServiceImpl implements UserService {

	private DAOFactory daoFactory = DAOFactory.getDaoFactory();
	private UserDao sqlUserDao = daoFactory.getUserDao();

	@Override
	public User logination(String login, String password) throws ServiceException {

		User user = null;

		try {
			user = sqlUserDao.logination(login, password);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}
		return user;
	}

	@Override
	public String registration(User user) throws ServiceException {

		String registered = validationUser(user);
		
		if (registered == null) {
			try {
				registered = sqlUserDao.registration(user);
			} catch (DAOException e) {
				throw new ServiceException("smth wrong", e);
			}
		}

		return registered;

	}

	private String validationUser(User user) throws ServiceException {
		String passwordValidate = "[a-zA-Z\\d]+";
		String nameValidate = "([a-zA-Z\\- ]+)||([а-яА-Я\\- ]+)";
		String emailValidate = "[.[^@\\s]]+@([.[^@\\s]]+)\\.([a-z]+)";

		if (!Pattern.matches(passwordValidate, user.getPassword())) {
			return "В пароле использованы недопустимые символы";
		}
		if (!Pattern.matches(emailValidate, user.getEmail())) {
			return "В адресе электронной почты использованы недопустимые символы";
		}
		if (!Pattern.matches(nameValidate, user.getName())) {
			return "В имени использованы недопустимые символы";
		}
		if (!Pattern.matches(nameValidate, user.getSurname())) {
			return "В фамилии использованы недопустимые символы";
		}
		if (!Pattern.matches(nameValidate, user.getCountry())) {
			return "В названии страны использованы недопустимые символы";
		}
		if (!Pattern.matches(nameValidate, user.getCity())) {
			return "В названии города использованы недопустимые символы";
		}

		return null;
	}

	@Override
	public ArrayList<User> getAllUsers() throws ServiceException {
		ArrayList<User> allUsers = null;
		try {
			allUsers = sqlUserDao.takeAllUsers();
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}
		return allUsers;
	}

}
