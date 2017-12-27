package by.lik.view;

import by.lik.controller.FrontController;
import by.lik.view.request.ShapingRequest;

public class View implements Runnable {

	@Override
	public void run() {

		ConsoleHelper consoleHelper = new ConsoleHelper();
		@SuppressWarnings("unused")
		FrontController controller = new FrontController();
		ShapingRequestProvider shapingRequestProvider = new ShapingRequestProvider();

		ShapingRequest shapingRequest = null;
		
		@SuppressWarnings("unused")
		String request;
		@SuppressWarnings("unused")
		String response;

		int numberCommand;

		while (true) {
			System.out.println("Введи:\n" + "1. Регистрация пользователя\n" + "2. Логинация пользователя\n"
					+ "3. Добавление книги\n" + "4. Поиск книги по автору\n" + "5. Поиск книги по названию\n");
			
			numberCommand = consoleHelper.inputInteger();

			shapingRequest = shapingRequestProvider.takeShapeRequest(numberCommand);

			try {
				request = shapingRequest.shape();
				//response = controller.doAction(request);
			} catch (Exception e) {
				response = "Операция не выполнена. Повторите попытку.";
			}

			//System.out.println(response);
		}

	}

}