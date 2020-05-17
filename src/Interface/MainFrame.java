package Interface;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Controller.Car;

public class MainFrame extends JFrame {
	
	private MenuPanel menuPanel;
	private DashboardPanel dashboardPanel;
	private ControlsPanel controlsPanel;
	
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
