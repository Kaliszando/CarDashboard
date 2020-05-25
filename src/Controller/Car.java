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
 * Pozwala operować silnikiem, zmianą biegów, włączaniem i wyłączaniem świateł.
 * Rejestruje podróże, które zaczynają się po wywołaniu metody {@link #start()}, a kończą {@link #stop()}
 * 
 * @version 1.1
 * @author Adam Kalisz
 * @author Kamil Rojszczak
 * 
 */

public class Car implements Cloneable, Serializable {
	
	private static final long serialVersionUID = -2060576986212609784L;	
	private float mileageTotal;
	private float mileage1;
	private float mileage2;
	private float distance;
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
	private float waterTemp, fuel;
	private ArrayList<Travel> travels;
	private Travel currentTravel;
	private Database db;
	private long timeInSec;

	/**
	 * Konstruktor klasy Car. Ustawia domyślne wartości oraz tworzy obiekt klasy Database do którego będą później zapisywane
	 * dane z podróży.
	 */
	public Car() {
		distance = 0;
		avgFuelConsumption = 0;
		maxSpeed = 0;
		currentSpeed = 0;
		rpms = 0;
		mileageTotal = 224564.1f; 
		mileage1 = 123.33f;
		mileage2 = 0;
		gear = 0;
		fixedSpeed = 0;
		rpmMax = 7000;
		waterTemp = 0;
		fuel = 13;
		lights = new CarLights();
		timeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		travels = new ArrayList<>();
		
		try {
			db = new Database();
			System.out.println("Database connected successfuly");
		} catch (SQLException e) {
			System.out.println("Connecting to database failed");
		}
	}
	
	/**
	 * Ustawia datę i czas uruchomienia silnika, uruchamia silnik poprzez ustawienie flagi isRunning.
	 * Tworzy obiekt currentTravel klasy Travel który przechowuje dane o aktualnej podróży.
	 */
	public void start() {
		avgSpeed = 0;
		maxSpeed = 0;
		distance = 0;
		setStartTime(LocalDateTime.now());
		setRunning(true);
		currentTravel = new Travel(0, mileageTotal, 0, LocalDateTime.now());
	}
	
	/**
	 * Wyłącza silnik, zapisuje przebyty dystans, średnie zużycie paliwa
	 * oraz datę i czas zatrzymania silnika do obiektu currentTravel. 
	 * Dodaje ostatnią podróż do bazy danych.
	 */
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
	
	/**
	 * Zmienia bieg na wyższy.
	 * Sprawdza czy możliwa jest zmiana biegu na wyższy, jeśli tak to go zmienia oraz odpowiednio zmniejsza obroty silnika.
	 */
	public void gearUp() {
		if(gear < 6) {
			gear++;
			rpms *= 0.5;
		}
	}
	
	/**
	 * Zmienia bieg na niższy.
	 * Sprawdza czy możliwa jest zmiana biegu na niższy, jeśli tak to go zmienia oraz odpowiednio zwiększa obroty silnika.
	 */
	public void gearDown() {
		if(gear > 0) {
			gear--;
			if(isRunning) rpms += 600;
		}
	}
	
	/**
	 * Aktualizuje wartości komputera pokładowego
	 */
	public void update() {
		if(currentSpeed > maxSpeed && isRunning) maxSpeed = currentSpeed;
		// 1 sec distance
		double diff = currentSpeed / 3.6 / 1000;
		distance += diff;
		mileage1 += diff;
		mileage2 += diff;
		mileageTotal += diff;
		if(isRunning) avgSpeed = distance * 1000 / timeInSec * 3.6f;
		if(waterTemp < 90) waterTemp++;
	}
	
	/**
	 * Zwraca prędkość średnią od początku podróży
	 * @return prędkość średnia
	 */
	public float getAvgSpeed() {
		return avgSpeed;
	}

	
//	public void setTimeInSec(long timeInSec) {
//		this.timeInSec = timeInSec;
//	}

	/**
	 * Wykonywana cyklicznie, zwięszka lub zminiejsza obroty silnika
	 * w zależności od ustawionej prędkości, podobnie jak w tempomacie.
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
		float diff = desiredSpeed > currentSpeed ? 0.9f : -1.9f;
		rpms += diff;
	}
	
	/**
	 * Oblicza czas pomiędzy uruchomieniem i zatrzymaniem silnika (aktualnym czasem jeśli silnik jest uruchomiony).
	 * Zapisuje czas trwania do zmiennej totalTime w formacie godziny:minuty:sekundy.
	 * @throws InvalidDateException wyjątek niepoprawnej daty
	 */
	public void calculatePeriodRunning() throws InvalidDateException {
		if(startTime == null) return;
		if(isRunning)stopTime = LocalDateTime.now();
		Duration duration = Duration.between(startTime, stopTime);
		if(duration.getSeconds() < 0) throw new InvalidDateException();
		timeInSec = duration.getSeconds();
		totalTime = LocalTime.of((int)timeInSec / 360 % 24, (int)timeInSec / 60 % 60, (int)timeInSec % 60);
	}

	/**
	 * Klonuje obiekt.
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	/**
	 * Zwraca aktualny biegu samochodu.
	 * @return wartość atualnego biegu samochodu jako String
	 */
	public String gearToString() {
		return gear == 0 ? "N" : String.valueOf(this.gear);
	}

	/**
	 * Ustawia ilość paliwa.
	 * @param fuel ilość paliwa jako float
	 */
	public void setFuel(float fuel) {
		this.fuel = fuel;
	}
	
	/**
	 * Zwraca przebieg całkowity.
	 * @return zwraca ilość przebytych kilometrów jako float
	 */
	public float getMileageTotal() {
		return mileageTotal;
	}

	/**
	 * Zwraca wartość czasu jaki został uprzednio obliczony poprzez {@link #calculatePeriodRunning()}.
	 * @return zwraca wartość czasu jaki upłynął jako string w formacie: godziny:minuty:sekundy.
	 */
	public String getTotalTime() {
		return totalTime.toString();
	}
	
	/**
	 * Dodaje do przebiegu podaną wartość.
	 * @param add zmienna typu float która zostanie dodana do przebiegu całkowitego
	 */
	public void increaseMileageTotal(float add) {
		this.mileageTotal += add;
	}

	/**
	 * Zwraca wartość pierwszego licznika dziennego.
	 * @return zwraca ilość przebytych kilometrów jako float
	 */
	public float getMileage1() {
		return mileage1;
	}

	/**
	 * Dodaje do pierwszego licznika dziennego podaną wartość.
	 * @param add zmienna typu float która zostanie dodana
	 */
	public void increaseMileage1(float add) {
		this.mileage1 += add;
	}
	
	/**
	 * Resetuje wartość pierwszego licznika dziennego.
	 */
	public void resetMileage1() {
		this.mileage1 = 0;
	}

	/**
	 * Zwraca wartość drugiego licznika dziennego.
	 * @return zwraca ilość przebytych kilometrów jako float
	 */
	public float getMileage2() {
		return mileage2;
	}
	
	/**
	 * Dodaje do drugiego licznika dziennego podaną wartość.
	 * @param add zmienna typu float która zostanie dodana
	 */
	public void increaseMileage2(float add) {
		this.mileage2 += add;
	}

	/**
	 * Resetuje wartość drugiego licznika dziennego.
	 */
	public void resetMileage2() {
		this.mileage2 = 0;
	}

	/**
	 * Zwraca przebyty dystans od uruchomienia silnika.
	 * @return zwraca długość przebytego dystansu jako float
	 */
	public float getDistance() {
		return distance;
	}

	/**
	 * Dodaje do odległości podróży podaną zmienną.
	 * @param add zmienna typu float która zostanie dodana do dystansu aktualnej podróży
	 */
	public void increaseDistance(float add) {
		this.distance += add;
	}

	/**
	 * Zwraca średnie zużycie paliwa.
	 * @return zwraca średnie zużycie paliwa jako float
	 */
	public float getAvgFuelConsumption() {
		return avgFuelConsumption;
	}

	/**
	 * Ustawia średnie zużycie paliwa.
	 * @param avgFuelConsumption średnie zużycie paliwa jako float
	 */
	public void setAvgFuelConsumption(float avgFuelConsumption) {
		this.avgFuelConsumption = avgFuelConsumption;
	}

	/**
	 * Zwraca maksymalną prędkość.
	 * @return zwraca maksymalną prędkość jako float
	 */
	public float getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * Ustawia maksynalną prędkość.
	 * @param maxSpeed maksymalną prędkość jako float
	 */
	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	/**
	 * Oblicza aktualną prędkość.
	 * Iloczyn obrotów silnika i wartości odpowiadającej biegu samochodu.
	 * @return zwraca aktualną prędkość jako float
	 */
	public float getCurrentSpeed() {
		return rpms * gearRatios[gear];
	}

	/**
	 * Ustawia aktualną prędkość.
	 * @param currentSpeed aktualna prędkość
	 */
	public void setCurrentSpeed(float currentSpeed) {
		this.currentSpeed = currentSpeed;
	}

	/**
	 * Zwraca obroty silnika.
	 * @return  obroty silnika jako float
	 */
	public float getRpms() {
		return rpms;
	}

	/**
	 * Ustawia obroty silnika.
	 * @param rpms obroty silnika jako int
	 */
	public void setRpms(int rpms) {
		this.rpms = rpms;
	}

	/**
	 * Zwraca maksymalne obroty silnika.
	 * @return maksymalne obroty silnika jako float
	 */
	public int getRpmMax() {
		return rpmMax;
	}

	/**
	 * Ustawia maksymalne obroty silnika.
	 * @param rpmMax obroty silnika jako int
	 */
	public void setRpmMax(int rpmMax) {
		this.rpmMax = rpmMax;
	}

	/**
	 * Stan silnika (włączony/wyłączony).
	 * @return Zwraca aktualny stan silnika jako true/false.
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * Ustawia stan silnika.
	 * @param isRunning stan silnika jako true/false
	 */
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	/**
	 * Zwraca czas uruchomienia silnika.
	 * @return czas początkowy jako String
	 */
	public String getStartTime() {
		return startTime.format(timeFormat);
	}

	/**
	 * Ustawia czas startu silnika.
	 * @param startTime czas początkowy
	 */
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * Zwraca czas zatrzymania silnika.
	 * @return czas zatrzymania jako String
	 */
	public String getStopTime() {
		return stopTime.format(timeFormat);
	}

	/**
	 * Ustawia czas zatrzymania silnika.
	 * @param stopTime czas zatrzymania
	 */
	public void setStopTime(LocalDateTime stopTime) {
		this.stopTime = stopTime;
	}

	/**
	 * Zwraca obiekt lights.
	 * @return obiekt lights klasy CarLights
	 */
	public CarLights getLights() {
		return lights;
	}

	/**
	 * Zwraca prędkość ustawioną na tempomacie.
	 * @return prędkość ustawiona na tempomacie jako float
	 */
	public float getFixedSpeed() {
		return fixedSpeed;
	}

	/**
	 * Ustawia prędkość na tempomacie.
	 * @param fixedSpeed prędkość na tempomacie jako float
	 */
	public void setFixedSpeed(float fixedSpeed) {
		this.fixedSpeed = fixedSpeed;
	}

	/**
	 * Zwraca temperaturę płynu chłodniczego.
	 * @return temperatura płynu chłodniczego jako float
	 */
	public float getWaterTemp() {
		return waterTemp;
	}

	/**
	 * Ustawia temperaturę płynu chłodniczego.
	 * @param waterTemp temperatura płynu chłodniczego jako float
	 */
	public void setWaterTemp(float waterTemp) {
		this.waterTemp = waterTemp;
	}

	/**
	 * Zwraca ilość paliwa.
	 * @return ilość paliwa jako float
	 */
	public float getFuel() {
		return fuel;
	}

	/**
	 * Zwraca listę zapisanych podróży.
	 * @return zapisane podróże
	 */
	public ArrayList<Travel> getTravels() {
		return travels;
	}
	
	/**
	 * Zwraca czas jaki upłynął od uruchomienia silnika.
	 * @return timeInSec czas w sekundach
	 */
	public long getTimeInSec() {
		return timeInSec;
	}
	
	/**
	 * Zwraca obiekt klasy Databse, reprezentujący bazę danych.
	 * @return obiekt klasy Database
	 */
	public Database getDb() {
		return db;
	}
	
}
