package Controller;

import java.io.Serializable;

public class Car implements Cloneable, Serializable {
	
	private static final long serialVersionUID = -2060576986212609784L;
	
	private float mileageTotal;	// przebieg całkowity
	private float mileage1;		// przebieg dzienny 1 i 2, można wyzerować
	private float mileage2;
	private float distance;		// długość podróży od startu
	private float avgFuelConsumption;
	private float maxSpeed;
	private float currentSpeed;
	private float time;
	private int rpms;
	private int rpmMax;
	
	public Car() {
		// time start
		distance = 0;
		avgFuelConsumption = 0;
		maxSpeed = 0;
		currentSpeed = 0;
		rpms = 0;
		mileageTotal = 0;
		mileage1 = 0;
		mileage2 = 0;
	}

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

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
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
}
