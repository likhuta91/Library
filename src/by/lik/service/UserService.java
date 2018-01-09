package by.lik.service;

import java.util.ArrayList;
import by.lik.bean.User;
import by.lik.service.exception.ServiceException;

public interface UserService {
	
	User logination(String login, String password)throws ServiceException;
	String registration(User user)throws ServiceException;
	ArrayList<User> takeAllUsers()throws ServiceException;
	String сhangePassword(String newPassword, int id)throws ServiceException;
	String deleteUserAccount (String[] idDeletedUsers)throws ServiceException;
	
}
