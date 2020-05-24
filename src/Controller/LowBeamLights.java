package Controller;

/**
 * Klasa reprezentuj¹ca œwiat³a mijania.
 * 
 * Odpowiada za ustawienie stanu w³¹czone/wy³¹czone oraz przechowuje ich aktualny stan.
 * 
 * @author Adam Kamil
 * @author Kamil Rojszczak
 * @version 1.0
 */
public class LowBeamLights implements LightsInterface {

	private boolean isOn;
	
	/**
	 * Konstruktor, domyœlnie œwiat³a wy³¹czone.
	 */
	public LowBeamLights() {
		turnOff();
	}
	
	/**
	 * Zmiana wartoci isOn na true, w³¹czenie œwiate³.
	 */
	@Override
	public void turnOn() {
		this.isOn = true;
	}

	/**
	 * Zmiana wartoci isOn na false, wy³¹czenie œwiate³.
	 */
	@Override
	public void turnOff() {
		this.isOn = false;
	}

	/**
	 * Zapis aktualnego stanu œwiate³ do zmiennej typu String.
	 * @return zwraca aktualny stan jako String
	 */
	@Override
	public String status() {
		String info = (this.isOn == true) ? "low beam lights are on" : "low beam lights are off";
		return info;
	}

	/**
	 * Stan œwiate³ w³¹czone/wy³¹czone (true/false).
	 * @return zwraca aktualny stan œwiat³a jako true/false
	 */
	@Override
	public boolean isOn() {
		return isOn;
	}

}
