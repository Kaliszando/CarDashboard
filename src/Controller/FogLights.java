package Controller;

/**
 * Klasa reprezentuj¹ca œwiat³o przeciwmgielne.
 * 
 * Odpowiada za ustawienie stanu w³¹czone/wy³¹czone, miejsca oraz przechowuje aktualny stan.
 * 
 * @author Adam Kamil
 * @author Kamil Rojszczak
 * @version 1.0
 */
public class FogLights implements LightsInterface {
	
	private boolean isOn;
	private String position;
	
	/**
	 * Konstruktor, domyœlnie œwiat³o wy³¹czone.
	 * @param position umiejscowienie œwiate³
	 */
	public FogLights(String position) {
		this.position = position;
		turnOff();
	}
	
	/**
	 * Zmiana wartoœci isOn na true, w³¹czenie œwiat³a.
	 */
	@Override
	public void turnOn() {
		this.isOn = true;
	}

	/**
	 * Zmiana wartoœci isOn na false, wy³¹czenie œwiat³a.
	 */
	@Override
	public void turnOff() {
		this.isOn = false;
	}
	
	/**
	 * Zapis aktualnego stanu œwiat³a do zmiennej typu String.
	 * @return zwraca aktualny stan jako String
	 */
	@Override
	public String status() {
		String info = (this.isOn) ? "fog lights are on" : "fog lights are off";
		return position + " " +  info;
	}
	
	/**
	 * Stan œwiat³a w³¹czone/wy³¹czone (true/false).
	 * @return zwraca aktualny stan œwiat³a jako true/false
	 */
	@Override
	public boolean isOn() {
		return isOn;
	}
	
}
