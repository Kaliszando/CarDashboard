package Controller;
import java.awt.Color;

public class TailLights implements LightsInterface {

	boolean isOn;
	Color color;
	
	public TailLights() {
		this.color = new Color(204, 0, 0);
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
		String info = (this.isOn == true) ? "brake lights are on" : "brake lights are off";
		return "Lights status: " + info;
	}

}
