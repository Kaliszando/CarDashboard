package Controller;
import java.util.Comparator;

import Data.Travel;

public class TravelDateComparator implements Comparator<Travel> {

	@Override
	public int compare(Travel o1, Travel o2) {
		return o1.getStartDate().compareTo(o2.getStartDate());
	}

}
