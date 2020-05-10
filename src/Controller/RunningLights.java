package Controller;

public class RunningLights implements LightsInterface {

	private boolean isOn;
	
	public RunningLights() {
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
		String info = (this.isOn == true) ? "running lights are on" : "running lights are off";
		return info;
	}

	@Override
	public boolean isOn() {
		return isOn;
	}
	
}
