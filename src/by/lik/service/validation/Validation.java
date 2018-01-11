package by.lik.service.validation;

import java.util.regex.Pattern;

import by.lik.bean.User;
import by.lik.service.exception.ServiceException;

public class Validation {
	private static volatile Validation validation;

	public static Validation getInstance() {
		Validation localInstance = validation;
		if (localInstance == null) {
			synchronized (Validation.class) {
				localInstance = validation;
				if (localInstance == null) {
					validation = localInstance = new Validation();
				}
			}
		}
		return localInstance;
	}

	private final String PASSWORD_VALIDATE = "[a-zA-Z\\d]+";
	private String NAME_VALIDATE = "([a-zA-Z\\- ]+)||([а-яА-Я\\- ]+)";
	private String EMAIL_VALIDATE = "[.[^@\\s]]+@([.[^@\\s]]+)\\.([a-z]+)";

	public String validation(User user) throws ServiceException {

		if (!Pattern.matches(PASSWORD_VALIDATE, user.getPassword())) {
			return "В пароле использованы недопустимые символы";
		}
		if (!Pattern.matches(EMAIL_VALIDATE, user.getEmail())) {
			return "В адресе электронной почты использованы недопустимые символы";
		}
		if (!Pattern.matches(NAME_VALIDATE, user.getName())) {
			return "В имени использованы недопустимые символы";
		}
		if (!Pattern.matches(NAME_VALIDATE, user.getSurname())) {
			return "В фамилии использованы недопустимые символы";
		}
		if (!Pattern.matches(NAME_VALIDATE, user.getCountry())) {
			return "В названии страны использованы недопустимые символы";
		}
		if (!Pattern.matches(NAME_VALIDATE, user.getCity())) {
			return "В названии города использованы недопустимые символы";
		}

		return null;
	}

	public String validationPassword (String password) throws ServiceException {

		if (!Pattern.matches(PASSWORD_VALIDATE, password)) {
			
			return "В пароле использованы недопустимые символы";
		}

		return null;
	}

}
