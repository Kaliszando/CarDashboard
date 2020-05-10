package Controller;
import java.util.Comparator;

import Data.Travel;

public class TravelLengthComparator implements Comparator<Travel> {

	@Override
	public int compare(Travel o1, Travel o2) {
		return Integer.compare(o1.getLength(), o2.getLength());
	}

}
