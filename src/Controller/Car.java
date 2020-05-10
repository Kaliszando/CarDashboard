package Controller;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Car implements Cloneable, Serializable {
	
	// Fields
	private static final long serialVersionUID = -2060576986212609784L;	
	private float mileageTotal;	// przebieg całkowity
	private float mileage1;		// przebieg dzienny 1 i 2, można wyzerować
	private float mileage2;
	private float distance;		// długość podróży od startu
	private float avgFuelConsumption;
	private float maxSpeed;
	private float currentSpeed;
	private int rpms;
	private int rpmMax;
	private LocalDateTime startTime;
	private LocalDateTime stopTime;
	private LocalTime totalTime;
	private DateTimeFormatter timeFormat;
	private boolean isRunning;
	private CarLights lights;
	
	// Constructor
	public Car() {
		distance = 0;
		avgFuelConsumption = 0;
		maxSpeed = 0;
		currentSpeed = 0;
		rpms = 0;
		mileageTotal = 0;
		mileage1 = 0;
		mileage2 = 0;
		
		lights = new CarLights();
		timeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	}
	
	// Methods
	public void start() {
		setStartTime(LocalDateTime.now());
		setRunning(true);
	}
	
	public void stop() {
		setStopTime(LocalDateTime.now());
		setRunning(false);
	}
	
	public void calculatePeriodRunning() throws InvalidDateException {
		if(stopTime == null) {
			stopTime = LocalDateTime.now();
		}
		else if(startTime == null) {
			throw new InvalidDateException();
		}
		
		Duration duration = Duration.between(startTime, stopTime);
		long timeInSec = duration.getSeconds();
		totalTime = LocalTime.of((int)timeInSec/360, (int)timeInSec/60, (int)timeInSec);
		System.out.println(totalTime.toString());
	}

	// Getters, Setters
	public float getMileageTotal() {
		return mileageTotal;
	}

	public void increaseMileageTotal(float add) {
		this.mileageTotal += add;
	}

	public float getMileage1() {
		return mileage1;
	}

	public void increaseMileage1(float add) {
		this.mileage1 += add;
	}
	
	public void resetMileage1() {
		this.mileage1 = 0;
	}

	public float getMileage2() {
		return mileage2;
	}
	
	public void increaseMileage2(float add) {
		this.mileage2 += add;
	}

	public void resetMileage2(float mileage2) {
		this.mileage2 = 0;
	}

	public float getDistance() {
		return distance;
	}

	public void increaseDistance(float add) {
		this.distance += add;
	}

	public float getAvgFuelConsumption() {
		return avgFuelConsumption;
	}

	public void setAvgFuelConsumption(float avgFuelConsumption) {
		this.avgFuelConsumption = avgFuelConsumption;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public float getCurrentSpeed() {
		return currentSpeed;
	}

	public void setCurrentSpeed(float currentSpeed) {
		this.currentSpeed = currentSpeed;
	}

	public int getRpms() {
		return rpms;
	}

	public void setRpms(int rpms) {
		this.rpms = rpms;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getRpmMax() {
		return rpmMax;
	}

	public void setRpmMax(int rpmMax) {
		this.rpmMax = rpmMax;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public String getStartTime() {
		return startTime.format(timeFormat);
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public String getStopTime() {
		return stopTime.format(timeFormat);
	}

	public void setStopTime(LocalDateTime stopTime) {
		this.stopTime = stopTime;
	}

	public CarLights getLights() {
		return lights;
	}
}
