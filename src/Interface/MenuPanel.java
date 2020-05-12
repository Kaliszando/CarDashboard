package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Controller.Car;

public class MenuPanel extends JPanel implements ActionListener {
	
	private Car car;
	private JButton JBshowSettings, JBhideSettings;
	private SettingsFrame settingsFrame;
	
	public MenuPanel(Car car) {
		this.car = car;
		
		JBshowSettings = new JButton("Show settings");
		JBhideSettings = new JButton("Hide settings");
		
		add(JBshowSettings);
		add(JBhideSettings);
		
		JBshowSettings.addActionListener(this);
		JBhideSettings.addActionListener(this);
		
		settingsFrame = new SettingsFrame("Settings");
		settingsFrame.setSize(360, 200);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == JBshowSettings) settingsFrame.setVisible(true);
		if(e.getSource() == JBhideSettings) settingsFrame.setVisible(false);
	}
	
}
