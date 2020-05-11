package Interface;

import java.awt.Color;
import java.awt.Dimension;
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

public class DashboardPanel extends JPanel {
	
	private Image dashboard;
	RotateableImage speedPointer, rpmPointer, oilPointer, fuelPointer;
	
	public DashboardPanel() {
		setLayout(null);
		setBorder(BorderFactory.createLineBorder(Color.RED));
		
		// Dashboard background
		try {
			dashboard = ImageIO.read(new File("img/dashboard.png"));
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
	
	
	int i = 0;
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g.drawImage(dashboard, 0, 0, null);
		
		// Speed pointer
		speedPointer.setTranslation(236, 253);
		float val = 210.f - i++ * 0.01f;
		speedPointer.setVal(val);
		g2d.drawImage(speedPointer.getImg(), speedPointer.getAt(), null);
		
		// Rpm pointer
		rpmPointer.setTranslation(570, 253);
		rpmPointer.setVal(val * 100);
		g2d.drawImage(rpmPointer.getImg(), rpmPointer.getAt(), null);
		
		// Oil pointer
		oilPointer.setTranslation(80, 389);
		oilPointer.setVal(90);
		g2d.drawImage(oilPointer.getImg(), oilPointer.getAt(), null);
		
		// Fuel pointer
		fuelPointer.setTranslation(810, 389);
		fuelPointer.setVal(30);
		g2d.drawImage(fuelPointer.getImg(), fuelPointer.getAt(), null);
		
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
