package GUI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Controller.Car;

/**
 * Klasa odpowiada za stworzenie menu ponad desk¹ rozdzielcza.
 * 
 * @version 1.0
 * @author Adam Kalisz
 * @author Kamil Rojszczak
 * 
 */
public class MenuPanel extends JPanel implements ActionListener {
	
	private Car car;
	private JButton JBsettings, JBhistory;
	private SettingsFrame settingsFrame;
	private TravelHistoryFrame historyFrame;
	private boolean settingsVisible, historyVisible;
	
	/**
	 * Odpowiada za stworzenie menu i jego zawartoœæ. 
	 * 
	 * @param car obiekt klasy Car
	 */
	public MenuPanel(Car car) {
		setLayout(new FlowLayout(FlowLayout.CENTER));
		this.car = car;
		
		// Settings
		JBsettings = new JButton("Settings");
		add(JBsettings);
		JBsettings.addActionListener(this);
		
		settingsFrame = new SettingsFrame("Settings");
		settingsFrame.setSize(360, 200);
		settingsVisible = false;
		
		// Travel history
		JBhistory = new JButton("Travel history");
		add(JBhistory);
		JBhistory.addActionListener(this);
		
		historyFrame = new TravelHistoryFrame("Travels", car);
		historyFrame.setSize(900, 200);
		historyVisible = false;
	}

	/**
	 * Obs³uga zdarzeñ menu.
	 * @param e powsta³e zdarzenie
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == JBsettings) {
			settingsVisible = !settingsVisible;
			settingsFrame.setVisible(settingsVisible);
		}
		if(e.getSource() == JBhistory) {
			historyVisible = !historyVisible;
			historyFrame.setVisible(historyVisible);
		}
	}
	
}
