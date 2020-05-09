package Controller;
import java.awt.Color;

public class Blinker implements LightsInterface {

	String side;
	boolean isBlinking;
	Color color;
	
	public Blinker(String side) {
		this.color = new Color(204, 102, 0);
		this.isBlinking = false;
		this.side = side;
	}
	
	@Override
	public void turnOn() {
		this.isBlinking = true;
	}

	@Override
	public void turnOff() {
		this.isBlinking = false;
	}

	@Override
	public String status() {
		String status = (this.isBlinking == true) ? "blinker is on" : "blinker is off";
		return "Lights status: " + side + " " + status;
	}

}
