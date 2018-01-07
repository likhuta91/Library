package by.lik.dao;

import java.util.ArrayList;

import by.lik.bean.Order;
import by.lik.dao.exception.DAOException;

public interface OrderDao {
	
	public ArrayList<Order> takeUserOrder (int userId) throws DAOException;
	
}
