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
	
	public void toggleLowBeamLights() {
		if(lowBeamLights.isOn()) {
			lowBeamLights.turnOff();
			highBeamLights.turnOff();
			
			frontFogLights.turnOff();
			rearFogLights.turnOff();
		}
		else {
			lowBeamLights.turnOn();
			runningLights.turnOff();
		}
		lightsOn = lowBeamLights.isOn();
	}
	
	public void toggleRunningLights() {
		if(runningLights.isOn()) runningLights.turnOff();
		else {
			runningLights.turnOn();
			lowBeamLights.turnOff();
		}
		lightsOn = runningLights.isOn();
	}

	public void toggleHighBeamLights() {
		if(highBeamLights.isOn()) {
			highBeamLights.turnOff();
		}
		else if(lowBeamLights.isOn()) {
			highBeamLights.turnOn();
		}
	}
	
	public void toggleFrontFogLigths() {
		if(frontFogLights.isOn()) {
			frontFogLights.turnOff();
		}
		else if(lowBeamLights.isOn()) {
			frontFogLights.turnOn();
		}
	}
	
	public void toggleRearFogLigths() {
		if(rearFogLights.isOn()) {
			rearFogLights.turnOff();
		}
		else if(lowBeamLights.isOn()) {
			rearFogLights.turnOn();
		}
	}
	
	public void toggleLeftBlinker() {
		rightBlinker.turnOff();
		if(leftBlinker.isOn()) leftBlinker.turnOff();
		else leftBlinker.turnOn();
	}
	
	public void toggleRightBlinker() {
		leftBlinker.turnOff();
		if(rightBlinker.isOn()) rightBlinker.turnOff();
		else rightBlinker.turnOn();
	}
	
	public void toggleHazardLights() {
		if(hazardLights.isOn()) {
			hazardLights.turnOff();
		}
		else {
			hazardLights.turnOn();
		}
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
		info += leftBlinker.status() + "\n";
		info += rightBlinker.status() + "\n";
		info += hazardLights.status() + "\n";
		info += frontFogLights.status() + "\n";
		info += rearFogLights.status() + "\n";
		return info;
	}
	
}
