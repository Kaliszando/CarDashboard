package Controller;

public class InvalidDateException extends Exception {

	private static final long serialVersionUID = 5357130290575686090L;

	public InvalidDateException() {
		super("Dana data jest niepoprawna");
	}
	
	public InvalidDateException(String msg) {
		super(msg);
	}
	
}
