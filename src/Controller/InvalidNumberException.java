package Controller;

/**
 * Klasa wyj¹tku niepoprawnej wartoœci numerycznej.
 * Rozszerzenie klasy Exception.
 */
public class InvalidNumberException extends Exception {

	private static final long serialVersionUID = -5079022391455771671L;

	/**
	 * Konstruktor wyj¹tku z ustawion¹ wiadomoœci¹.
	 */
	public InvalidNumberException() {
		super("Dana liczba jest niepoprawna");
	}
	
	/**
	 * Konstruktor wyj¹tku z wiadomoœci¹ podan¹ jako parametr.
	 * @param msg wiadomoœæ do rzucanego wyj¹tku
	 */
	public  InvalidNumberException(String msg) {
		super(msg);
	}
	
}
