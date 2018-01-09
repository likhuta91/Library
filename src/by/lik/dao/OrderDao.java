package by.lik.dao;

import java.util.ArrayList;

import by.lik.bean.Order;
import by.lik.dao.exception.DAOException;

public interface OrderDao {
	
	ArrayList<Order> takeUserOrder (int userId) throws DAOException;
	String addOrder(int userId, String[] bookId) throws DAOException;
	
}
