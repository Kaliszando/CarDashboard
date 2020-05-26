package Controller;

/**
 * Klasa wyj¹tku niepoprawnej daty.
 * Rozszerzenie klasy Exception.
 */
public class InvalidDateException extends Exception {

	private static final long serialVersionUID = 5357130290575686090L;

	/**
	 * Konstruktor wyj¹tku z ustawion¹ wiadomoœci¹.
	 */
	public InvalidDateException() {
		super("Dana data jest niepoprawna");
	}
	
	/**
	 * Konstruktor wyj¹tku z wiadomoœci¹ podan¹ jako parametr.
	 * @param msg wiadomoœæ do rzucanego wyj¹tku
	 */
	public InvalidDateException(String msg) {
		super(msg);
	}
	
}
