package by.lik.dao;

import by.lik.dao.impl.SQLBookDao;
import by.lik.dao.impl.SQLOrderDao;
import by.lik.dao.impl.SQLUserDao;

public class DAOFactory {
	private static final DAOFactory daoFactory = new DAOFactory();
	private final BookDao sqlBookDao = new SQLBookDao();
	private final UserDao sqlUserDao = new SQLUserDao();
	private final OrderDao sqlOrderDao = new SQLOrderDao();
	
	public BookDao getBookDao() {
		
		return sqlBookDao;
	}
	
	public UserDao getUserDao() {
		
		return sqlUserDao;
	}
	
	public OrderDao getSqlOrderDao() {
		return sqlOrderDao;
	}
	
	public static DAOFactory getDaoFactory() {
		
		return daoFactory;
	}
	
}
