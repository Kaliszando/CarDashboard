package Controller;

/**
 * Klasa reprezentuj�ca �wiat�a postojowe.
 * 
 * Odpowiada za ustawienie stanu w��czone/wy��czone oraz przechowuje aktualny stan.
 * 
 * @author Adam Kamil
 * @author Kamil Rojszczak
 * @version 1.0
 */
public class RunningLights implements LightsInterface {

	private boolean isOn;
	
	/**
	 * Konstruktor, domy�lnie �wiat�o wy��czone.
	 */
	public RunningLights() {
		turnOff();
	}
	
	/**
	 * Zmiana wartoci areOn na true, w��czenie �wiate� awaryjnych.
	 */
	@Override
	public void turnOn() {
		this.isOn = true;
	}

	/**
	 * Zmiana wartoci areOn na true, w��czenie �wiate� awaryjnych.
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
		String info = (this.isOn == true) ? "running lights are on" : "running lights are off";
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
