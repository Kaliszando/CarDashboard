package Controller;

public class InvalidNumberException extends Exception {

	private static final long serialVersionUID = -5079022391455771671L;

	public InvalidNumberException() {
		super("Dana liczba jest niepoprawna");
	}
	
	public  InvalidNumberException(String msg) {
		super(msg);
	}
	
}
