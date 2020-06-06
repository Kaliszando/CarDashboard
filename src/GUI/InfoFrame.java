package GUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Okienko dialogowe z informacjami jak korzystaæ z programu.
 * Wyœwietla informacje o programie, jak uruchomiæ pojazd.
 * Dok³adny opis przycisków steruj¹cych panelem sterowania.
 * 
 * @author Adam Kalisz
 *
 */
public class InfoFrame extends JFrame {
	
	/**
	 * Tworzy informacyjne okienko dialogowe.
	 * @param title tytu³ okna dialogowego
	 */
	public InfoFrame(String title) {
		super(title);
		setSize(700, 600);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2 - this.getWidth()/2, dim.height/2 - this.getHeight()/2);
		setResizable(false);
		
		JLabel html = new JLabel("<html>"
				 + "<h3>Panels in main frame</h3>\r\n" + 
				 "<ul>\r\n" + 
				 "<li>menu bar</li>\r\n" + 
				 "<li>dashboard</li>\r\n" + 
				 "<li>control panel</li>\r\n" + 
				 "</ul>\r\n" + 
				 "<h3>How to use</h3>\r\n" + 
				 "<p>To start the car press <strong>Start</strong> button. In order to accelerate adjust the&nbsp;throttle and change gear.</p>\r\n" + 
				 "<p>You can load or save settings through menu at the top (<em>File &gt; Load / Save).&nbsp;</em></p>\r\n" +
				 "<p>Changes are automatically saved when&nbsp;<strong>Stop</strong> button is pressed.</p>\r\n" + 
				 "<p>The car will not start if the fuel tank is empty. Go to settings and refuel (<em>View &gt; Settings</em>).</p>\r\n" + 
				 "<p>You can browse travel history of a car with <strong>Travel History </strong>window.</p>\r\n" + 
				 "<h3>Steering</h3>\r\n" + 
				 "<p>Press&nbsp;<strong>Keyboard Steering</strong> button to control car with keyboard.</p>\r\n" +
				"<ul style=\"list-style-type: disc;\">\r\n" + 
				"<li>UP / DOWN arrow - accelerate / decelerate</li>\r\n" + 
				"<li>LEFT / RIGHT arrow - toggle left / right blinker</li>\r\n" + 
				"<li>1 key - starts the engine and record travel</li>\r\n" + 
				"<li>2 key - stops the engine, saves settings in backup.dat file</li>\r\n" + 
				"<li>W key - upshift</li>\r\n" + 
				"<li>S key - downshift</li>\r\n" + 
				"<li>L key - toggles low beam lights</li>\r\n" + 
				"<li>P key - toggles parking lights</li>\r\n" + 
				"<li>H key - toggles hazard lights</li>\r\n" + 
				"<li>Q key - toggles high beam lights</li>\r\n" + 
				"<li>E key - blinks high beam lights</li>\r\n" + 
				"<li>R / F key - toggles rear / front fog lights&nbsp;</li>\r\n" + 
				"</ul>"
				+ "</html>");
		html.setHorizontalAlignment(JLabel.CENTER);
	    add(html);
	}
	
}
