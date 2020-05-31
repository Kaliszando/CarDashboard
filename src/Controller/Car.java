package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Data.CarSettings;
import Data.Database;
import Data.Travel;
import Data.XMLFileManager;

/**
 * Klasa reprezentuj�ca samoch�d i komputer pok�adowy.
 * 
 * Wykonuje operacje na takich warto�ciach jak przebieg ca�kowity, dystans, �rednie spalanie, pr�dko��, itd.
 * Pozwala operowa� silnikiem, zmian� bieg�w, w��czaniem i wy��czaniem �wiate�.
 * Rejestruje podr�e, kt�re zaczynaj� si� po wywo�aniu metody {@link #start()}, a ko�cz� {@link #stop()}
 * 
 * @version 1.1
 * @author Adam Kalisz
 * @author Kamil Rojszczak
 * 
 */

public class Car {
		
	private float mileageTotal;
	private float mileage1;
	private float mileage2;
	private float distance;
	private float avgFuelConsumption;
	private float fuelConsumed;
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
	private CarSettings settings;
	private float maxFuel;

	/**
	 * Konstruktor klasy Car. Ustawia domy�lne warto�ci oraz tworzy obiekt klasy Database do kt�rego b�d� p�niej zapisywane
	 * dane z podr�y.
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
		waterTemp = 0;
		fuel = 10;
		fuelConsumed = 0;
		lights = new CarLights();
		timeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		travels = new ArrayList<>();
		maxFuel = 60;
		
		// Connecting to database
		try {
			db = new Database();
		} catch (SQLException e) {
			System.out.println("Connecting to database failed");
		}
		
		// Loading backup
		XMLFileManager fm = new XMLFileManager();
		try {
			settings = (CarSettings) fm.readFromFile("bac/backup.dat");
			this.retrieveSettings(settings);
		} catch (ClassNotFoundException | IOException e) {
			// Default settings stay the same
			System.out.println("Loading backup file was unsuccesful");
		}
	}
	
	/**
	 * Ustawia dat� i czas uruchomienia silnika, uruchamia silnik poprzez ustawienie flagi isRunning.
	 * Tworzy obiekt currentTravel klasy Travel kt�ry przechowuje dane o aktualnej podr�y.
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
	 * Wy��cza silnik, zapisuje przebyty dystans, �rednie zu�ycie paliwa
	 * oraz dat� i czas zatrzymania silnika do obiektu currentTravel. 
	 * Dodaje ostatni� podr� do bazy danych.
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
			
			// Updating database
			try {
				db.addTravel(currentTravel);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			// Saving settings
			applySettings();
			saveSettings("bac/backup.dat");
			return;
		}
	}
	
	/**
	 * Zmienia bieg na wy�szy.
	 * Sprawdza czy mo�liwa jest zmiana biegu na wy�szy, je�li tak to go zmienia oraz odpowiednio zmniejsza obroty silnika.
	 */
	public void gearUp() {
		if(gear < 6) {
			gear++;
			rpms *= 0.5;
		}
	}
	
	/**
	 * Zmienia bieg na ni�szy.
	 * Sprawdza czy mo�liwa jest zmiana biegu na ni�szy, je�li tak to go zmienia oraz odpowiednio zwi�ksza obroty silnika.
	 */
	public void gearDown() {
		if(gear > 0) {
			gear--;
			if(isRunning) rpms += 600;
		}
	}
	
	/**
	 * Aktualizuje warto�ci komputera pok�adowego
	 */
	public void update() {
		if(currentSpeed > maxSpeed && isRunning) maxSpeed = currentSpeed;
		// 1 sec distance difference
		double distanceDiff = currentSpeed / 3.6 / 1000;
		float fuelDiff = 0;
		distance += distanceDiff;
		mileage1 += distanceDiff;
		mileage2 += distanceDiff;
		mileageTotal += distanceDiff;
		if(isRunning) avgSpeed = distance * 1000 / timeInSec * 3.6f;
		if(waterTemp < 20 && rpms > 100) waterTemp += 0.1;
		if(rpms > 0) {
			if(rpms < 2000) fuelDiff = (float) (9.5 / 3600);
			else if(rpms > 5000) fuelDiff = (float) (15.7 / 3600);
			else fuelDiff = (float) (6.2 / 3600);
			fuelConsumed += fuelDiff;
		}
		avgFuelConsumption = fuelConsumed * 3600 / timeInSec;
		fuel -= fuelDiff;
	}
	
	/**
	 * Zwraca pr�dko�� �redni� od pocz�tku podr�y
	 * @return pr�dko�� �rednia
	 */
	public float getAvgSpeed() {
		return avgSpeed;
	}

	/**
	 * Wykonywana cyklicznie, zwi�ksza lub zminiejsza obroty silnika
	 * w zale�no�ci od ustawionej pr�dko�ci, podobnie jak w tempomacie.
	 * 
	 * @param desiredSpeed docelowa pr�dko�� kt�r� chcemy osi�gn��
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
		if(rpms < 0) rpms = 0;
	}
	
	/**
	 * Przywraca ustawienia z odczytanego przedniej obiektu CarSettings.
	 * 
	 * @param backup obiekt klasy CarSettings z danymi kt�re chcemy przywr�ci�
	 */
	public void retrieveSettings(CarSettings backup) {
		this.mileageTotal = backup.getMileageTotal();
		this.mileage1 = backup.getMileage1();
		this.mileage2 = backup.getMileage2();
		this.fuel = backup.getFuel();
	}
	
	/**
	 * Zapisuje ustawienia zapisane w obiekcie klasy CarSettings.
	 * @param path �cie�ka zapisu
	 */
	public void saveSettings(String path) {
		XMLFileManager fm = new XMLFileManager();
		try {
			fm.saveToFile(settings, path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Zwraca ustawienie akutalnie zachowane w obiekcie klasy Car.
	 * @return zachowane ustawienia
	 */
	public CarSettings getSettings() {
		return settings;
	}
	
	/**
	 * Ustawia aktualne warto�ci przebiegu i paliwa do zmiennej CarSettings.
	 */
	public void applySettings() {
		settings = new CarSettings(this);
	}

	/**
	 * Zeruje wszystkie warto�ci zachowanych ustawie� samochodu.
	 * 
	 */
	public void resetSettings() {
		this.settings = new CarSettings();
	}
	
	/**
	 * Oblicza czas pomi�dzy uruchomieniem i zatrzymaniem silnika (aktualnym czasem je�li silnik jest uruchomiony).
	 * Zapisuje czas trwania do zmiennej totalTime w formacie godziny:minuty:sekundy.
	 * @throws InvalidDateException wyj�tek niepoprawnej daty
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
	 * Zwraca aktualny biegu samochodu.
	 * @return warto�� atualnego biegu samochodu jako String
	 */
	public String gearToString() {
		return gear == 0 ? "N" : String.valueOf(this.gear);
	}

	/**
	 * Ustawia ilo�� paliwa.
	 * @param fuel ilo�� paliwa jako float
	 */
	public void setFuel(float fuel) {
		if(fuel > maxFuel) return;
		this.fuel = fuel;
	}
	
	/**
	 * Zwraca przebieg ca�kowity.
	 * @return zwraca ilo�� przebytych kilometr�w jako float
	 */
	public float getMileageTotal() {
		return mileageTotal;
	}

	/**
	 * Zwraca warto�� czasu jaki zosta� uprzednio obliczony poprzez {@link #calculatePeriodRunning()}.
	 * @return zwraca warto�� czasu jaki up�yn�� jako string w formacie: godziny:minuty:sekundy.
	 */
	public String getTotalTime() {
		return totalTime.toString();
	}
	
	/**
	 * Dodaje do przebiegu podan� warto��.
	 * @param add zmienna typu float kt�ra zostanie dodana do przebiegu ca�kowitego
	 */
	public void increaseMileageTotal(float add) {
		this.mileageTotal += add;
	}

	/**
	 * Zwraca warto�� pierwszego licznika dziennego.
	 * @return zwraca ilo�� przebytych kilometr�w jako float
	 */
	public float getMileage1() {
		return mileage1;
	}

	/**
	 * Dodaje do pierwszego licznika dziennego podan� warto��.
	 * @param add zmienna typu float kt�ra zostanie dodana
	 */
	public void increaseMileage1(float add) {
		this.mileage1 += add;
	}
	
	/**
	 * Resetuje warto�� pierwszego licznika dziennego.
	 */
	public void resetMileage1() {
		this.mileage1 = 0;
	}

	/**
	 * Zwraca warto�� drugiego licznika dziennego.
	 * @return zwraca ilo�� przebytych kilometr�w jako float
	 */
	public float getMileage2() {
		return mileage2;
	}
	
	/**
	 * Dodaje do drugiego licznika dziennego podan� warto��.
	 * @param add zmienna typu float kt�ra zostanie dodana
	 */
	public void increaseMileage2(float add) {
		this.mileage2 += add;
	}

	/**
	 * Resetuje warto�� drugiego licznika dziennego.
	 */
	public void resetMileage2() {
		this.mileage2 = 0;
	}

	/**
	 * Zwraca przebyty dystans od uruchomienia silnika.
	 * @return zwraca d�ugo�� przebytego dystansu jako float
	 */
	public float getDistance() {
		return distance;
	}

	/**
	 * Dodaje do odleg�o�ci podr�y podan� zmienn�.
	 * @param add zmienna typu float kt�ra zostanie dodana do dystansu aktualnej podr�y
	 */
	public void increaseDistance(float add) {
		this.distance += add;
	}

	/**
	 * Zwraca �rednie zu�ycie paliwa.
	 * @return zwraca �rednie zu�ycie paliwa jako float
	 */
	public float getAvgFuelConsumption() {
		return avgFuelConsumption;
	}

	/**
	 * Ustawia �rednie zu�ycie paliwa.
	 * @param avgFuelConsumption �rednie zu�ycie paliwa jako float
	 */
	public void setAvgFuelConsumption(float avgFuelConsumption) {
		this.avgFuelConsumption = avgFuelConsumption;
	}

	/**
	 * Zwraca maksymaln� pr�dko��.
	 * @return zwraca maksymaln� pr�dko�� jako float
	 */
	public float getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * Ustawia maksynaln� pr�dko��.
	 * @param maxSpeed maksymaln� pr�dko�� jako float
	 */
	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	/**
	 * Oblicza aktualn� pr�dko��.
	 * Iloczyn obrot�w silnika i warto�ci odpowiadaj�cej biegu samochodu.
	 * @return zwraca aktualn� pr�dko�� jako float
	 */
	public float getCurrentSpeed() {
		return rpms * gearRatios[gear];
	}

	/**
	 * Ustawia aktualn� pr�dko��.
	 * @param currentSpeed aktualna pr�dko��
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
	 * Stan silnika (w��czony/wy��czony).
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
	 * @return czas pocz�tkowy jako String
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
	 * Zwraca pr�dko�� ustawion� na tempomacie.
	 * @return pr�dko�� ustawiona na tempomacie jako float
	 */
	public float getFixedSpeed() {
		return fixedSpeed;
	}

	/**
	 * Ustawia pr�dko�� na tempomacie.
	 * @param fixedSpeed pr�dko�� na tempomacie jako float
	 */
	public void setFixedSpeed(float fixedSpeed) {
		this.fixedSpeed = fixedSpeed;
	}

	/**
	 * Zwraca temperatur� p�ynu ch�odniczego.
	 * @return temperatura p�ynu ch�odniczego jako float
	 */
	public float getWaterTemp() {
		return waterTemp;
	}

	/**
	 * Ustawia temperatur� p�ynu ch�odniczego.
	 * @param waterTemp temperatura p�ynu ch�odniczego jako float
	 */
	public void setWaterTemp(float waterTemp) {
		this.waterTemp = waterTemp;
	}

	/**
	 * Zwraca ilo�� paliwa.
	 * @return ilo�� paliwa jako float
	 */
	public float getFuel() {
		return fuel;
	}

	/**
	 * Zwraca list� zapisanych podr�y.
	 * @return zapisane podr�e
	 */
	public ArrayList<Travel> getTravels() {
		return travels;
	}
	
	/**
	 * Zwraca czas jaki up�yn�� od uruchomienia silnika.
	 * @return timeInSec czas w sekundach
	 */
	public long getTimeInSec() {
		return timeInSec;
	}
	
	/**
	 * Zwraca obiekt klasy Databse, reprezentuj�cy baz� danych.
	 * @return obiekt klasy Database
	 */
	public Database getDb() {
		return db;
	}
	
}
