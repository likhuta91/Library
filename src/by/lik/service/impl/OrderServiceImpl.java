package by.lik.service.impl;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import by.lik.bean.Order;
import by.lik.dao.DAOFactory;
import by.lik.dao.OrderDao;
import by.lik.dao.exception.DAOException;
import by.lik.service.OrderService;
import by.lik.service.exception.ServiceException;

public class OrderServiceImpl implements OrderService {
	
	private static final Logger log = Logger.getLogger(OrderServiceImpl.class);
	private DAOFactory daoFactory = DAOFactory.getDaoFactory();
	private OrderDao sqlOrderDao = daoFactory.getSqlOrderDao();

	@Override
	public ArrayList<Order> takeUserOrder(int userId) throws ServiceException {
		
		ArrayList<Order> allOrders = null;

		try {
			allOrders = sqlOrderDao.takeUserOrder(userId);

		} catch (DAOException e) {
			
			log.log(Level.ERROR, "Ошибка при получении списка заказов пользователя");
			
			throw new ServiceException("smth wrong", e);
		}

		return allOrders;
	}

	@Override
	public String addOrder(int userId, String[] bookId) throws ServiceException {
		
		String message;
		
		try {
			message = sqlOrderDao.addOrder(userId, bookId);
			
		} catch (DAOException e) {
			
			message = "Произошла непредвиденная ошибка при отправке заказа";
			
			log.log(Level.ERROR, "Ошибка при добавлении заказа");
			
			throw new ServiceException("smth wrong", e);
		}
		return message;
	}

}
