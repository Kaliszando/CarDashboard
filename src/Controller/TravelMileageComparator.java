package Controller;
import java.util.Comparator;

import Data.Travel;

public class TravelMileageComparator implements Comparator<Travel> {

	@Override
	public int compare(Travel o1, Travel o2) {
		return Integer.compare(o1.getMileage(), o2.getMileage());
	}

}
