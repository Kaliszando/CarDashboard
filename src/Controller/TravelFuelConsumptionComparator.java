package Controller;
import java.util.Comparator;

import Data.Travel;

public class TravelFuelConsumptionComparator implements Comparator<Travel> {

	@Override
	public int compare(Travel o1, Travel o2) {
		return Float.compare(o1.getAvgFuelConsumption(), o2.getAvgFuelConsumption());
	}

}
