package Interface;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Controller.Car;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = -6190465062949119095L;
	private Car car;
	private MenuPanel menuPanel;
	private DashboardPanel dashboardPanel;
	private ControlsPanel controlsPanel;
	
	public MainFrame(Car car) {
		super("Car dashboard");
		setLayout(new BorderLayout());
		
		menuPanel = new MenuPanel();
		dashboardPanel = new DashboardPanel();
		controlsPanel = new ControlsPanel();
		
		add(menuPanel, BorderLayout.PAGE_START);
		add(dashboardPanel, BorderLayout.CENTER);
		add(controlsPanel, BorderLayout.PAGE_END);
		this.car = car;	
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 622);
		setVisible(true);
	}

}
