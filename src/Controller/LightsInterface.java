package Controller;

public interface LightsInterface {
	
	abstract void turnOn();
	
	abstract void turnOff();
	
	abstract String status();
	
	abstract boolean isOn();
	
}
