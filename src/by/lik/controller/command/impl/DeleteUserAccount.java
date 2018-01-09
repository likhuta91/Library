package by.lik.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.lik.controller.command.Command;
import by.lik.controller.helper.CommandHelper;
import by.lik.service.ServiceFactory;
import by.lik.service.UserService;
import by.lik.service.exception.ServiceException;

public class DeleteUserAccount implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CommandHelper commandHelper = CommandHelper.getInstance();
		commandHelper.logOutIfUserNotAuthorized(request, response);	
		
		String[] idDeletedUsers = request.getParameterValues((CommandHelper.DELETE_USER_ACCOUNT));
		String message;
		
		if(idDeletedUsers!=null) {
			
			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			UserService userService = serviceFactory.getUserService();
			
			try {
				message = userService.deleteUserAccount(idDeletedUsers);
				
				if(message==null) {
					
					request.getSession().setAttribute(CommandHelper.MESSAGE, "Пользователи успешно удалены");
				}else {

					request.getSession().setAttribute(CommandHelper.MESSAGE, message);
				}
				
			} catch (ServiceException e) {
				request.getSession().setAttribute(CommandHelper.MESSAGE, "Произошла непредвиденная ошибка, повторите попытку");
			}
			
		}
		
		response.sendRedirect(request.getSession().getAttribute(CommandHelper.URL).toString());
	}

}
