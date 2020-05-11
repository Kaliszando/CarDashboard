package Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Controller.Car;

public class ControlsPanel extends JPanel implements ActionListener, ChangeListener {
	
	private JButton JBgearUp, JBgearDown, JBstartCar, JBstopCar, JBleftBlinker, JBrightBlinker;
	private JLabel JLstartStop, JLgears, JLblinkers;
	private JSlider JSthrottle;
	private Car car;
	private Hashtable<Integer, JLabel> labelTable;
	
	public ControlsPanel(Car car) {
		this.car = car;
		setLayout(null);
		setPreferredSize(new Dimension(1000, 200));
		
		// Slider
		JSthrottle = new JSlider(JSlider.VERTICAL, 0, 200, 0);
		JSthrottle.setMajorTickSpacing(50);
		JSthrottle.setMinorTickSpacing(10);
		JSthrottle.setPaintTicks(true);
		JSthrottle.setPaintLabels(true);

		labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put( new Integer(0), new JLabel("Stop"));
		labelTable.put( new Integer(50), new JLabel("50 km/h"));
		labelTable.put( new Integer(100), new JLabel("100 km/h"));
		labelTable.put( new Integer(150), new JLabel("150 km/h"));
		labelTable.put( new Integer(200), new JLabel("Top speed"));
		JSthrottle.setLabelTable(labelTable);
		
		JSthrottle.setBounds(-5, 5, 120, 190);
		add(JSthrottle);
		JSthrottle.addChangeListener(this);
		
		// Start/Stop
		JLstartStop = new JLabel("Engine", JLabel.CENTER);
		JLstartStop.setBounds(120, 5, 170, 20);
		add(JLstartStop);
		
		JBstartCar = new JButton("Start");
		JBstopCar = new JButton("Stop");
		
		JBstartCar.setBounds(120, 30, 80, 30);
		JBstopCar.setBounds(210, 30, 80, 30);
		
		add(JBstartCar);
		add(JBstopCar);
		
		JBstartCar.addActionListener(this);
		JBstopCar.addActionListener(this);
		
		// Gears
		JLgears = new JLabel("Gears", JLabel.CENTER);
		JLgears.setBounds(120, 65, 170, 20);
		add(JLgears);
		
		JBgearUp = new JButton("Up");
		JBgearDown = new JButton("Down");
		
		JBgearUp.setBounds(120, 90, 80, 30);
		JBgearDown.setBounds(210, 90, 80, 30);
		
		add(JBgearUp);
		add(JBgearDown);
		
		JBgearUp.addActionListener(this);
		JBgearDown.addActionListener(this);
		
		// Blinkers
		JLblinkers = new JLabel("Blinkers", JLabel.CENTER);
		JLblinkers.setBounds(120, 125, 170, 20);
		add(JLblinkers);
		
		JBleftBlinker = new JButton("Left");
		JBrightBlinker = new JButton("Right");
		
		JBleftBlinker.setBounds(120, 150, 80, 30);
		JBrightBlinker.setBounds(210, 150, 80, 30);
		
		add(JBleftBlinker);
		add(JBrightBlinker);
		
		JBleftBlinker.addActionListener(this);
		JBrightBlinker.addActionListener(this);
		// Lights
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.GRAY);
		g.drawLine(300, 0, 300, 200);
		
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == JBstartCar) car.start();
		if(evt.getSource() == JBstopCar) car.stop();
		if(evt.getSource() == JBgearUp) car.gearUp();
		if(evt.getSource() == JBgearDown) car.gearDown();
		if(evt.getSource() == JBleftBlinker) {
			car.getLights().toggleLeftBlinker();
			System.out.println(car.getLights().toString());
		}
		if(evt.getSource() == JBrightBlinker) {
			car.getLights().toggleRightBlinker();
			System.out.println(car.getLights().toString());
		}
		
	}

	@Override
	public void stateChanged(ChangeEvent evt) {
        if (evt.getSource() == JSthrottle && car.isRunning()) {
        		car.setFixedSpeed(JSthrottle.getValue());
        } 
	}
	

}
