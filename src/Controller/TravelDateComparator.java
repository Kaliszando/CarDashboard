package Controller;
import java.util.Comparator;

import Data.Travel;

/**
 * Klasa s³u¿y do porównywania obiektów klasy Travel na podstawie dat pocz¹kowych.
 * Implementuje interfejs Comparator.
 * @author Adam Kalisz
 * @author Kamil Rojszczak
 * @version 1.0
 */
public class TravelDateComparator implements Comparator<Travel> {

	/**
	 * Porównuje ze sob¹ dwa obiekty klasy Travel
	 * @param o1 pierwszy argument klasy Travel
	 * @param o2 drugi argument klasy Travel
	 * @return zwraca wartoœci 1, 0 lub -1 w zale¿noœci czy pierwszy argument jest wiêkszy, równy lub mniejszy od drugiego 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Travel o1, Travel o2) {
		return o1.getStartDate().compareTo(o2.getStartDate());
	}

}
