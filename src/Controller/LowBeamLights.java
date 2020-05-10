package Controller;

public class LowBeamLights implements LightsInterface {

	private boolean isOn;
	
	public LowBeamLights() {
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
		String info = (this.isOn == true) ? "low beam lights are on" : "low beam lights are off";
		return info;
	}

	@Override
	public boolean isOn() {
		return isOn;
	}

}
