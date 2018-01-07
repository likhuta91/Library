package by.lik.service.impl;

import java.util.ArrayList;

import by.lik.bean.Order;
import by.lik.dao.DAOFactory;
import by.lik.dao.OrderDao;
import by.lik.dao.exception.DAOException;
import by.lik.service.OrderService;
import by.lik.service.exception.ServiceException;

public class OrderServiceImpl implements OrderService{
	
	private DAOFactory daoFactory = DAOFactory.getDaoFactory();
	private OrderDao sqlOrderDao = daoFactory.getSqlOrderDao();
	
	@Override
	public ArrayList<Order> takeUserOrder(int userId) throws ServiceException {
		ArrayList<Order> allOrders = null;

		try {
			allOrders = sqlOrderDao.takeUserOrder(userId);

		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}

		return allOrders;
	}

}
