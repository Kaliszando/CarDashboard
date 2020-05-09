package Controller;
import java.awt.Color;

public class HeadLights implements LightsInterface {

	boolean isOn;
	Color color;
	
	public HeadLights() {
		this.color = new Color(255, 255, 195);
		this.isOn = false;
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
		String info = (this.isOn == true) ? "head lights are on" : "head lights are off";
		return "Lights status: " + info;
	}

}
