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
	private JLabel JLstartStop, JLgears, JLblinkers, JLlights;
	private JSlider JSthrottle;
	private ButtonGroup BGlightsGroup;
	private JRadioButton JRBlightsOff, JRBlowBeamLights, JRBparkingLights;
	private Car car;
	private Hashtable<Integer, JLabel> labelTable;
	private JLabel JLhighBeams;
	private JButton JBhighBeams;
	private JLabel JLfogLights;
	private JCheckBox CBfrontFogLights;
	private JCheckBox CBrearFogLights;
	private JButton JBfogLights;
	private JLabel JLhazardLights;
	private JButton JBhazardLights;
	private JLabel JLgear;
	private JLabel JLgearVal;
	private JLabel JLfuelConsumption;
	private JLabel JLfuelConsumptionVal;
	private JLabel JLdistance;
	private JLabel JLdistanceVal;
	private JLabel JLavgSpeed;
	private JLabel JLavgSpeedVal;
	
	public ControlsPanel(Car car) {
		this.car = car;
		setLayout(null);
		setPreferredSize(new Dimension(1000, 200));
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
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
		
		JSthrottle.setBounds(1, 5, 110, 190);
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
		JLlights = new JLabel("Lights", JLabel.CENTER);
		JLlights.setBounds(300, 5, 100, 20);
		add(JLlights);
		
		JRBlightsOff = new JRadioButton("off");
		JRBparkingLights = new JRadioButton("parking");
		JRBlowBeamLights = new JRadioButton("low beam");
		
		add(JRBlightsOff);
		add(JRBparkingLights);
		add(JRBlowBeamLights);
		
		JRBlightsOff.setBounds(310, 30, 70, 30);
		JRBparkingLights.setBounds(310, 55, 90, 30);
		JRBlowBeamLights.setBounds(310, 80, 90, 30);
		
		JRBlightsOff.setSelected(true);
		JRBparkingLights.setSelected(false);
		JRBlowBeamLights.setSelected(false);
		
		BGlightsGroup = new ButtonGroup();
		BGlightsGroup.add(JRBlightsOff);
		BGlightsGroup.add(JRBlowBeamLights);
		BGlightsGroup.add(JRBparkingLights);
		
		JRBlightsOff.addActionListener(this);
		JRBparkingLights.addActionListener(this);
		JRBlowBeamLights.addActionListener(this);
		
		// High beam lights
		JLhighBeams = new JLabel("High beams", JLabel.CENTER);
		JLhighBeams.setBounds(300, 125, 100, 20);
		add(JLhighBeams);
		
		JBhighBeams = new JButton("Turn on");
		JBhighBeams.setBounds(310, 150, 80, 30);
		add(JBhighBeams);
		JBhighBeams.addActionListener(this);
		
		// Foglights
		JLfogLights = new JLabel("Fog lights", JLabel.CENTER);
		JLfogLights.setBounds(400, 5, 150, 20);
		add(JLfogLights);
		
		CBfrontFogLights = new JCheckBox("front", false);
		CBrearFogLights = new JCheckBox("rear", false);
		
		add(CBfrontFogLights);
		add(CBrearFogLights);
		
		CBfrontFogLights.setBounds(420, 30, 60, 30);
		CBrearFogLights.setBounds(480, 30, 60, 30);
		
		CBfrontFogLights.addActionListener(this);
		CBrearFogLights.addActionListener(this);
		
		JBfogLights = new JButton("Switch");
		JBfogLights.setBounds(435, 70, 80, 30);
		add(JBfogLights);
		JBfogLights.addActionListener(this);
		
		// Hazard lights
		JLhazardLights = new JLabel("Hazard lights", JLabel.CENTER);
		JLhazardLights.setBounds(400, 125, 150, 20);
		add(JLhazardLights);
		
		JBhazardLights = new JButton("Turn on");
		JBhazardLights.setBounds(435, 150, 80, 30);
		add(JBhazardLights);
		JBhazardLights.addActionListener(this);
		
		// Dashboard computer
		JLgear = new JLabel("Current gear", JLabel.CENTER);
		JLgear.setBounds(560, 0, 105, 66);
		add(JLgear);
		
		JLgearVal = new JLabel("N", JLabel.CENTER);
		JLgearVal.setBounds(655, 0, 105, 66);
		add(JLgearVal);
		
		JLfuelConsumption = new JLabel("Fuel consumption", JLabel.CENTER);
		JLfuelConsumption.setBounds(560, 66, 105, 66);
		add(JLfuelConsumption);
		
		JLfuelConsumptionVal = new JLabel("0.0", JLabel.CENTER);
		JLfuelConsumptionVal.setBounds(655, 66, 105, 66);
		add(JLfuelConsumptionVal);
		
		JLdistance = new JLabel("Distance", JLabel.CENTER);
		JLdistance.setBounds(560, 133, 105, 66);
		add(JLdistance);
		
		JLdistanceVal = new JLabel("0.0 km", JLabel.CENTER);
		JLdistanceVal.setBounds(655, 133, 105, 66);
		add(JLdistanceVal);
		
		//
		JLavgSpeed = new JLabel("Avg. speed", JLabel.CENTER);
		JLavgSpeed.setBounds(560+215, 0, 105, 66);
		add(JLavgSpeed);
		
		JLavgSpeedVal = new JLabel("0.0 km/h", JLabel.CENTER);
		JLavgSpeedVal.setBounds(655+225, 0, 105, 66);
		add(JLavgSpeedVal);
		
		JLfuelConsumption = new JLabel("Max speed", JLabel.CENTER);
		JLfuelConsumption.setBounds(560+215, 66, 105, 66);
		add(JLfuelConsumption);
		
		JLfuelConsumptionVal = new JLabel("0.0 km/h", JLabel.CENTER);
		JLfuelConsumptionVal.setBounds(655+225, 66, 105, 66);
		add(JLfuelConsumptionVal);
		
		JLdistance = new JLabel("Time", JLabel.CENTER);
		JLdistance.setBounds(560+215, 133, 105, 66);
		add(JLdistance);
		
		JLdistanceVal = new JLabel("0 s", JLabel.CENTER);
		JLdistanceVal.setBounds(655+225, 133, 105, 66);
		add(JLdistanceVal);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.GRAY);
		g.drawLine(300, 0, 300, 200);
		g.drawLine(400, 0, 400, 200);
		g.drawLine(550, 0, 550, 200);
		g.drawLine(760, 0, 760, 200);
		g.drawLine(550, 66, 1000, 66);
		g.drawLine(550, 66, 1000, 66);
		g.drawLine(550, 133, 1000, 133);
		
		JLgearVal.setText(car.gearToString());
		JLdistanceVal.setText(String.valueOf(car.getTimeInSec()) + " sec");
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == JBstartCar) car.start();
		if(evt.getSource() == JBstopCar) car.stop();
		if(evt.getSource() == JBgearUp) {
			//JLgearVal.setText(car.gearToString());
			car.gearUp();
		}
		if(evt.getSource() == JBgearDown) {
			//JLgearVal.setText(car.gearToString());
			car.gearDown();
		}
		if(evt.getSource() == JBleftBlinker) car.getLights().toggleLeftBlinker();
		if(evt.getSource() == JBrightBlinker) car.getLights().toggleRightBlinker();
		if(evt.getSource() == JRBlightsOff) {
			if(car.getLights().getRunningLights().isOn()) car.getLights().toggleRunningLights();
			if(car.getLights().getLowBeamLights().isOn()) car.getLights().toggleLowBeamLights();
		}
		if(evt.getSource() == JRBparkingLights) car.getLights().toggleRunningLights();
		if(evt.getSource() == JRBlowBeamLights) car.getLights().toggleLowBeamLights();
		if(evt.getSource() == JBhighBeams) car.getLights().toggleHighBeamLights();
		if(car.getLights().getHighBeamLights().isOn()) JBhighBeams.setText("Turn off");
		else JBhighBeams.setText("Turn on");
		if(evt.getSource() == JBfogLights) {
			if(CBfrontFogLights.isSelected())
				car.getLights().toggleFrontFogLigths();
			if(CBrearFogLights.isSelected())
				car.getLights().toggleRearFogLigths();
			CBfrontFogLights.setSelected(false);
			CBrearFogLights.setSelected(false);
		}
		if(evt.getSource() == JBhazardLights) car.getLights().toggleHazardLights();
		if(car.getLights().getHazardLights().isOn()) JBhazardLights.setText("Turn off");
		else JBhazardLights.setText("Turn on");
	}

	@Override
	public void stateChanged(ChangeEvent evt) {
        if (evt.getSource() == JSthrottle && car.isRunning()) {
        		car.setFixedSpeed(JSthrottle.getValue());
        } 
	}
	

}
