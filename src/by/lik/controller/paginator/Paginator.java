package by.lik.controller.paginator;

import java.util.ArrayList;

public class Paginator<E> {

	public ArrayList<E> returnSelectedListOfValues(ArrayList<E> list, int currentPageNumber, int numberOfItemsDisplayedOnThePage) {

		ArrayList<E> resultList = new ArrayList<>();

		int lastItemNumber = currentPageNumber * numberOfItemsDisplayedOnThePage;
		int firstItemNumber = lastItemNumber - numberOfItemsDisplayedOnThePage;

		if (list.size() < lastItemNumber) {
			lastItemNumber = list.size();
		}

		for (int i = firstItemNumber; i < lastItemNumber; i++) {
			resultList.add(list.get(i));
		}

		return resultList;
	}

}
