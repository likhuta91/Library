package by.lik.controller.helper;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class CommandHelper {

	private static volatile CommandHelper commandHelper;

	public static CommandHelper getInstance() {
		CommandHelper localInstance = commandHelper;
		if (localInstance == null) {
			synchronized (CommandHelper.class) {
				localInstance = commandHelper;
				if (localInstance == null) {
					commandHelper = localInstance = new CommandHelper();
				}
			}
		}
		return localInstance;
	}
	
	public static final int NUMBER_OF_ITEMS_DISPLAYED_ON_THE_PAGE = 10;

	public static final String URL = "url";
	
	public static final String GSP_NAME = "gspName";
	public static final String LOCAL = "local";
	public static final String MY_USER = "myUser";
	public static final String ALL_BOOKS = "allBooks";
	public static final String ALL_USERS = "allUsers";
	public static final String COMMAND = "command";
	
	public static final String LOGIN = "login";
	public static final String PASSWORD = "password";
	public static final String NAME = "name";
	public static final String SURNAME = "surname";
	public static final String CITY = "city";
	public static final String COUNTRY = "country";
	public static final String EMAIL = "email";
	
	
	public static final String MESSAGE = "message";
	public static final String CURRENT_PAGE_NUMBER = "currentPageNumber";
	public static final String NUMBER_OF_ALL_PAGES = "numberOfAllPages";

	public static final String INDEX_PATH = "index.jsp";
	public static final String USER_PATH = "/WEB-INF/jsp/user.jsp";
	public static final String LOGINATION_PATH = "/WEB-INF/jsp/logination.jsp";
	public static final String REGISTRATION_PATH = "/WEB-INF/jsp/registration.jsp";
	public static final String CHANGE_PASSWORD_PATH = "/WEB-INF/jsp/changePassword.jsp";
	public static final String GO_TO_GSP_COMMAND = "?command=goToGsp&gspName=";

	public String takeURL(HttpServletRequest request) {

		String url = request.getRequestURL().toString() + "?";

		Map<String, String[]> parameterMap = request.getParameterMap();
		int sizeMap = parameterMap.size();

		if (parameterMap != null) {
			for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
				sizeMap--;

				for (int i = 0; i < entry.getValue().length; i++) {

					url = url + entry.getKey() + "=" + entry.getValue()[i];
					if (sizeMap != 0 || i < entry.getValue().length - 1) {
						url = url + "&";
					}
				}

			}
		}

		return url;
	}

}
