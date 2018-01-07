package by.lik.service;

import java.util.ArrayList;

import by.lik.bean.Order;
import by.lik.service.exception.ServiceException;

public interface OrderService  {
	ArrayList<Order> takeUserOrder(int userId)throws ServiceException;
}
