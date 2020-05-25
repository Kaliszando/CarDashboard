package Controller;

import java.io.Serializable;

/**
 * Klasa zarz�dza klasami odpowiedzialnymi za �wiat�a samochodu.
 * 
 * Wykonuje operacje na takich �wiat�ach jak przeciwmgielne, kierunkowskazu, hamowania itd.
 * Pozwala w��cza� i wy��cza� �wiat�a oraz zwr�ci� ich aktualny stan.
 * 
 * @author Adam Kalisz
 * @author Kamil Rojszczak
 * @version 1.0
 * 
 */
public class CarLights implements Cloneable, Serializable {
	
	private static final long serialVersionUID = -4304452317729176974L;
	private LowBeamLights lowBeamLights;
	private HighBeamLights highBeamLights;
	private RunningLights runningLights;
	private BlinkerLights leftBlinker, rightBlinker;
	private HazardLights hazardLights;
	private FogLights frontFogLights, rearFogLights;
	private boolean lightsOn;
	
	/**
	 * Konstruktor zestawu �wiate�.
	 */
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
	
	/**
	 * Prze��cznik �wiate� mijania. 
	 */
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
	
	/**
	 * Prze��cznik �wiate� postojowych. 
	 */
	public void toggleRunningLights() {
		if(runningLights.isOn()) runningLights.turnOff();
		else {
			runningLights.turnOn();
			lowBeamLights.turnOff();
			highBeamLights.turnOff();
			frontFogLights.turnOff();
			rearFogLights.turnOff();
		}
		lightsOn = runningLights.isOn();
	}

	/**
	 * Prze��cznik �wiate� drogowych. 
	 */
	public void toggleHighBeamLights() {
		if(highBeamLights.isOn()) {
			highBeamLights.turnOff();
		}
		else if(lowBeamLights.isOn()) {
			highBeamLights.turnOn();
		}
	}
	
	/**
	 * Prze��cznik przednich �wiate� przeciwmgielnych.
	 */
	public void toggleFrontFogLigths() {
		if(frontFogLights.isOn()) {
			frontFogLights.turnOff();
		}
		else if(lowBeamLights.isOn()) {
			frontFogLights.turnOn();
		}
	}
	
	/**
	 * Prze��cznik tylnich �wiate� przeciwmgielnych.
	 */
	public void toggleRearFogLigths() {
		if(rearFogLights.isOn()) {
			rearFogLights.turnOff();
		}
		else if(lowBeamLights.isOn()) {
			rearFogLights.turnOn();
		}
	}
	
	/**
	 * Prze��cznik lewego kierunkowskazu.
	 */
	public void toggleLeftBlinker() {
		if(leftBlinker.isOn()) leftBlinker.turnOff();
		else leftBlinker.turnOn();
		rightBlinker.turnOff();
	}
	
	/**
	 * Prze��cznik prawego kierunkowskazu.
	 */
	public void toggleRightBlinker() {
		leftBlinker.turnOff();
		if(rightBlinker.isOn()) rightBlinker.turnOff();
		else rightBlinker.turnOn();
	}
	
	/**
	 * Prze��cznik �wiate� awaryjnych.
	 */
	public void toggleHazardLights() {
		if(hazardLights.isOn()) {
			hazardLights.turnOff();
		}
		else {
			hazardLights.turnOn();
		}
	}
	
	/**
	 * Zwraca stan �wiate�.
	 * @return warto�� true/false stanu �wiate�
	 */
	public boolean areLightsOn() {
		return lightsOn;
	}
	
	/**
	 * Zwraca stany �wiate�.
	 * @return zwraca stany �wiate� jako string
	 */
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
	
	/**
	 * Klonuje obiekt.
	 * @return sklonowany obiekt
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 * Zwraca obiekt reprezentuj�cy �wiat�a mijania.
	 * @return obiekt klasy LowBeamLights
	 */
	public LowBeamLights getLowBeamLights() {
		return lowBeamLights;
	}

	/**
	 * Zwraca obiekt reprezentuj�cy drogowe.
	 * @return obiekt klasy HighBeamLights
	 */
	public HighBeamLights getHighBeamLights() {
		return highBeamLights;
	}

	/**
	 * Zwraca obiekt reprezentuj�cy �wiat�a postojowe.
	 * @return obiekt klasy RunningLights
	 */
	public RunningLights getRunningLights() {
		return runningLights;
	}

	/**
	 * Zwraca obiekt reprezentuj�cy lewy kierunkowskaz.
	 * @return obiekt klasy BlinkerLights
	 */
	public BlinkerLights getLeftBlinker() {
		return leftBlinker;
	}

	/**
	 * Zwraca obiekt reprezentuj�cy prawy kierunkowskaz.
	 * @return obiekt klasy BlinkerLights
	 */
	public BlinkerLights getRightBlinker() {
		return rightBlinker;
	}

	/**
	 * Zwraca obiekt reprezentuj�cy �wiat�a awaryjne.
	 * @return obiekt klasy HazardLights
	 */
	public HazardLights getHazardLights() {
		return hazardLights;
	}

	/**
	 * Zwraca obiekt reprezentuj�cy �wiat�a przeciwmgielne przednie
	 * @return obiekt klasy FogLights
	 */
	public FogLights getFrontFogLights() {
		return frontFogLights;
	}

	/**
	 * Zwraca obiekt reprezentuj�cy �wiat�a przeciwmgielne tylnie
	 * @return obiekt klasy FogLights
	 */
	public FogLights getRearFogLights() {
		return rearFogLights;
	}

	/**
	 * Ustawia czy �wiat�a s� w��czone czy wy��czone
	 * @param lightsOn warto�� true/false
	 */
	public void setLights(boolean lightsOn) {
		this.lightsOn = lightsOn;
	}
	
}
