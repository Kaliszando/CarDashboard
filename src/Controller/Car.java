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
 * Klasa reprezentuj¹ca samochód i komputer pok³adowy.
 * 
 * Wykonuje operacje na takich wartoœciach jak przebieg ca³kowity, dystans, œrednie spalanie, prêdkoœæ, itd.
 * Pozwala operowaæ silnikiem, zmian¹ biegów, w³¹czaniem i wy³¹czaniem œwiate³.
 * Rejestruje podró¿e, które zaczynaj¹ siê po wywo³aniu metody {@link #start()}, a koñcz¹ {@link #stop()}
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
	 * Konstruktor klasy Car. Ustawia domyœlne wartoœci oraz tworzy obiekt klasy Database do którego bêd¹ póŸniej zapisywane
	 * dane z podró¿y.
	 */
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
		waterTemp = 73;
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
	 * Ustawia datê i czas uruchomienia silnika, uruchamia silnik poprzez ustawienie flagi isRunning.
	 * Tworzy obiekt currentTravel klasy Travel który przechowuje dane o aktualnej podró¿y.
	 */
	public void start() {
		avgSpeed = 0;
		setStartTime(LocalDateTime.now());
		setRunning(true);
		currentTravel = new Travel(0, mileageTotal, 0, LocalDateTime.now());
	}
	
	/**
	 * Wy³¹cza silnik, zapisuje przebyty dystans, œrednie zu¿ycie paliwa
	 * oraz datê i czas zatrzymania silnika do obiektu currentTravel. 
	 * Dodaje ostatni¹ podró¿ do bazy danych.
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
	 * Zmienia bieg na wy¿szy.
	 * Sprawdza czy mo¿liwa jest zmiana biegu na wy¿szy, jeœli tak to go zmienia oraz odpowiednio zmniejsza obroty silnika.
	 */
	public void gearUp() {
		if(gear < 6) {
			gear++;
			rpms /= 2;
		}
	}
	
	/**
	 * Zmienia bieg na ni¿szy.
	 * Sprawdza czy mo¿liwa jest zmiana biegu na ni¿szy, jeœli tak to go zmienia oraz odpowiednio zwiêksza obroty silnika.
	 */
	public void gearDown() {
		if(gear > 0) {
			gear--;
			rpms += 600;
		}
	}
	
	/**
	 * Aktualizuje prêdkoœæ i dystans
	 */
	public void update() {
		//avgSpeed += currentSpeed;
		//System.out.println(avgSpeed / timeInSec + " " + timeInSec);		
	}
	
	
	/**
	 * Wykonywana cyklicznie, zwiêszka lub zminiejsza obroty silnika
	 * w zale¿noœci od ustawionej prêdkoœci, podobnie jak w tempomacie.
	 * 
	 * @param desiredSpeed docelowa prêdkoœæ któr¹ chcemy osi¹gn¹æ
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
	
	/**
	 * Oblicza czas pomiêdzy uruchomieniem i zatrzymaniem silnika (aktualnym czasem jeœli silnik jest uruchomiony).
	 * Zapisuje czas trwania do zmiennej totalTime w formacie godziny:minuty:sekundy.
	 * @throws InvalidDateException wyj¹tek niepoprawnej daty
	 */
	public void calculatePeriodRunning() throws InvalidDateException {
		if(startTime == null) {
			return;
		}
		if(isRunning)stopTime = LocalDateTime.now();
		
		Duration duration = Duration.between(startTime, stopTime);
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
	 * @return wartoœæ atualnego biegu samochodu jako String
	 */
	public String gearToString() {
		return gear == 0 ? "N" : String.valueOf(this.gear);
	}

	/**
	 * Ustawia iloœæ paliwa.
	 * @param fuel iloœæ paliwa jako float
	 */
	public void setFuel(float fuel) {
		this.fuel = fuel;
	}
	
	/**
	 * Zwraca przebieg ca³kowity.
	 * @return zwraca iloœæ przebytych kilometrów jako float
	 */
	public float getMileageTotal() {
		return mileageTotal;
	}

	/**
	 * Zwraca wartoœæ czasu jaki zosta³ uprzednio obliczony poprzez {@link #calculatePeriodRunning()}.
	 * @return zwraca wartoœæ czasu jaki up³yn¹³ jako string w formacie: godziny:minuty:sekundy.
	 */
	public String getTotalTime() {
		return totalTime.toString();
	}
	
	/**
	 * Dodaje do przebiegu podan¹ wartoœæ.
	 * @param add zmienna typu float która zostanie dodana do przebiegu ca³kowitego
	 */
	public void increaseMileageTotal(float add) {
		this.mileageTotal += add;
	}

	/**
	 * Zwraca wartoœæ pierwszego licznika dziennego.
	 * @return zwraca iloœæ przebytych kilometrów jako float
	 */
	public float getMileage1() {
		return mileage1;
	}

	/**
	 * Dodaje do pierwszego licznika dziennego podan¹ wartoœæ.
	 * @param add zmienna typu float która zostanie dodana
	 */
	public void increaseMileage1(float add) {
		this.mileage1 += add;
	}
	
	/**
	 * Resetuje wartoœæ pierwszego licznika dziennego.
	 */
	public void resetMileage1() {
		this.mileage1 = 0;
	}

	/**
	 * Zwraca wartoœæ drugiego licznika dziennego.
	 * @return zwraca iloœæ przebytych kilometrów jako float
	 */
	public float getMileage2() {
		return mileage2;
	}
	
	/**
	 * Dodaje do drugiego licznika dziennego podan¹ wartoœæ.
	 * @param add zmienna typu float która zostanie dodana
	 */
	public void increaseMileage2(float add) {
		this.mileage2 += add;
	}

	/**
	 * Resetuje wartoœæ drugiego licznika dziennego.
	 */
	public void resetMileage2() {
		this.mileage2 = 0;
	}

	/**
	 * Zwraca przebyty dystans od uruchomienia silnika.
	 * @return zwraca d³ugoœæ przebytego dystansu jako float
	 */
	public float getDistance() {
		return distance;
	}

	/**
	 * Dodaje do odleg³oœci podró¿y podan¹ zmienn¹.
	 * @param add zmienna typu float która zostanie dodana do dystansu aktualnej podró¿y
	 */
	public void increaseDistance(float add) {
		this.distance += add;
	}

	/**
	 * Zwraca œrednie zu¿ycie paliwa.
	 * @return zwraca œrednie zu¿ycie paliwa jako float
	 */
	public float getAvgFuelConsumption() {
		return avgFuelConsumption;
	}

	/**
	 * Ustawia œrednie zu¿ycie paliwa.
	 * @param avgFuelConsumption œrednie zu¿ycie paliwa jako float
	 */
	public void setAvgFuelConsumption(float avgFuelConsumption) {
		this.avgFuelConsumption = avgFuelConsumption;
	}

	/**
	 * Zwraca maksymaln¹ prêdkoœæ.
	 * @return zwraca maksymaln¹ prêdkoœæ jako float
	 */
	public float getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * Ustawia maksynaln¹ prêdkoœæ.
	 * @param maxSpeed maksymaln¹ prêdkoœæ jako float
	 */
	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	/**
	 * Oblicza aktualn¹ prêdkoœæ.
	 * Iloczyn obrotów silnika i wartoœci odpowiadaj¹cej biegu samochodu.
	 * @return zwraca aktualn¹ prêdkoœæ jako float
	 */
	public float getCurrentSpeed() {
		return rpms * gearRatios[gear];
	}

	/**
	 * Ustawia aktualn¹ prêdkoœæ.
	 * @param currentSpeed aktualna prêdkoœæ
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
	 * Stan silnika (w³¹czony/wy³¹czony).
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
	 * @return czas pocz¹tkowy jako String
	 */
	public String getStartTime() {
		return startTime.format(timeFormat);
	}

	/**
	 * Ustawia czas startu silnika.
	 * @param startTime czas pocz¹tkowy
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
	 * Zwraca prêdkoœæ ustawion¹ na tempomacie.
	 * @return prêdkoœæ ustawiona na tempomacie jako float
	 */
	public float getFixedSpeed() {
		return fixedSpeed;
	}

	/**
	 * Ustawia prêdkoœæ na tempomacie.
	 * @param fixedSpeed prêdkoœæ na tempomacie jako float
	 */
	public void setFixedSpeed(float fixedSpeed) {
		this.fixedSpeed = fixedSpeed;
	}

	/**
	 * Zwraca temperaturê p³ynu ch³odniczego.
	 * @return temperatura p³ynu ch³odniczego jako float
	 */
	public float getWaterTemp() {
		return waterTemp;
	}

	/**
	 * Ustawia temperaturê p³ynu ch³odniczego.
	 * @param waterTemp temperatura p³ynu ch³odniczego jako float
	 */
	public void setWaterTemp(float waterTemp) {
		this.waterTemp = waterTemp;
	}

	/**
	 * Zwraca iloœæ paliwa.
	 * @return iloœæ paliwa jako float
	 */
	public float getFuel() {
		return fuel;
	}

	/**
	 * Zwraca listê zapisanych podró¿y.
	 * @return zapisane podró¿e
	 */
	public ArrayList<Travel> getTravels() {
		return travels;
	}
	
	/**
	 * Zwraca czas jaki up³yn¹³ od uruchomienia silnika.
	 * @return timeInSec czas w sekundach
	 */
	public long getTimeInSec() {
		return timeInSec;
	}
	
	/**
	 * Zwraca obiekt klasy Databse, reprezentuj¹cy bazê danych.
	 * @return obiekt klasy Database
	 */
	public Database getDb() {
		return db;
	}
	
}
