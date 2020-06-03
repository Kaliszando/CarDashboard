package GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.Car;

/**
 * Klasa reprezentuje okienko dialogowe ustawieñ.
 * 
 * @version 1.0
 * @author Adam Kalisz
 * @author Kamil Rojszczak
 * 
 */
public class SettingsFrame extends JFrame implements ActionListener {
	
	private Car car;
	private JButton jbReset1, jbReset2, jbFuel1, jbFuel2, jbFuel3;
	private JTextField jtxt1, jtxt2, jtxt3, jtxt4, jtxt5, jtxt6;
	private JPanel jp1, jp2, jp3;
	
	/**
	 * Tworzy okno ustawieñ.
	 * @param title tytu³ okienka dialogowego
	 */
	public SettingsFrame(Car car, String title) {
		super(title);
		this.car = car;
		setLayout(new GridLayout(6,1));
		setSize(360, 360);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2 - this.getWidth()/2, dim.height/2 - this.getHeight()/2);
		
		// Reset mileage counters
		add(new JLabel("Reset mileage counters", JLabel.CENTER));
		
		jbReset1 = new JButton("Counter 1");
		jbReset2 = new JButton("Counter 2");
		
		jbReset1.addActionListener(this);
		jbReset2.addActionListener(this);

		jp1 = new JPanel();
		jp1.add(jbReset1);
		jp1.add(jbReset2);
		add(jp1);
		
		// Refuel
		add(new JLabel("Refuel", JLabel.CENTER));
		
		jbFuel1 = new JButton("+ 5l");
		jbFuel2 = new JButton("+ 10l");
		jbFuel3 = new JButton("Full tank");
		
		jbFuel1.addActionListener(this);
		jbFuel2.addActionListener(this);
		jbFuel3.addActionListener(this);
		
		jp2 = new JPanel();
		jp2.add(jbFuel1);
		jp2.add(jbFuel2);
		jp2.add(jbFuel3);
		add(jp2);
		
		// Gear ratios
		add(new JLabel("Change gear ratios", JLabel.CENTER));
		
		jtxt1 = new JTextField(String.valueOf(car.getGearRatios()[1]));
		jtxt2 = new JTextField(String.valueOf(car.getGearRatios()[2]));
		jtxt3 = new JTextField(String.valueOf(car.getGearRatios()[3]));
		jtxt4 = new JTextField(String.valueOf(car.getGearRatios()[4]));
		jtxt5 = new JTextField(String.valueOf(car.getGearRatios()[5]));
		jtxt6 = new JTextField(String.valueOf(car.getGearRatios()[6]));
		
		jtxt1.addActionListener(this);
		jtxt2.addActionListener(this);
		jtxt3.addActionListener(this);
		jtxt4.addActionListener(this);
		jtxt5.addActionListener(this);
		jtxt6.addActionListener(this);
		
		jp3 = new JPanel();
		jp3.add(jtxt1);
		jp3.add(jtxt2);
		jp3.add(jtxt3);
		jp3.add(jtxt4);
		jp3.add(jtxt5);
		jp3.add(jtxt6);
		add(jp3);
	}
	
	/**
	 * Obs³uga zdarzeñ.
	 * @param e powsta³e zdarzenie
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jbReset1) car.resetMileage1();
		if(e.getSource() == jbReset2) car.resetMileage2();
		if(e.getSource() == jbFuel1) car.addFuel(5);
		if(e.getSource() == jbFuel2) car.addFuel(10);
		if(e.getSource() == jbFuel3) car.setFuel(car.getMaxFuel());
		if(e.getSource() == jtxt1 || e.getSource() == jtxt2 || e.getSource() == jtxt3 
				|| e.getSource() == jtxt4 || e.getSource() == jtxt5 || e.getSource() == jtxt6) {
			float[] changedGears = new float[7];
			changedGears[0] = 0;
			changedGears[1] = Float.valueOf(jtxt1.getText());
			changedGears[2] = Float.valueOf(jtxt2.getText());
			changedGears[3] = Float.valueOf(jtxt3.getText());
			changedGears[4] = Float.valueOf(jtxt4.getText());
			changedGears[5] = Float.valueOf(jtxt5.getText());
			changedGears[6] = Float.valueOf(jtxt6.getText());

			if(changedGears[1] >= car.getGearRatios()[2] || changedGears[2] >= car.getGearRatios()[3]
					|| changedGears[3] >= car.getGearRatios()[4] || changedGears[4] >= car.getGearRatios()[5] || changedGears[5] >= car.getGearRatios()[6]) {
				jtxt1.setText(String.valueOf(car.getGearRatios()[1]));
				jtxt2.setText(String.valueOf(car.getGearRatios()[2]));
				jtxt3.setText(String.valueOf(car.getGearRatios()[3]));
				jtxt4.setText(String.valueOf(car.getGearRatios()[4]));
				jtxt5.setText(String.valueOf(car.getGearRatios()[5]));
				jtxt6.setText(String.valueOf(car.getGearRatios()[6]));
				return;
			}

			car.setGearRatios(changedGears);
		}
	}
}

