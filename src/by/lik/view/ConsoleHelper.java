package by.lik.view;

import java.util.Scanner;

public class ConsoleHelper {

	private static Scanner scanner = new Scanner(System.in);

	public String inputString() {

		String parametr = null;
		
		do {
			if (scanner.hasNextLine()) {
				parametr = scanner.nextLine();
			}
		} while (parametr == null);

		return parametr;
	}

	public int inputInteger() {

		int parametr = 111;

		do {
			if (scanner.hasNextInt()) {
				parametr = scanner.nextInt();
			}else {
				System.out.println("Введено не число. Повторите ввод");
				scanner.nextLine();
			}
		} while (parametr == 111);
		scanner.nextLine();
		return parametr;
	}

}