package Controller;

/**
 * Klasa reprezentuj¹ca œwiat³o kierunkowskazu.
 * 
 * Odpowiada za ustawienie stanu w³¹czony/wy³¹czony, kierunku oraz przechowuje jego aktualny stan.
 * 
 * @author Adam Kamil
 * @author Kamil Rojszczak
 * @version 1.0
 */
public class BlinkerLights implements LightsInterface {

	private String side;
	private boolean isBlinking;
	
	/**
	 * Konstruktor kierunkowskazu, ustawia wybrany kierunek oraz stan œwiat³a.
	 * @param side zmienna String oznaczaj¹ca stronê po której siê znajduje
	 */
	public BlinkerLights(String side) {
		this.isBlinking = false;
		this.side = side;
	}
	
	/**
	 * Zmiana wartoœci isBlinking na true, w³¹czenie œwiate³.
	 */
	@Override
	public void turnOn() {
		this.isBlinking = true;
	}

	/**
	 * Zmiana wartoœci isBlinking na false, wy³¹czenie œwiate³.
	 */
	@Override
	public void turnOff() {
		this.isBlinking = false;
	}

	/**
	 * Zapis aktualnego stanu œwiat³a do zmiennej typu String.
	 * @return zwraca aktualny stan jako String
	 */
	@Override
	public String status() {
		String status = (this.isBlinking == true) ? "blinker is on" : "blinker is off";
		return side + " " + status;
	}
	
	/**
	 * Stan œwiat³a w³¹czone/wy³¹czone (true/false).
	 * @return zwraca aktualny stan œwiat³a jako true/false
	 */
	@Override
	public boolean isOn() {
		return isBlinking;
	}

}
