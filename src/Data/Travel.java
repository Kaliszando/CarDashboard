package Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Controller.InvalidDateException;
import Controller.InvalidNumberException;

/**
 * Klasa reprezentuj�ca podr�.
 * 
 * Umo�liwia wykonanie operacji na takich warto�ciach jak data pocz�tkowa, data ko�cowa, �rednie spalanie, odleg�o��, itd.
 * Pozwala odczytywa� i ustawia� warto�ci podr�y.
 * 
 * @author Adam Kalisz
 * @author Kamil Rojszczak
 * @version 1.0
 * 
 */
public class Travel implements Cloneable, Serializable {

	private static final long serialVersionUID = 4725391663616879476L;
	private float length;
	private float mileage;
	private float avgFuelConsumption;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * Konstruktor przyjmuje warto�ci dystansu, przebiegu, zu�ycia paliwa, roku, miesi�ca, dnia.
	 * @param length dystans
	 * @param mileage przebieg
	 * @param fuelConsumption zu�ycie paliwa
	 * @param year rok
	 * @param month miesi�c
	 * @param day dzie�
	 */
	public Travel(float length, float mileage, float fuelConsumption, int year, int month, int day) {
		this.length = length;
		this.mileage = mileage;
		this.avgFuelConsumption = fuelConsumption;
		this.startDate = LocalDateTime.of(year, month, day, 0, 0);
	}
	
	/**
	 * Konstruktor przyjmuje warto�ci dystansu, przebiegu, zu�ycia paliwa, daty jako zmiennej String.
	 * @param length dystans
	 * @param mileage przebieg
	 * @param fuelConsumption zu�ycie paliwa
	 * @param date data zapisana jako sekwencja znak�w
	 */
	public Travel(float length, float mileage, float fuelConsumption, String date) {
		this.length = length;
		this.mileage = mileage;
		this.avgFuelConsumption = fuelConsumption;
		this.startDate = LocalDateTime.parse(date);
	}
	
	/**
	 * Konstruktor przyjmuje warto�ci dystansu, przebiegu, zu�ycia paliwa, daty jako zmiennej LocalDateTime.
	 * @param length dystans
	 * @param mileage przebieg
	 * @param fuelConsumption zu�ycie paliwa
	 * @param date data przy pomocy ISO-8601
	 */
	public Travel(float length, float mileage, float fuelConsumption, LocalDateTime date) {
		this.length = length;
		this.mileage = mileage;
		this.avgFuelConsumption = fuelConsumption;
		this.startDate = date;
	}
	
	/**
	 * Klonuje obiekt.
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	/**
	 * Zwraca dane podr�y jako String.
	 * @return dane podr�y
	 */
	public String toString() {
		return "Start time: " + startDate.format(timeFormat) + "\n" +
				"End time: " + endDate.format(timeFormat) + "\n" +
				"Distance: " + length + "\n" +
				"Total mileage: " + mileage + "\n" +
				"Avg. fuel consumption: " + avgFuelConsumption;
	}
	
	/**
	 * Ustawia nowy dystans dla podr�y.
	 * @param newLength nowy dystans zapisany jako float
	 * @throws InvalidNumberException wyj�tek rzucony gdy d�ugo�� jest ujemna
	 */
	public void setLength(float newLength) throws InvalidNumberException {
		if(newLength < 0)
			throw new InvalidNumberException(String.valueOf(newLength) + " d�ugo�� podr�y nie mo�e by� ujemna");
		this.length = newLength;
	}
	
	/**
	 * Ustawia nowy przebieg dla podr�y.
	 * @param newMileage nowy przebieg zapisany jako float
	 */
	public void setMileage(float newMileage) {
		this.mileage = newMileage;
	}
	
	/**
	 * Ustawia nowe �rednie zu�ycie paliwa.
	 * @param newAvgFuelConsumption nowe zu�ycie paliwa jako float
	 */
	public void setAvgFuelConsumption(float newAvgFuelConsumption) {
		this.avgFuelConsumption = newAvgFuelConsumption;
	}
	
	/**
	 * Ustawia now� dat� pocz�tku podr�y i sprawdza jej poprawo��.
	 * @param year rok
	 * @param month miesi�c
	 * @param day dzie�
	 * @throws InvalidDateException wyj�tek rzucony gdy data jest niepoprawna
	 */
	public void setStartDate(int year, int month, int day) throws InvalidDateException {
		LocalDateTime newDate = LocalDateTime.of(year, month, day, 0, 0);
		if(LocalDateTime.now().compareTo(newDate) < 0) {
			throw new InvalidDateException(newDate.toString() + " podana data jest z przysz�o�ci");
		}
		this.startDate = newDate;
	}
	
	/**
	 * Ustawia now� dat� pocz�tku podr�y i sprawdza jej poprawo��.
	 * @param date nowa data pocz�tku podr�y
	 * @throws InvalidDateException wyj�tek rzucony gdy data jest niepoprawna
	 */
	public void setStartDate(String date) throws InvalidDateException {
		LocalDateTime newDate = LocalDateTime.parse(date);
		if(LocalDateTime.now().compareTo(newDate) < 0) {
			throw new InvalidDateException(newDate.toString() + " podana data jest z przysz�o�ci");
		}
		this.startDate = newDate;
	}
	
	/**
	 * Zwraca dystans podr�y.
	 * @return  dystans jako float
	 */
	public float getLength() {
		return this.length;
	}
	
	/**
	 * Zwraca przebieg podr�y.
	 * @return przebieg jako float
	 */
	public float getMileage() {
		return this.mileage;
	}
	
	/**
	 * Zwraca �rednie zu�ycie paliwa.
	 * @return �rednie zu�ycie paliwa jako float
	 */
	public float getAvgFuelConsumption() {
		return this.avgFuelConsumption;
	}
	
	/**
	 * Zwraca dat� pocz�tkow� jako String.
	 * @return data pocz�tkowa
	 */
	public String getStartDateString() {
		return this.startDate.format(timeFormat);
	}
	
	/**
	 * Zwraca dat� ko�cow� jako String.
	 * @return data ko�cowa
	 */
	public String getEndDateString() {
		return this.endDate.format(timeFormat);
	}
	
	/**
	 * Zwraca dat� pocz�tkow�.
	 * @return data pocz�tkowa
	 */
	public LocalDateTime getStartDate() {
		return this.startDate;
	}

	/**
	 * Zwraca dat� ko�cow�.
	 * @return data ko�cowa
	 */
	public LocalDateTime getEndDate() {
		return endDate;
	}

	/**
	 * Ustawia dat� zako�czenia podr�y.
	 * @param endDate nowa data zako�czenia
	 */
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
}
