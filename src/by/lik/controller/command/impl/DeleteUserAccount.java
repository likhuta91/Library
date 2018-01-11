package by.lik.controller.command.impl;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.lik.bean.Order;
import by.lik.controller.command.Command;
import by.lik.controller.helper.CommandHelper;
import by.lik.service.OrderService;
import by.lik.service.ServiceFactory;
import by.lik.service.UserService;
import by.lik.service.exception.ServiceException;

public class DeleteUserAccount implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		CommandHelper commandHelper = CommandHelper.getInstance();
		commandHelper.logOutIfUserNotAuthorized(request, response);

		String[] idUsersWantDeleted = request.getParameterValues((CommandHelper.ID));

		ArrayList<Integer> idUsersWhichCanBeDeleted;
		String message;
		System.out.println(idUsersWantDeleted == null);
		if (idUsersWantDeleted != null) {

			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			OrderService orderService = serviceFactory.getOrderService();
			UserService userService = serviceFactory.getUserService();

			idUsersWhichCanBeDeleted = new ArrayList<>();
			ArrayList<Order> userOrders = null;
			int userId;

			for (String id : idUsersWantDeleted) {
				idUsersWhichCanBeDeleted.add(Integer.parseInt(id));
			}

			for (int i = 0; i < idUsersWantDeleted.length; i++) {

				userId = Integer.parseInt(idUsersWantDeleted[i]);
				try {
					userOrders = orderService.takeUserOrder(userId);
				} catch (ServiceException e) {
					request.getSession().setAttribute(CommandHelper.MESSAGE,
							"Не удалось приверить наличие не завершенных заказов пользователя");
				}

				if (userOrders != null) {
					for (Order order : userOrders) {
						if (!order.getStatus().equals("closed")) {
							idUsersWhichCanBeDeleted.remove((Object) userId);
						}
					}
				}
			}

			try {
				message = userService.deleteUserAccount(idUsersWhichCanBeDeleted);

				if (message == null) {

					if (idUsersWantDeleted.length == idUsersWhichCanBeDeleted.size()) {
						request.getSession().setAttribute(CommandHelper.MESSAGE,
								"Аккаунты пользователей успешно удалены");
					} else {
						request.getSession().setAttribute(CommandHelper.MESSAGE,
								"Не удалены аккаунты пользователей, у которых есть незавершенные заказы.");
					}

				} else {

					request.getSession().setAttribute(CommandHelper.MESSAGE, message);
				}

			} catch (ServiceException e) {
				request.getSession().setAttribute(CommandHelper.MESSAGE,
						"Произошла непредвиденная ошибка, повторите попытку");
			}
		}

		response.sendRedirect(request.getSession().getAttribute(CommandHelper.URL).toString());
	}

}
