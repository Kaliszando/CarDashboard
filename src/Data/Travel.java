package Data;
import java.io.Serializable;
import java.time.LocalDate;

import Controller.InvalidDateException;
import Controller.InvalidNumberException;

public class Travel implements Cloneable, Serializable {

	private static final long serialVersionUID = 4725391663616879476L;
	private int length;
	private int mileage;
	private float avgFuelConsumption;
	private LocalDate date;
	
	// Constructors
	public Travel(int length, int mileage, float fuelConsumption, int year, int month, int day) {
		this.length = length;
		this.mileage = mileage;
		this.avgFuelConsumption = fuelConsumption;
		this.date = LocalDate.of(year, month, day);
	}
	
	public Travel(int length, int mileage, float fuelConsumption, String date) {
		this.length = length;
		this.mileage = mileage;
		this.avgFuelConsumption = fuelConsumption;
		this.date = LocalDate.parse(date);
	}
	
	public Travel(Object obj) {
		String info = obj.toString();
		String parts[] = info.split(":", 4);
		this.length = Integer.valueOf(parts[0]);
		this.mileage = Integer.valueOf(parts[1]);
		this.avgFuelConsumption = Float.valueOf(parts[2]);
		this.date = LocalDate.parse(parts[3]);
	}
	//
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public String toString() {
		return date + " " + mileage + " " + length + " " + avgFuelConsumption;
	}
	
	public String toStreamString() {
		return length + ":" + mileage + ":" + avgFuelConsumption + ":" + date + ";";
	}
	
	// Setters
	public void setLength(int newLength) throws InvalidNumberException {
		if(newLength <= 0)
			throw new InvalidNumberException(String.valueOf(newLength) + " długość podróży nie może być ujemna");
		this.length = newLength;
	}
	
	public void setMileage(int newMileage) {
		this.length = newMileage;
	}
	
	public void setAvgFuelConsumption(float newAvgFuelConsumption) {
		this.avgFuelConsumption = newAvgFuelConsumption;
	}
	
	public void setDate(int year, int month, int day) throws InvalidDateException {
		LocalDate newDate = LocalDate.of(year, month, day);
		if(LocalDate.now().compareTo(newDate) < 0) {
			throw new InvalidDateException(newDate.toString() + " podana data jest z przyszłości");
		}
		this.date = newDate;
	}
	
	public void setDate(String date) throws InvalidDateException {
		LocalDate newDate = LocalDate.parse(date);
		if(LocalDate.now().compareTo(newDate) < 0) {
			throw new InvalidDateException(newDate.toString() + " podana data jest z przyszłości");
		}
		this.date = newDate;
	}
	
	// Getters
	public int getLength() {
		return this.length;
	}
	
	public int getMileage() {
		return this.mileage;
	}
	
	public float getAvgFuelConsumption() {
		return this.avgFuelConsumption;
	}
	
	public LocalDate getDate() {
		return this.date;
	}
}
