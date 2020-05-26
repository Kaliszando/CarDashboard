package Controller;

/**
 * Klasa reprezentuj�ca �wiat�a hamowania.
 * 
 * Odpowiada za ustawienie stanu w��czony/wy��czony, kierunku oraz przechowuje jego aktualny stan.
 * 
 * @author Adam Kamil
 * @author Kamil Rojszczak
 * @version 1.0
 */
public class BrakeLights implements LightsInterface {

	private boolean isOn;
	
	/**
	 * Konstruktor, domy�lnie �wiat�o wy��czone.
	 */
	public BrakeLights() {
		turnOff();
	}
	
	/**
	 * Zmiana warto�ci isOn na true, w��czenie �wiate�.
	 */
	@Override
	public void turnOn() {
		this.isOn = true;
	}

	/**
	 * Zmiana warto�ci isOn na false, wy��czenie �wiate�.
	 */
	@Override
	public void turnOff() {
		this.isOn = false;
	}

	/**
	 * Zapis aktualnego stanu �wiat�a do zmiennej typu String.
	 * @return zwraca aktualny stan jako String
	 */
	@Override
	public String status() {
		String info = (this.isOn == true) ? "brake lights are on" : "brake lights are off";
		return info;
	}
	
	/**
	 * Stan �wiat�a w��czone/wy��czone (true/false).
	 * @return zwraca aktualny stan �wiat�a jako true/false
	 */
	@Override
	public boolean isOn() {
		return isOn;
	}

}
