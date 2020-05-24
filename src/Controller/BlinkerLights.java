package Controller;

/**
 * Klasa reprezentuj�ca �wiat�o kierunkowskazu.
 * 
 * Odpowiada za ustawienie stanu w��czony/wy��czony, kierunku oraz przechowuje jego aktualny stan.
 * 
 * @author Adam Kamil
 * @author Kamil Rojszczak
 * @version 1.0
 */
public class BlinkerLights implements LightsInterface {

	private String side;
	private boolean isBlinking;
	
	/**
	 * Konstruktor kierunkowskazu, ustawia wybrany kierunek oraz stan �wiat�a.
	 * @param side zmienna String oznaczaj�ca stron� po kt�rej si� znajduje
	 */
	public BlinkerLights(String side) {
		this.isBlinking = false;
		this.side = side;
	}
	
	/**
	 * Zmiana warto�ci isBlinking na true, w��czenie �wiate�.
	 */
	@Override
	public void turnOn() {
		this.isBlinking = true;
	}

	/**
	 * Zmiana warto�ci isBlinking na false, wy��czenie �wiate�.
	 */
	@Override
	public void turnOff() {
		this.isBlinking = false;
	}

	/**
	 * Zapis aktualnego stanu �wiat�a do zmiennej typu String.
	 * @return zwraca aktualny stan jako String
	 */
	@Override
	public String status() {
		String status = (this.isBlinking == true) ? "blinker is on" : "blinker is off";
		return side + " " + status;
	}
	
	/**
	 * Stan �wiat�a w��czone/wy��czone (true/false).
	 * @return zwraca aktualny stan �wiat�a jako true/false
	 */
	@Override
	public boolean isOn() {
		return isBlinking;
	}

}
