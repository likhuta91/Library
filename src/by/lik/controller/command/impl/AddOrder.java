package by.lik.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.lik.bean.User;
import by.lik.controller.command.Command;
import by.lik.controller.helper.CommandHelper;
import by.lik.service.OrderService;
import by.lik.service.ServiceFactory;
import by.lik.service.exception.ServiceException;

public class AddOrder implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		CommandHelper commandHelper = CommandHelper.getInstance();
		commandHelper.logOutIfUserNotAuthorized(request, response);
		
		String[] booksInBasket = request.getParameterValues(CommandHelper.ID);

		if (booksInBasket != null) {
			
			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			OrderService orderService = serviceFactory.getOrderService();

			String message;

			User user = (User) request.getSession().getAttribute(CommandHelper.MY_USER);

			try {
				message = orderService.addOrder(user.getId(), booksInBasket);

				if (message == null) {

					request.getSession().setAttribute(CommandHelper.MESSAGE, "Ваш заказ успешно отправлен");
					
					commandHelper.deleteBookFromBasket(request, booksInBasket);

				} else {

					request.getSession().setAttribute(CommandHelper.MESSAGE, message);
				}

			} catch (ServiceException e) {

				request.getSession().setAttribute(CommandHelper.MESSAGE,
						"Произошла непредвиденная ошибка, попробуйте повторно отправить заказ");
			}

		}
		
		response.sendRedirect(request.getSession().getAttribute(CommandHelper.URL).toString());
	}

}
