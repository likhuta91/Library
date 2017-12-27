package by.lik.dao;

import by.lik.dao.impl.SQLBookDao;
import by.lik.dao.impl.SQLUserDao;

public class DAOFactory {
	private static final DAOFactory daoFactory = new DAOFactory();
	private final BookDao sqlBookDao = new SQLBookDao();
	private final UserDao sqlUserDao = new SQLUserDao();
	
	public BookDao getBookDao() {
		return sqlBookDao;
	}
	public UserDao getUserDao() {
		
		return sqlUserDao;
	}
	public static DAOFactory getDaoFactory() {
		/*try {
			Class.forName("org.gjt.mm.mysql.Driver");

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}*/
		return daoFactory;
	}
	
}
