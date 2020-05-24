package Controller;

/**
 * Klasa reprezentuj¹ca œwiat³a postojowe.
 * 
 * Odpowiada za ustawienie stanu w³¹czone/wy³¹czone oraz przechowuje aktualny stan.
 * 
 * @author Adam Kamil
 * @author Kamil Rojszczak
 * @version 1.0
 */
public class RunningLights implements LightsInterface {

	private boolean isOn;
	
	/**
	 * Konstruktor, domyœlnie œwiat³o wy³¹czone.
	 */
	public RunningLights() {
		turnOff();
	}
	
	/**
	 * Zmiana wartoci areOn na true, w³¹czenie œwiate³ awaryjnych.
	 */
	@Override
	public void turnOn() {
		this.isOn = true;
	}

	/**
	 * Zmiana wartoci areOn na true, w³¹czenie œwiate³ awaryjnych.
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
		String info = (this.isOn == true) ? "running lights are on" : "running lights are off";
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
