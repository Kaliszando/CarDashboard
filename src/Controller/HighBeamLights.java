package Controller;

/**
 * Klasa reprezentuj�ca �wiat�a drogowe.
 * 
 * Odpowiada za ustawienie stanu w��czone/wy��czone oraz przechowuje ich aktualny stan.
 * 
 * @author Adam Kamil
 * @author Kamil Rojszczak
 * @version 1.0
 */
public class HighBeamLights implements LightsInterface {

	private boolean isOn;
	
	/**
	 * Konstruktor, domy�lnie �wiat�a wy��czone.
	 */
	public HighBeamLights() {
		turnOff();
	}
	
	/**
	 * Zmiana wartoci isOn na true, w��czenie �wiate�.
	 */
	@Override
	public void turnOn() {
		this.isOn = true;
	}

	/**
	 * Zmiana wartoci isOn na false, wy��czenie �wiate�.
	 */
	@Override
	public void turnOff() {
		this.isOn = false;
	}

	/**
	 * Zapis aktualnego stanu �wiate� do zmiennej typu String.
	 * @return zwraca aktualny stan jako String
	 */
	@Override
	public String status() {
		String info = (this.isOn == true) ? "high beam lights are on" : "high beam lights are off";
		return info;
	}
	
	/**
	 * Stan �wiate� w��czone/wy��czone (true/false).
	 * @return zwraca aktualny stan �wiat�a jako true/false
	 */
	@Override
	public boolean isOn() {
		return isOn;
	}
	
}
