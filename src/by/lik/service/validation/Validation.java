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

	private String passwordValidate = "[a-zA-Z\\d]+";
	private String nameValidate = "([a-zA-Z\\- ]+)||([а-яА-Я\\- ]+)";
	private String emailValidate = "[.[^@\\s]]+@([.[^@\\s]]+)\\.([a-z]+)";

	public String validation(User user) throws ServiceException {

		if (!Pattern.matches(passwordValidate, user.getPassword())) {
			return "В пароле использованы недопустимые символы";
		}
		if (!Pattern.matches(emailValidate, user.getEmail())) {
			return "В адресе электронной почты использованы недопустимые символы";
		}
		if (!Pattern.matches(nameValidate, user.getName())) {
			return "В имени использованы недопустимые символы";
		}
		if (!Pattern.matches(nameValidate, user.getSurname())) {
			return "В фамилии использованы недопустимые символы";
		}
		if (!Pattern.matches(nameValidate, user.getCountry())) {
			return "В названии страны использованы недопустимые символы";
		}
		if (!Pattern.matches(nameValidate, user.getCity())) {
			return "В названии города использованы недопустимые символы";
		}

		return null;
	}

	public String validation(String password) throws ServiceException {

		if (!Pattern.matches(passwordValidate, password)) {
			return "В пароле использованы недопустимые символы";
		}

		return null;
	}

}
