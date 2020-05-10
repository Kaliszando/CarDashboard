package Controller;

public class HighBeamLights implements LightsInterface {

	private boolean isOn;
	
	public HighBeamLights() {
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
		String info = (this.isOn == true) ? "high beam lights are on" : "high beam lights are off";
		return info;
	}
	
	@Override
	public boolean isOn() {
		return isOn;
	}
	
}
