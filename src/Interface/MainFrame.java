package Interface;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = -6190465062949119095L;
	private JPanel contentPane;
	
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 800);
		
		contentPane = new JPanel();
		setContentPane(contentPane);
	}


}
