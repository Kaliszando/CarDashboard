package Controller;

/**
 * Klasa reprezentuj¹ca œwiat³a awaryjne.
 * 
 * Odpowiada za ustawienie stanu w³¹czone/wy³¹czone oraz przechowuje aktualny stan.
 * 
 * @author Adam Kamil
 * @author Kamil Rojszczak
 * @version 1.0
 */
public class HazardLights implements LightsInterface {

	private boolean areOn;
	private BlinkerLights L, R;
	
	/**
	 * Konstruktor, domyœlnie œwiat³o wy³¹czone.
	 * @param L Obiekt klasy BlinkerLights reprezentuj¹cy lewy kierunkowskaz
	 * @param R Obiekt klasy BlinkerLights reprezentuj¹cy prawy kierunkowskaz
	 */
	public HazardLights(BlinkerLights L, BlinkerLights R) {
		this.L = L;
		this.R = R;
		turnOff();
	}
	
	/**
	 * Zmiana wartoci areOn na true, w³¹czenie œwiate³ awaryjnych.
	 */
	@Override
	public void turnOn() {
		this.areOn = true;
		L.turnOn();
		R.turnOn();
	}

	/**
	 * Zmiana wartoci areOn na fase, wy³¹czenie œwiate³ awaryjnych.
	 */
	@Override
	public void turnOff() {
		this.areOn = false;
		L.turnOff();
		R.turnOff();
	}

	/**
	 * Zapis aktualnego stanu œwiate³ do zmiennej typu String.
	 * @return zwraca aktualny stan jako String
	 */
	@Override
	public String status() {
		String info = (this.areOn == true) ? "hazard lights are on" : "hazard lights are off";
		return info;
	}
	
	/**
	 * Stan œwiate³ w³¹czone/wy³¹czone (true/false).
	 * @return zwraca aktualny stan œwiat³a jako true/false
	 */
	@Override
	public boolean isOn() {
		return areOn;
	}
	
}
