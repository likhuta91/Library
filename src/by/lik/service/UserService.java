package by.lik.service;

import java.util.ArrayList;
import by.lik.bean.User;
import by.lik.service.exception.ServiceException;

public interface UserService {
	
	User logination(String login, String password)throws ServiceException;
	String registration(User user)throws ServiceException;
	ArrayList<User> getAllUsers()throws ServiceException;
	
}
