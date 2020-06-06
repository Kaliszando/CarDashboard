package GUI;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;


/**
 * Okienko dialogowe informacji o programie.
 * Wyœwietla informacje na temat projektu, autorów itp..
 * 
 * @author Adam Kalisz
 *
 */
public class AboutFrame extends JFrame {

	/**
	 * Tworzy okienko dialogowe o programie.
	 * @param title tytu³ okna dialogowego
	 */
	public AboutFrame(String title) {
		super(title);
		setSize(500, 400);
		setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2 - this.getWidth()/2, dim.height/2 - this.getHeight()/2);
		
		JLabel html = new JLabel("<html><h2>Car Dashboard project</h2><br>This project was designed to simulate real car dashboard.<br>"
				+ "Graphics were inspired by real car - Volkswagen Golf III.<br><br>"
				+ "<p style='font-size:11px;'>Subject:</p> <p><i>Programowanie komponentowe 2020</i></p><br>"
				+ "<p style='font-size:11px;'>Authors:</p>"
				+ "224319 Adam Kalisz<br>"
				+ "210305 Kamil Rojszczak</html>");
        html.setHorizontalAlignment(JLabel.CENTER);
	    add(html);
	}
	
}
