package by.lik.controller.command.impl;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import by.lik.bean.Order;
import by.lik.bean.User;
import by.lik.controller.command.Command;
import by.lik.controller.helper.CommandHelper;
import by.lik.service.OrderService;
import by.lik.service.ServiceFactory;
import by.lik.service.exception.ServiceException;

public class TakeUserOrders implements Command {
	
	private static final Logger log = Logger.getLogger(TakeUserOrders.class);
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CommandHelper commandHelper = CommandHelper.getInstance();
		commandHelper.logOutIfUserNotAuthorized(request, response);
		
		String goToPage = CommandHelper.USER_ORDER_PATH;
		
		ArrayList<Order> userOrders = null;

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		OrderService orderService = serviceFactory.getOrderService();

		try {
			User user = (User)request.getSession().getAttribute(CommandHelper.MY_USER);
			userOrders = orderService.takeUserOrder(user.getId());

		} catch (ServiceException e) {
			log.log(Level.ERROR, "Ошибка при получении списка заказов пользователя");
			request.getSession().setAttribute(CommandHelper.MESSAGE, "Не удалось получить информацию о ваших заказах");
		}

		if (userOrders == null) {
			request.setAttribute(CommandHelper.MESSAGE, "У вас нет ни одного заказа");
			log.log(Level.DEBUG, "Cписок заказов пуст");
		} else {
			request.setAttribute(CommandHelper.ALL_BOOKS, userOrders);
			log.log(Level.DEBUG, "Cписок заказов не пустой");
		}
		
		String url = commandHelper.takeURL(request);
		request.getSession().setAttribute(CommandHelper.URL, url);

		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);

	}

}
