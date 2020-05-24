package GUI;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Controller.Car;

/**
 * Klasa odpowiada za otwarcie g³ównego okna JFrame oraz wywo³anie klas MenuPanel, DashboardPanel i ControlsPanel.
 * @version 1.0
 * @author Adam Kalisz
 * @author Kamil Rojszczak
 * 
 */
public class MainFrame extends JFrame {
	
	private MenuPanel menuPanel;
	private DashboardPanel dashboardPanel;
	private ControlsPanel controlsPanel;
	
	/**
	 * Otwiera g³ówne okno JFrame oraz wywo³uje klas MenuPanel, DshboardPanel i ControlsPanel.
	 * @param car obiekt klasy Car
	 */
	public MainFrame(Car car) {
		super("Car dashboard");
		
		setLayout(new BorderLayout());
		
		menuPanel = new MenuPanel(car);
		dashboardPanel = new DashboardPanel(car);
		controlsPanel = new ControlsPanel(car);
		
		add(menuPanel, BorderLayout.PAGE_START);
		add(dashboardPanel, BorderLayout.CENTER);
		add(controlsPanel, BorderLayout.PAGE_END);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1015, 790);
		setVisible(true);
	}

}
