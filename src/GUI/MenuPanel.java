package GUI;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Controller.Car;
import Data.CarSettings;
import Data.XMLFileManager;

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
	private SettingsFrame settingsFrame;
	private TravelHistoryFrame historyFrame;
	private InfoFrame infoFrame;
	private AboutFrame aboutFrame;
	private JMenuBar menubar;
	private JMenu fileMenu, viewMenu, helpMenu;
	private JMenuItem miNew, miLoad, miSave, miExit, miCloseAll, miAbout, miInfo;
	private JCheckBoxMenuItem miSettings, miTravelHistory;
	
	/**
	 * Odpowiada za stworzenie menu i jego zawartoœæ. 
	 * 
	 * @param car obiekt klasy Car
	 */
	public MenuPanel(Car car) {
		this.car = car;
		setLayout(new GridLayout(1, 1));
		
		// Menu bar
		menubar = new JMenuBar();
		add(menubar);
		
		// File menu
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		miNew = new JMenuItem("New");
		miLoad = new JMenuItem("Load");
		miSave = new JMenuItem("Save");
		miExit = new JMenuItem("Exit");
		
		miNew.addActionListener(this);
		miLoad.addActionListener(this);
		miSave.addActionListener(this);
		miExit.addActionListener(this);
		
		fileMenu.add(miNew);
		fileMenu.add(miLoad);
		fileMenu.add(miSave);
		fileMenu.addSeparator();
		fileMenu.add(miExit);
		
		menubar.add(fileMenu);
		
		// View menu
		viewMenu = new JMenu("View");
		viewMenu.setMnemonic(KeyEvent.VK_V);
		
		miSettings = new JCheckBoxMenuItem("Settings", false);
		miTravelHistory = new JCheckBoxMenuItem("Travel history", false);
		miCloseAll = new JMenuItem("Close all");
		
		miSettings.addActionListener(this);
		miTravelHistory.addActionListener(this);
		miCloseAll.addActionListener(this);
		
		viewMenu.add(miSettings);
		viewMenu.add(miTravelHistory);
		viewMenu.addSeparator();
		viewMenu.add(miCloseAll);
		
		menubar.add(viewMenu);
		
		// Help menu
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		
		miInfo = new JMenuItem("Info");
		miAbout = new JMenuItem("About program");
		
		miInfo.addActionListener(this);
		miAbout.addActionListener(this);
		
		helpMenu.add(miInfo);
		helpMenu.add(miAbout);
		
		menubar.add(helpMenu);
		
		
		// Settings frame
		settingsFrame = new SettingsFrame(car, "Settings");
		settingsFrame.setSize(settingsFrame.getSize());
		settingsFrame.addWindowListener(new WindowAdapter() {
		  public void windowClosing(WindowEvent we) {
			  miSettings.setSelected(false);
		  }
		});
		
		// Travel history frame
		historyFrame = new TravelHistoryFrame("Travels", car);
		historyFrame.setSize(historyFrame.getSize());
		historyFrame.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent we) {
				  miTravelHistory.setSelected(false);
			  }
			});
		
		// About frame
		aboutFrame = new AboutFrame("About program");
		aboutFrame.setSize(aboutFrame.getSize());
		
		// Info frame
		infoFrame = new InfoFrame("Info");
		infoFrame.setSize(infoFrame.getSize());
		
	}

	/**
	 * Obs³uga zdarzeñ menu.
	 * @param e powsta³e zdarzenie
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == miNew) {
			car.resetSettings();
			car.retrieveSettings(car.getSettings());
		}
		if(e.getSource() == miLoad) {
			JFileChooser fc = new JFileChooser("./bac");
			int ret = fc.showOpenDialog(this);
			if (ret == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				XMLFileManager fm = new XMLFileManager();
				CarSettings backup = new CarSettings(); 
				try {
					backup = (CarSettings) fm.readFromFile(file.getAbsolutePath());
				} catch (ClassNotFoundException | IOException e1) {
					e1.printStackTrace();
				}
				car.retrieveSettings(backup);
				car.applySettings();
			}
			else {
				JOptionPane.showMessageDialog(this, "File not provided.", "Loading file error", JOptionPane.ERROR_MESSAGE);
			};
		}
		if(e.getSource() == miSave) {
			String filename = JOptionPane.showInputDialog("Enter file name for saved settings");
			XMLFileManager fm = new XMLFileManager();
			try {
				if(filename != "" && filename != ".") {
					fm.saveToFile(car.getSettings(), "bac/" + filename + ".xml");					
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if(e.getSource() == miExit) {
		    int option = JOptionPane.showConfirmDialog (null, "Would you like to save changes?","Save on exit", JOptionPane.YES_NO_OPTION);
		    if(option == JOptionPane.YES_OPTION) {
		    	car.applySettings();
		    	car.saveSettings("bac/backup.dat");
		    };
			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		}
		if(e.getSource() == miSettings) {
			settingsFrame.setVisible(miSettings.isSelected());
		}
		if(e.getSource() == miTravelHistory) {
			historyFrame.setVisible(miTravelHistory.isSelected());
		}
		if(e.getSource() == miCloseAll) {
			miSettings.setSelected(false);
			miTravelHistory.setSelected(false);
			settingsFrame.setVisible(false);
			historyFrame.setVisible(false);
		}
		if(e.getSource() == miAbout) {
			aboutFrame.setVisible(!aboutFrame.isVisible());
		}
		if(e.getSource() == miInfo) {
			infoFrame.setVisible(!infoFrame.isVisible());
		}
	}
	
}
