package by.lik.main;

import by.lik.view.View;

public class Main {

	public static void main(String[] args) {
		
		View view = new View();
		Thread viewThread = new Thread(view);
		viewThread.start();
		
	}

}