package Controller;

public class BlinkerLights implements LightsInterface {

	private String side;
	private boolean isBlinking;
	
	public BlinkerLights(String side) {
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
		return side + " " + status;
	}
	
	@Override
	public boolean isOn() {
		return isBlinking;
	}

}
