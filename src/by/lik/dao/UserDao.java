package by.lik.dao;

import java.util.ArrayList;
import by.lik.bean.User;
import by.lik.dao.exception.DAOException;

public interface UserDao {
	User logination(String login, String password) throws DAOException;
	String registration(User user) throws DAOException;
	ArrayList<User> takeAllUsers()throws DAOException;
	String сhangePassword (String newPassword, int id)throws DAOException;
	String deleteUserAccount(String [] idDeletedUsers)throws DAOException;
}
