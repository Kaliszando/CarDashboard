package GUI;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.Car;
import Controller.InvalidDateException;

/**
 * Klasa odpowiada za wy�wietlanie tablicy rozdzielczej, wskaz�wek pr�dko�ciomierza i obrotomierza, licznik�w.
 * 
 * @author Adam Kalisz
 * @author Kamil Rojszczak
 * @version 1.0
 * 
 */
public class DashboardPanel extends JPanel {
	
	private static final long serialVersionUID = -5399476276986171865L;
	private Image dashboard, leftBlinker, rightBlinker, checkEngine, parkingLights, lowBeams, highBeams, battery, hazards, frontFogLights, rearFogLights;
	private RotateableImage speedPointer, rpmPointer, waterPointer, fuelPointer;
	private Car car;
	private JLabel JLmileageTotal;
	private JLabel JLmileage1;
	private Font font1, font2;
	private JLabel JLmileage2;
	
	/**
	 * �aduje obrazy t�a, kontrolek, wskaz�wek oraz okre�la ich rozmieszczenie.
	 * Ustawia r�wnie� rozmieszczenie i wygl�d licznik�w dziennych i przebiegu.
	 * @param car obiekt klasy Car
	 */
	public DashboardPanel(Car car) {
		setLayout(null);
		this.car = car;
		
		// Loading images
		try {
			dashboard = ImageIO.read(new File("img/dashboard.png"));
			leftBlinker = ImageIO.read(new File("img/leftBlinker.png"));
			rightBlinker = ImageIO.read(new File("img/rightBlinker.png"));
			checkEngine = ImageIO.read(new File("img/check.png"));
			parkingLights = ImageIO.read(new File("img/parkingLights.png"));
			lowBeams = ImageIO.read(new File("img/lowBeamLights.png"));
			highBeams = ImageIO.read(new File("img/highBeamLights.png"));
			battery = ImageIO.read(new File("img/battery.png"));
			hazards = ImageIO.read(new File("img/hazards.png"));
			frontFogLights = ImageIO.read(new File("img/frontFogLights.png"));
			rearFogLights = ImageIO.read(new File("img/rearFogLights.png"));
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// Pointers
		speedPointer = new RotateableImage("img/bigPointer.png", 0, 200, 258);
		speedPointer.setCenter(99, 24);
		
		rpmPointer = new RotateableImage("img/bigPointer.png", 0, 7000, 258);
		rpmPointer.setCenter(99, 24);

		waterPointer = new RotateableImage("img/smallPointer.png", 70, 110, 102);
		waterPointer.setCenter(54, 45);
		
		fuelPointer = new RotateableImage("img/smallPointer.png", 0, 60, 102);
		fuelPointer.setCenter(54, 45);
		
		// Mileage
		font1 = new Font("Agency FB", 1, 20);
		JLmileageTotal = new JLabel("1231541.2", JLabel.RIGHT);
		JLmileageTotal.setBounds(263, 353, 115, 50);
		JLmileageTotal.setFont(font1);
		add(JLmileageTotal);
		
		font2 = new Font("Agency FB", 1, 18);
		JLmileage1 = new JLabel("1245.1", JLabel.RIGHT);
		JLmileage1.setBounds(598, 333, 115, 50);
		JLmileage1.setFont(font2);
		add(JLmileage1);
		
		JLmileage2 = new JLabel("312.1", JLabel.RIGHT);
		JLmileage2.setBounds(598, 353, 115, 50);
		JLmileage2.setFont(font2);
		add(JLmileage2);
		
	}
	
	/**
	 * Odpowiada za wywo�ywanie metod w celu wy�wietlenia obraz�w i ich animacji.
	 * @param g obiekt klasy Graphics umo�liwiaj�cy rysowanie
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g.drawImage(dashboard, 0, 0, null);
		
		// Car
		car.accelerate(car.getFixedSpeed());
		try {
			car.calculatePeriodRunning();
		} catch (InvalidDateException e) {
			e.printStackTrace();
		}
		car.update();
		
		// Speed pointer
		speedPointer.setTranslation(236, 253);
		speedPointer.setVal(car.getCurrentSpeed());
		g2d.drawImage(speedPointer.getImg(), speedPointer.getAt(), null);
		
		// Rpm pointer
		rpmPointer.setTranslation(570, 253);
		rpmPointer.setVal(car.getRpms());
		g2d.drawImage(rpmPointer.getImg(), rpmPointer.getAt(), null);
		
		// Water pointer
		waterPointer.setTranslation(80, 389);
		if(car.isRunning()) waterPointer.setVal(car.getWaterTemp());
		g2d.drawImage(waterPointer.getImg(), waterPointer.getAt(), null);
		
		// Fuel pointer
		fuelPointer.setTranslation(810, 389);
		if(car.isRunning()) fuelPointer.setVal(car.getFuel());	
		g2d.drawImage(fuelPointer.getImg(), fuelPointer.getAt(), null);
		
		// Blinkers
		if(car.getLights().getLeftBlinker().isOn())	g.drawImage(leftBlinker, 445, 120, null);
		if(car.getLights().getRightBlinker().isOn()) g.drawImage(rightBlinker, 500, 120, null);		
		
		// Dashboard lights
		if(car.getLights().getLowBeamLights().isOn()) g.drawImage(lowBeams, 380, 435, null);
		if(car.getLights().getRunningLights().isOn()) g.drawImage(parkingLights, 440, 435, null);
		if(car.getLights().getHighBeamLights().isOn()) g.drawImage(highBeams, 500, 435, null);
		if(car.getLights().getHazardLights().isOn()) g.drawImage(hazards, 560, 435, null);
		if(car.getLights().getFrontFogLights().isOn()) g.drawImage(frontFogLights, 620, 435, null);
		if(car.getLights().getRearFogLights().isOn()) g.drawImage(rearFogLights, 680, 435, null);
		if(!car.isRunning()) {
			g.drawImage(checkEngine, 260, 435, null);
			g.drawImage(battery, 320, 435, null);
			
			JLmileage2.setVisible(false);
			JLmileage1.setVisible(false);
			JLmileageTotal.setVisible(false);
		}
		else {
			JLmileage1.setText(String.valueOf(car.getMileage1()));
			JLmileage2.setText(String.valueOf(car.getMileage2()));
			JLmileageTotal.setText(String.valueOf(car.getMileageTotal()));
			
			JLmileage2.setVisible(true);
			JLmileage1.setVisible(true);
			JLmileageTotal.setVisible(true);
		}
		
		repaint();
	}
	
}
