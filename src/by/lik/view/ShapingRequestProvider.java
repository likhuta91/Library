package by.lik.view;

import java.util.HashMap;
import java.util.Map;

import by.lik.view.request.ShapingRequest;
import by.lik.view.request.impl.AddBookConsole;
import by.lik.view.request.impl.LoginationUserConsole;
import by.lik.view.request.impl.RegistrationUserConsole;
import by.lik.view.request.impl.SearchBookByAuthorConsole;
import by.lik.view.request.impl.SearchBookByTitleConsole;

public class ShapingRequestProvider {

	private Map<Integer, ShapingRequest> shapeRequestMap = new HashMap<>();

	public ShapingRequestProvider() {
		shapeRequestMap.put(1, new RegistrationUserConsole());
		shapeRequestMap.put(2, new LoginationUserConsole());
		shapeRequestMap.put(3, new AddBookConsole());
		shapeRequestMap.put(4, new SearchBookByAuthorConsole());
		shapeRequestMap.put(5, new SearchBookByTitleConsole());
	}

	public ShapingRequest takeShapeRequest(int number) {

		return shapeRequestMap.get(number);
	}

}
