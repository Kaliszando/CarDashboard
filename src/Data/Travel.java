package Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Controller.InvalidDateException;
import Controller.InvalidNumberException;

/**
 * Klasa reprezentuj¹ca podró¿.
 * 
 * Umo¿liwia wykonanie operacji na takich wartoœciach jak data pocz¹tkowa, data koñcowa, œrednie spalanie, odleg³oœæ, itd.
 * Pozwala odczytywaæ i ustawiaæ wartoœci podró¿y.
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
	 * Konstruktor przyjmuje wartoœci dystansu, przebiegu, zu¿ycia paliwa, roku, miesi¹ca, dnia.
	 * @param length dystans
	 * @param mileage przebieg
	 * @param fuelConsumption zu¿ycie paliwa
	 * @param year rok
	 * @param month miesi¹c
	 * @param day dzieñ
	 */
	public Travel(float length, float mileage, float fuelConsumption, int year, int month, int day) {
		this.length = length;
		this.mileage = mileage;
		this.avgFuelConsumption = fuelConsumption;
		this.startDate = LocalDateTime.of(year, month, day, 0, 0);
	}
	
	/**
	 * Konstruktor przyjmuje wartoœci dystansu, przebiegu, zu¿ycia paliwa, daty jako zmiennej String.
	 * @param length dystans
	 * @param mileage przebieg
	 * @param fuelConsumption zu¿ycie paliwa
	 * @param date data zapisana jako sekwencja znaków
	 */
	public Travel(float length, float mileage, float fuelConsumption, String date) {
		this.length = length;
		this.mileage = mileage;
		this.avgFuelConsumption = fuelConsumption;
		this.startDate = LocalDateTime.parse(date);
	}
	
	/**
	 * Konstruktor przyjmuje wartoœci dystansu, przebiegu, zu¿ycia paliwa, daty jako zmiennej LocalDateTime.
	 * @param length dystans
	 * @param mileage przebieg
	 * @param fuelConsumption zu¿ycie paliwa
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
	 * Zwraca dane podró¿y jako String.
	 * @return dane podró¿y
	 */
	public String toString() {
		return "Start time: " + startDate.format(timeFormat) + "\n" +
				"End time: " + endDate.format(timeFormat) + "\n" +
				"Distance: " + length + "\n" +
				"Total mileage: " + mileage + "\n" +
				"Avg. fuel consumption: " + avgFuelConsumption;
	}
	
	/**
	 * Ustawia nowy dystans dla podró¿y.
	 * @param newLength nowy dystans zapisany jako float
	 * @throws InvalidNumberException wyj¹tek rzucony gdy d³ugoœæ jest ujemna
	 */
	public void setLength(float newLength) throws InvalidNumberException {
		if(newLength < 0)
			throw new InvalidNumberException(String.valueOf(newLength) + " d³ugoœæ podró¿y nie mo¿e byæ ujemna");
		this.length = newLength;
	}
	
	/**
	 * Ustawia nowy przebieg dla podró¿y.
	 * @param newMileage nowy przebieg zapisany jako float
	 */
	public void setMileage(float newMileage) {
		this.mileage = newMileage;
	}
	
	/**
	 * Ustawia nowe œrednie zu¿ycie paliwa.
	 * @param newAvgFuelConsumption nowe zu¿ycie paliwa jako float
	 */
	public void setAvgFuelConsumption(float newAvgFuelConsumption) {
		this.avgFuelConsumption = newAvgFuelConsumption;
	}
	
	/**
	 * Ustawia now¹ datê pocz¹tku podró¿y i sprawdza jej poprawoœæ.
	 * @param year rok
	 * @param month miesi¹c
	 * @param day dzieñ
	 * @throws InvalidDateException wyj¹tek rzucony gdy data jest niepoprawna
	 */
	public void setStartDate(int year, int month, int day) throws InvalidDateException {
		LocalDateTime newDate = LocalDateTime.of(year, month, day, 0, 0);
		if(LocalDateTime.now().compareTo(newDate) < 0) {
			throw new InvalidDateException(newDate.toString() + " podana data jest z przysz³oœci");
		}
		this.startDate = newDate;
	}
	
	/**
	 * Ustawia now¹ datê pocz¹tku podró¿y i sprawdza jej poprawoœæ.
	 * @param date nowa data pocz¹tku podró¿y
	 * @throws InvalidDateException wyj¹tek rzucony gdy data jest niepoprawna
	 */
	public void setStartDate(String date) throws InvalidDateException {
		LocalDateTime newDate = LocalDateTime.parse(date);
		if(LocalDateTime.now().compareTo(newDate) < 0) {
			throw new InvalidDateException(newDate.toString() + " podana data jest z przysz³oœci");
		}
		this.startDate = newDate;
	}
	
	/**
	 * Zwraca dystans podró¿y.
	 * @return  dystans jako float
	 */
	public float getLength() {
		return this.length;
	}
	
	/**
	 * Zwraca przebieg podró¿y.
	 * @return przebieg jako float
	 */
	public float getMileage() {
		return this.mileage;
	}
	
	/**
	 * Zwraca œrednie zu¿ycie paliwa.
	 * @return œrednie zu¿ycie paliwa jako float
	 */
	public float getAvgFuelConsumption() {
		return this.avgFuelConsumption;
	}
	
	/**
	 * Zwraca datê pocz¹tkow¹ jako String.
	 * @return data pocz¹tkowa
	 */
	public String getStartDateString() {
		return this.startDate.format(timeFormat);
	}
	
	/**
	 * Zwraca datê koñcow¹ jako String.
	 * @return data koñcowa
	 */
	public String getEndDateString() {
		return this.endDate.format(timeFormat);
	}
	
	/**
	 * Zwraca datê pocz¹tkow¹.
	 * @return data pocz¹tkowa
	 */
	public LocalDateTime getStartDate() {
		return this.startDate;
	}

	/**
	 * Zwraca datê koñcow¹.
	 * @return data koñcowa
	 */
	public LocalDateTime getEndDate() {
		return endDate;
	}

	/**
	 * Ustawia datê zakoñczenia podró¿y.
	 * @param endDate nowa data zakoñczenia
	 */
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
}
