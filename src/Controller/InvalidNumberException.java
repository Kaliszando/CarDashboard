package Controller;

/**
 * Klasa wyj�tku niepoprawnej warto�ci numerycznej.
 * Rozszerzenie klasy Exception.
 */
public class InvalidNumberException extends Exception {

	private static final long serialVersionUID = -5079022391455771671L;

	/**
	 * Konstruktor wyj�tku z ustawion� wiadomo�ci�.
	 */
	public InvalidNumberException() {
		super("Dana liczba jest niepoprawna");
	}
	
	/**
	 * Konstruktor wyj�tku z wiadomo�ci� podan� jako parametr.
	 * @param msg wiadomo�� do rzucanego wyj�tku
	 */
	public  InvalidNumberException(String msg) {
		super(msg);
	}
	
}
