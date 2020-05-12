package Interface;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.Car;
import Controller.InvalidDateException;

public class DashboardPanel extends JPanel {
	
	private Image dashboard, leftBlinker, rightBlinker, checkEngine, parkingLights, lowBeams, highBeams, battery, hazards, frontFogLights, rearFogLights;
	private RotateableImage speedPointer, rpmPointer, oilPointer, fuelPointer;
	private Car car;
	private JLabel JLmileageTotal;
	private JLabel JLmileage1;
	private Font font1, font2;
	private JLabel JLmileage2;
	
	public DashboardPanel(Car car) {
		setLayout(null);
		this.car = car;
		
		// Dashboard background
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

		oilPointer = new RotateableImage("img/smallPointer.png", 70, 110, 102);
		oilPointer.setCenter(54, 45);
		
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
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g.drawImage(dashboard, 0, 0, null);
		
		// Car
		car.accelerate(car.getFixedSpeed());
		
		// Speed pointer
		speedPointer.setTranslation(236, 253);
		speedPointer.setVal(car.getCurrentSpeed());
		g2d.drawImage(speedPointer.getImg(), speedPointer.getAt(), null);
		
		// Rpm pointer
		rpmPointer.setTranslation(570, 253);
		rpmPointer.setVal(car.getRpms());
		g2d.drawImage(rpmPointer.getImg(), rpmPointer.getAt(), null);
		
		// Oil pointer
		oilPointer.setTranslation(80, 389);
		if(car.isRunning()) oilPointer.setVal(car.getOilTemp());
		g2d.drawImage(oilPointer.getImg(), oilPointer.getAt(), null);
		
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
	
	public BufferedImage LoadImage(String path) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
}
