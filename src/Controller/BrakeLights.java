package Controller;

/**
 * Klasa reprezentuj¹ca œwiat³a hamowania.
 * 
 * Odpowiada za ustawienie stanu w³¹czony/wy³¹czony, kierunku oraz przechowuje jego aktualny stan.
 * 
 * @author Adam Kamil
 * @author Kamil Rojszczak
 * @version 1.0
 */
public class BrakeLights implements LightsInterface {

	private boolean isOn;
	
	/**
	 * Konstruktor, domyœlnie œwiat³o wy³¹czone.
	 */
	public BrakeLights() {
		turnOff();
	}
	
	/**
	 * Zmiana wartoœci isOn na true, w³¹czenie œwiate³.
	 */
	@Override
	public void turnOn() {
		this.isOn = true;
	}

	/**
	 * Zmiana wartoœci isOn na false, wy³¹czenie œwiate³.
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
		String info = (this.isOn == true) ? "brake lights are on" : "brake lights are off";
		return info;
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
