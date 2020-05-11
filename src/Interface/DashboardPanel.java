package Interface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import Controller.Car;
import Controller.InvalidDateException;

public class DashboardPanel extends JPanel {
	
	private Image dashboard, leftBlinker, rightBlinker, checkEngine, parkingLights, lowBeams, highBeams;
	RotateableImage speedPointer, rpmPointer, oilPointer, fuelPointer;
	Car car;
	
	public DashboardPanel(Car car) {
		setLayout(null);
		setBorder(BorderFactory.createLineBorder(Color.RED));
		this.car = car;
		
		// Dashboard background
		try {
			dashboard = ImageIO.read(new File("img/dashboard.png"));
			leftBlinker = ImageIO.read(new File("img/leftBlinker.png"));
			rightBlinker = ImageIO.read(new File("img/rightBlinker.png"));
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
		oilPointer.setVal(0);
		g2d.drawImage(oilPointer.getImg(), oilPointer.getAt(), null);
		
		// Fuel pointer
		fuelPointer.setTranslation(810, 389);
		fuelPointer.setVal(0);
		g2d.drawImage(fuelPointer.getImg(), fuelPointer.getAt(), null);
		
		// Blinkers
		if(car.getLights().getLeftBlinker().isOn())	g.drawImage(leftBlinker, 445, 120, null);
		if(car.getLights().getRightBlinker().isOn()) g.drawImage(rightBlinker, 500, 120, null);		
		
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
