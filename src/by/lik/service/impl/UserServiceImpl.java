package by.lik.service.impl;

import java.util.ArrayList;
import by.lik.bean.User;
import by.lik.dao.DAOFactory;
import by.lik.dao.UserDao;
import by.lik.dao.exception.DAOException;
import by.lik.service.UserService;
import by.lik.service.exception.ServiceException;
import by.lik.service.validation.Validation;

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
		
		Validation validation = Validation.getInstance();
		String message = validation.validation(user);

		if (message == null) {
			try {
				message = sqlUserDao.registration(user);
			} catch (DAOException e) {
				throw new ServiceException("smth wrong", e);
			}
		}

		return message;

	}

	@Override
	public ArrayList<User> takeAllUsers() throws ServiceException {
		
		ArrayList<User> allUsers = null;
		try {
			allUsers = sqlUserDao.takeAllUsers();
			
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}
		
		return allUsers;
	}

	@Override
	public String сhangePassword(String newPassword, int id) throws ServiceException {
		
		Validation validation = Validation.getInstance();
		String message = validation.validation(newPassword);

		if (message == null) {
			try {
				message = sqlUserDao.сhangePassword(newPassword, id);
				
			} catch (DAOException e) {
				throw new ServiceException("smth wrong", e);
			}
		}

		return message;
	}

}
