package Interface;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

public class SettingsFrame extends JFrame implements ActionListener {

	JMenu menu1, menu2, menu3, menu4, subMenu;
	JMenuBar menuBar;
	JTextField text;
	JMenuItem item1, item2, item3, item4, item5, item6, item7, item8;
	
	public SettingsFrame(String title) {
		super(title);
		setLayout(new BorderLayout());

		text = new JTextField();
		add(text, BorderLayout.CENTER);
		
		menuBar = new JMenuBar();
		menu1 = new JMenu("Session...");
		menu2 = new JMenu("Users");
		menu3 = new JMenu("Info");
		menu4 = new JMenu("Authors");
		
		item1 = new JMenuItem("New");
		item2 = new JMenuItem("Load");
		item3 = new JMenuItem("Save");
		item4 = new JMenuItem("Exit");
		
		item1.addActionListener(this);
		item2.addActionListener(this);
		item3.addActionListener(this);
		item4.addActionListener(this);
		
		menu1.add(item1);
		menu1.add(item2);
		menu1.add(item3);
		menu1.addSeparator();
		menu1.add(item4);
		
		subMenu = new JMenu("Choose");
		
		item5 = new JMenuItem("Adam");
		item6 = new JMenuItem("Kamil");
		item7 = new JMenuItem("Guest");
		item8 = new JMenuItem("Help");
		
		item5.addActionListener(this);
		item6.addActionListener(this);
		item7.addActionListener(this);
		item8.addActionListener(this);
		
		subMenu.add(item5);
		subMenu.add(item6);
		subMenu.add(item7);
		
		menu2.add(subMenu);
		menu3.add(item8);
		menu4.add(new JMenuItem("Adam Kalisz"));
		menu4.add(new JMenuItem("Kamil Rojszczak"));
		
		menuBar.add(menu1);
		menuBar.add(menu2);
		menuBar.add(menu3);
		menuBar.add(menu4);
		
		add(menuBar, BorderLayout.PAGE_START);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == item1) {
			text.setText(item1.getText());
		}
		if(e.getSource() == item2) {
			text.setText(item2.getText());
		}
		if(e.getSource() == item3) {
			text.setText(item3.getText());
		}
		if(e.getSource() == item4) {
			setVisible(false);
			text.setText(new String());
		}
		if(e.getSource() == item5) {
			text.setText("Hello Adam");
		}
		if(e.getSource() == item6) {
			text.setText("Hello Kamil");
		}
		if(e.getSource() == item7) {
			text.setText("Hello guest");
		}
		if(e.getSource() == item8) {
			text.setText(new String());
		}
	}

}

