package Controller;
import java.util.Comparator;

import Data.Travel;

/**
 * Klasa s�u�y do por�wnywania obiekt�w klasy Travel na podstawie �redniego spalania.
 * Implementuje interfejs Comparator.
 * @author Adam Kalisz
 * @author Kamil Rojszczak
 * @version 1.0
 */
public class TravelFuelConsumptionComparator implements Comparator<Travel> {

	/**
	 * Por�wnuje ze sob� 2 argumenty klasy Travel
	 * @param o1 pierwszy argument klasy Travel
	 * @param o2 drugi argument klasy Travel
	 * @return zwraca warto�ci 1, 0 lub -1 w zale�no�ci czy pierwszy argument jest wi�kszy, r�wny lub mniejszy od drugiego 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Travel o1, Travel o2) {
		return Float.compare(o1.getAvgFuelConsumption(), o2.getAvgFuelConsumption());
	}

}
