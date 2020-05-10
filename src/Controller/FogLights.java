package Controller;

public class FogLights implements LightsInterface {
	
	private boolean isOn;
	private String position;
	
	public FogLights(String position) {
		this.position = position;
		turnOff();
	}
	
	@Override
	public void turnOn() {
		this.isOn = true;
	}

	@Override
	public void turnOff() {
		this.isOn = false;
	}

	@Override
	public String status() {
		String info = (this.isOn) ? "fog lights are on" : "fog lights are off";
		return position + " " +  info;
	}
	
	@Override
	public boolean isOn() {
		return isOn;
	}
	
}
