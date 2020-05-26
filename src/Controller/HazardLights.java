package Controller;

/**
 * Klasa reprezentuj�ca �wiat�a awaryjne.
 * 
 * Odpowiada za ustawienie stanu w��czone/wy��czone oraz przechowuje aktualny stan.
 * 
 * @author Adam Kamil
 * @author Kamil Rojszczak
 * @version 1.0
 */
public class HazardLights implements LightsInterface {

	private boolean areOn;
	private BlinkerLights L, R;
	
	/**
	 * Konstruktor, domy�lnie �wiat�o wy��czone.
	 * @param L Obiekt klasy BlinkerLights reprezentuj�cy lewy kierunkowskaz
	 * @param R Obiekt klasy BlinkerLights reprezentuj�cy prawy kierunkowskaz
	 */
	public HazardLights(BlinkerLights L, BlinkerLights R) {
		this.L = L;
		this.R = R;
		turnOff();
	}
	
	/**
	 * Zmiana wartoci areOn na true, w��czenie �wiate� awaryjnych.
	 */
	@Override
	public void turnOn() {
		this.areOn = true;
		L.turnOn();
		R.turnOn();
	}

	/**
	 * Zmiana wartoci areOn na fase, wy��czenie �wiate� awaryjnych.
	 */
	@Override
	public void turnOff() {
		this.areOn = false;
		L.turnOff();
		R.turnOff();
	}

	/**
	 * Zapis aktualnego stanu �wiate� do zmiennej typu String.
	 * @return zwraca aktualny stan jako String
	 */
	@Override
	public String status() {
		String info = (this.areOn == true) ? "hazard lights are on" : "hazard lights are off";
		return info;
	}
	
	/**
	 * Stan �wiate� w��czone/wy��czone (true/false).
	 * @return zwraca aktualny stan �wiat�a jako true/false
	 */
	@Override
	public boolean isOn() {
		return areOn;
	}
	
}
