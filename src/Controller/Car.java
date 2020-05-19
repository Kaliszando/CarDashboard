package Controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Data.Database;
import Data.Travel;

/**
 * Klasa reprezentująca samochód i komputer pokładowy.
 * 
 * Wykonuje operacje na takich wartościach jak przebieg całkowity, dystans, średnie spalanie, prędkość, itd.
 * Pozwala operować silnikiem, zmianą biegów, włączaniem wyłączaniem świateł.
 * Rejestruje podróże, która zaczynają się po wywołaniu metody {@link #start()}, a kończą {@link #stop()}
 * 
 * @version 1.0
 * @author Adam
 * 
 */

public class Car implements Cloneable, Serializable {
	
	// Fields
	private static final long serialVersionUID = -2060576986212609784L;	
	private float mileageTotal;	// przebieg caďż˝kowity
	private float mileage1;		// przebieg dzienny 1 i 2, moďż˝na wyzerowaďż˝
	private float mileage2;
	private float distance;		// dďż˝ugoďż˝ďż˝ podrďż˝y od startu
	private float avgFuelConsumption;
	private float maxSpeed;
	private float fixedSpeed;
	private float currentSpeed;
	private float avgSpeed;
	private float rpms;
	private int rpmMax;
	private LocalDateTime startTime;
	private LocalDateTime stopTime;
	private LocalTime totalTime;
	private DateTimeFormatter timeFormat;
	private boolean isRunning;
	private CarLights lights;
	private float[] gearRatios = { 0.f, 0.0056f, 0.011f, 0.017f, 0.0232f, 0.029f, 0.036f };
	private short gear; // 0 neutral, 1-6 normal
	private float oilTemp, fuel;
	private ArrayList<Travel> travels;
	private Travel currentTravel;
	public Database db;
	private long timeInSec;

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
		gear = 0;
		fixedSpeed = 0;
		rpmMax = 7000;
		oilTemp = 73;
		fuel = 13;
		lights = new CarLights();
		timeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		travels = new ArrayList<>();
		
		try {
			db = new Database();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Methods
	public void start() {
		avgSpeed = 0;
		setStartTime(LocalDateTime.now());
		setRunning(true);
		currentTravel = new Travel(0, mileageTotal, 0, LocalDateTime.now());
	}
	
	public void stop() {
		if(getCurrentSpeed() >= 0) {
			fixedSpeed = 0;
			setRunning(false);	
			setStopTime(LocalDateTime.now());
			
			try {
				currentTravel.setLength(distance);
			} catch (InvalidNumberException e) {
				e.printStackTrace();
			}
			currentTravel.setMileage(mileageTotal);
			currentTravel.setAvgFuelConsumption(avgFuelConsumption);
			currentTravel.setEndDate(LocalDateTime.now());	
			
			System.out.println(currentTravel.toString());
			travels.add(currentTravel);
			
			try {
				db.addTravel(currentTravel);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return;
		}
	}
	
	public void gearUp() {
		if(gear < 6) {
			gear++;
			rpms /= 2;
		}
	}
	
	public void gearDown() {
		if(gear > 0) {
			gear--;
			rpms += 600;
		}
	}
	
	public void update() {
		//avgSpeed += currentSpeed;
		//System.out.println(avgSpeed / timeInSec + " " + timeInSec);		
	}
	
	
	/**
	 * Wykonywana cyklicznie, zwięszka lub zminiejsza obroty silnika
	 * w zależności od ustawionej prędkościci, podobnie jak w tempomacie. 
	 * 
	 * @param desiredSpeed docelowa prędkość którą chcemy osiągnąć
	 */
	public void accelerate(float desiredSpeed) {
		this.currentSpeed = this.getCurrentSpeed();
		if(currentSpeed >= desiredSpeed) {
			rpms--;
			return;
		}
		if(rpms >= rpmMax) {
			rpms = rpmMax - 150;
		}
		float diff = desiredSpeed > currentSpeed ? 0.7f : -0.8f;
		rpms += diff;
	}
	
	public void calculatePeriodRunning() throws InvalidDateException {
		if(startTime == null) {
			return;
		}
		if(isRunning)stopTime = LocalDateTime.now();
		
		Duration duration = Duration.between(startTime, stopTime);
		timeInSec = duration.getSeconds();
		totalTime = LocalTime.of((int)timeInSec / 360 % 24, (int)timeInSec / 60 % 60, (int)timeInSec % 60);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public String gearToString() {
		return gear == 0 ? "N" : String.valueOf(this.gear);
	}

	public void setFuel(float fuel) {
		this.fuel = fuel;
	}
	
	public float getMileageTotal() {
		return mileageTotal;
	}

	public String getTotalTime() {
		return totalTime.toString();
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
		return rpms * gearRatios[gear];
	}

	public void setCurrentSpeed(float currentSpeed) {
		this.currentSpeed = currentSpeed;
	}

	public float getRpms() {
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

	public float getFixedSpeed() {
		return fixedSpeed;
	}

	public void setFixedSpeed(float fixedSpeed) {
		this.fixedSpeed = fixedSpeed;
	}

	public float getOilTemp() {
		return oilTemp;
	}

	public void setOilTemp(float oilTemp) {
		this.oilTemp = oilTemp;
	}

	public float getFuel() {
		return fuel;
	}

	public ArrayList<Travel> getTravels() {
		return travels;
	}
	
	public long getTimeInSec() {
		return timeInSec;
	}
	
}
