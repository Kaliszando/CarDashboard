package Interface;

import javax.swing.*;

import Controller.Car;

public class MenuPanel extends JPanel {
	
	private Car car;
	
	public MenuPanel(Car car) {
		add(new JButton("Menu"));
		this.car = car;
	}
	
}
