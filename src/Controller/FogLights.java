package Controller;

/**
 * Klasa reprezentuj�ca �wiat�o przeciwmgielne.
 * 
 * Odpowiada za ustawienie stanu w��czone/wy��czone, miejsca oraz przechowuje aktualny stan.
 * 
 * @author Adam Kamil
 * @author Kamil Rojszczak
 * @version 1.0
 */
public class FogLights implements LightsInterface {
	
	private boolean isOn;
	private String position;
	
	/**
	 * Konstruktor, domy�lnie �wiat�o wy��czone.
	 * @param position umiejscowienie �wiate�
	 */
	public FogLights(String position) {
		this.position = position;
		turnOff();
	}
	
	/**
	 * Zmiana warto�ci isOn na true, w��czenie �wiat�a.
	 */
	@Override
	public void turnOn() {
		this.isOn = true;
	}

	/**
	 * Zmiana warto�ci isOn na false, wy��czenie �wiat�a.
	 */
	@Override
	public void turnOff() {
		this.isOn = false;
	}
	
	/**
	 * Zapis aktualnego stanu �wiat�a do zmiennej typu String.
	 * @return zwraca aktualny stan jako String
	 */
	@Override
	public String status() {
		String info = (this.isOn) ? "fog lights are on" : "fog lights are off";
		return position + " " +  info;
	}
	
	/**
	 * Stan �wiat�a w��czone/wy��czone (true/false).
	 * @return zwraca aktualny stan �wiat�a jako true/false
	 */
	@Override
	public boolean isOn() {
		return isOn;
	}
	
}
