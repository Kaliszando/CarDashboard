package Controller;
import java.util.Comparator;

public class TravelDateComparator implements Comparator<Travel> {

	@Override
	public int compare(Travel o1, Travel o2) {
		return o1.getDate().compareTo(o2.getDate());
	}

}
