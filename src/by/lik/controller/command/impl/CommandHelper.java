package by.lik.controller.command.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class CommandHelper {
	public String takeURL(HttpServletRequest request) {

		Map<String, String[]> parameterMap = request.getParameterMap();
		String url = request.getRequestURL().toString() + "?";
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
