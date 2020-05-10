package Controller;

public class CarLights {
	
	private LowBeamLights lowBeamLights;
	private HighBeamLights highBeamLights;
	private RunningLights runningLights;
	private BlinkerLights leftBlinker, rightBlinker;
	private HazardLights hazardLights;
	private FogLights frontFogLights, rearFogLights;
	private boolean lightsOn;
	
	public CarLights() {
		lowBeamLights = new LowBeamLights();
		highBeamLights = new HighBeamLights();
		runningLights = new RunningLights();
		leftBlinker = new BlinkerLights("left");
		rightBlinker = new BlinkerLights("right");
		hazardLights = new HazardLights(leftBlinker, rightBlinker);
		frontFogLights = new FogLights("front");
		rearFogLights = new FogLights("rear");
		lightsOn = false;
	}
	
	void toggleLowBeamLights() {
		if(lowBeamLights.isOn()) lowBeamLights.turnOff();
		else {
			lowBeamLights.turnOn();
			runningLights.turnOff();
		}
		lightsOn = lowBeamLights.isOn();
	}
	
	void toggleRunningLights() {
		if(runningLights.isOn()) runningLights.turnOff();
		else {
			runningLights.turnOn();
			lowBeamLights.turnOff();
		}
		lightsOn = runningLights.isOn();
	}

	public boolean areLightsOn() {
		return lightsOn;
	}
	
	public String toString() {
		String info = new String();
		info = "lights are on: " + lightsOn + "\n";
		info += runningLights.status() + "\n";
		info += lowBeamLights.status() + "\n";
		info += highBeamLights.status() + "\n";
		return info;
	}
	
}
