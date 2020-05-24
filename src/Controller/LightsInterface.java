package Controller;

/**
 * Interfejs �wiate�.
 */
public interface LightsInterface {
	
	/**
	 * W��czenie �wiat�a/�wiate�
	 */
	abstract void turnOn();
	
	/**
	 * Wy��czenie �wiat�a/�wiate�
	 */
	abstract void turnOff();
	
	/**
	 * Zwr�cenie statusu �wiate� w zmiennej String
	 * @return status �wiate� zapisany jako String
	 */
	abstract String status();
	
	/**
	 * Zwraca status �wiate� w��czone/wy��czone
	 * @return status jako boolean
	 */
	abstract boolean isOn();
	
}
