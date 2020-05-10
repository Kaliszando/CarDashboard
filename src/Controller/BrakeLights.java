package Controller;
import java.awt.Color;

public class BrakeLights implements LightsInterface {

	boolean isOn;
	
	public BrakeLights() {
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
		String info = (this.isOn == true) ? "brake lights are on" : "brake lights are off";
		return info;
	}
	
	@Override
	public boolean isOn() {
		return isOn;
	}

}
