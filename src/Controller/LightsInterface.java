package Controller;

/**
 * Interfejs œwiate³.
 */
public interface LightsInterface {
	
	/**
	 * W³¹czenie œwiat³a/œwiate³
	 */
	abstract void turnOn();
	
	/**
	 * Wy³¹czenie œwiat³a/œwiate³
	 */
	abstract void turnOff();
	
	/**
	 * Zwrócenie statusu œwiate³ w zmiennej String
	 * @return status œwiate³ zapisany jako String
	 */
	abstract String status();
	
	/**
	 * Zwraca status œwiate³ w³¹czone/wy³¹czone
	 * @return status jako boolean
	 */
	abstract boolean isOn();
	
}
