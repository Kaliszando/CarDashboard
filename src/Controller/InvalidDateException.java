package Controller;

/**
 * Klasa wyj�tku niepoprawnej daty.
 * Rozszerzenie klasy Exception.
 */
public class InvalidDateException extends Exception {

	private static final long serialVersionUID = 5357130290575686090L;

	/**
	 * Konstruktor wyj�tku z ustawion� wiadomo�ci�.
	 */
	public InvalidDateException() {
		super("Dana data jest niepoprawna");
	}
	
	/**
	 * Konstruktor wyj�tku z wiadomo�ci� podan� jako parametr.
	 * @param msg wiadomo�� do rzucanego wyj�tku
	 */
	public InvalidDateException(String msg) {
		super(msg);
	}
	
}
