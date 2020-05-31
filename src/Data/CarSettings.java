package Data;

import java.io.Serializable;

import Controller.Car;

/**
 * Przetrzymuje najwa�niejsze informacje klasy Car.
 * S�u�y do przechowywania informacji, by p�zniej przywr�ci� zapisany stan pojazdu.
 * 
 * @author Adam Kalisz
 *
 */
public class CarSettings implements Serializable {

	private static final long serialVersionUID = 596014525192799942L;
	private float mileageTotal;
	private float mileage1;
	private float mileage2;
	private float fuel;
	
	/**
	 * Konstruktor pobiera najwa�niejsze aktualne informacje obiektu klasy Car.
	 * @param car obiekt klasy Car kt�rego stan chcemy przechowa�
	 */
	public CarSettings(Car car) {
		this.mileageTotal = car.getMileageTotal();
		this.mileage1 = car.getMileage1();
		this.mileage2 = car.getMileage2();
		this.fuel = car.getFuel();
	}
	
	/**
	 * Konstruktor domy�lny.
	 */
	public CarSettings() {
		this.mileageTotal = 0;
		this.mileage1 = 0;
		this.mileage2 = 0;
		this.fuel = 0;
	}

	/**
	 * Zwraca zapisany przebieg ca�kowity.
	 * @return przebieg ca�kowity jako float
	 */
	public float getMileageTotal() {
		return mileageTotal;
	}

	/**
	 * Zwraca zapisany przebieg dzienny nr 1.
	 * @return przebieg dzienny jako float
	 */
	public float getMileage1() {
		return mileage1;
	}

	/**
	 * Zwraca zapisany przebieg dzienny nr 2.
	 * @return przebieg dzienny jako float
	 */
	public float getMileage2() {
		return mileage2;
	}

	/**
	 * Zwraca zapisan� ilo�� paliwa.
	 * @return ilo�� paliwa jako float
	 */
	public float getFuel() {
		return fuel;
	}

}
