package Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Controller.InvalidDateException;
import Controller.InvalidNumberException;

public class Travel implements Cloneable, Serializable {

	private static final long serialVersionUID = 4725391663616879476L;
	private float length;
	private float mileage;
	private float avgFuelConsumption;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	// Constructors
	public Travel(float length, float mileage, float fuelConsumption, int year, int month, int day) {
		this.length = length;
		this.mileage = mileage;
		this.avgFuelConsumption = fuelConsumption;
		this.startDate = LocalDateTime.of(year, month, day, 0, 0);
	}
	
	public Travel(float length, float mileage, float fuelConsumption, String date) {
		this.length = length;
		this.mileage = mileage;
		this.avgFuelConsumption = fuelConsumption;
		this.startDate = LocalDateTime.parse(date);
	}
	
	public Travel(float length, float mileage, float fuelConsumption, LocalDateTime date) {
		this.length = length;
		this.mileage = mileage;
		this.avgFuelConsumption = fuelConsumption;
		this.startDate = date;
	}
	
	public Travel(Object obj) {
		String info = obj.toString();
		String parts[] = info.split(":", 4);
		this.length = Integer.valueOf(parts[0]);
		this.mileage = Integer.valueOf(parts[1]);
		this.avgFuelConsumption = Float.valueOf(parts[2]);
		this.startDate = LocalDateTime.parse(parts[3]);
	}
	//
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public String toString() {
		return "Start time: " + startDate.format(timeFormat) + "\n" +
				"End time: " + endDate.format(timeFormat) + "\n" +
				"Distance: " + length + "\n" +
				"Total mileage: " + mileage + "\n" +
				"Avg. fuel consumption: " + avgFuelConsumption;
	}
	
	public String toStreamString() {
		return length + ":" + mileage + ":" + avgFuelConsumption + ":" + startDate + ";";
	}
	
	// Setters
	public void setLength(float newLength) throws InvalidNumberException {
		if(newLength < 0)
			throw new InvalidNumberException(String.valueOf(newLength) + " długość podróży nie może być ujemna");
		this.length = newLength;
	}
	
	public void setMileage(float newMileage) {
		this.mileage = newMileage;
	}
	
	public void setAvgFuelConsumption(float newAvgFuelConsumption) {
		this.avgFuelConsumption = newAvgFuelConsumption;
	}
	
	public void setStartDate(int year, int month, int day) throws InvalidDateException {
		LocalDateTime newDate = LocalDateTime.of(year, month, day, 0, 0);
		if(LocalDateTime.now().compareTo(newDate) < 0) {
			throw new InvalidDateException(newDate.toString() + " podana data jest z przyszłości");
		}
		this.startDate = newDate;
	}
	
	public void setStartDate(String date) throws InvalidDateException {
		LocalDateTime newDate = LocalDateTime.parse(date);
		if(LocalDateTime.now().compareTo(newDate) < 0) {
			throw new InvalidDateException(newDate.toString() + " podana data jest z przyszłości");
		}
		this.startDate = newDate;
	}
	
	// Getters
	public float getLength() {
		return this.length;
	}
	
	public float getMileage() {
		return this.mileage;
	}
	
	public float getAvgFuelConsumption() {
		return this.avgFuelConsumption;
	}
	
	public String getStartDateString() {
		return this.startDate.format(timeFormat);
	}
	
	public String getEndDateString() {
		return this.endDate.format(timeFormat);
	}
	
	public LocalDateTime getStartDate() {
		return this.startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
}
