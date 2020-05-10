package Controller;

public class HazardLights implements LightsInterface {

	private boolean areOn;
	private BlinkerLights L, R;
	
	public HazardLights(BlinkerLights L, BlinkerLights R) {
		this.L = L;
		this.R = R;
		turnOff();
	}
	
	@Override
	public void turnOn() {
		this.areOn = true;
	}

	@Override
	public void turnOff() {
		this.areOn = false;
	}

	@Override
	public String status() {
		String info = (this.areOn == true) ? "hazard lights are on" : "hazard lights are off";
		return info;
	}
	
	@Override
	public boolean isOn() {
		return areOn;
	}
	
}
