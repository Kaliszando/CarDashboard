package Controller;

import java.io.IOException;

import javax.swing.SwingUtilities;

import Data.CarSettings;
import Data.XMLFileManager;
import GUI.MainFrame;

/**
 * Klasa uruchamiająca program.
 * 
 * @version 1.0
 * @author Adam Kalisz
 * @author Kamil Rojszczak
 * 
 */
public class Main {

	/**
	 * Uruchamia program w trybie graficznym lub konsolowym.
	 * GUI uruchamiane jest domyślnie.
	 * Podanie parametru '-e' lub '--emergency' uruchamia program w trybie konsolowym.
	 * @param args opcje uruchomienia programu
	 */
	public static void main(String[] args) {			
		if(args.length == 0) {
			
			Car car = new Car();
			
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new MainFrame(car);
				}
			});
		}
		else if(args[0].compareTo("-e") == 0 || args[0].compareTo("--emergency") == 0) {
			Car car = new Car();
			car.start();
			System.out.println(car.getLights().toString());
		}
		else {
			System.out.println("Option '" + args[0] + "' unrecognized");
			System.out.println("-e --emergency to start in console mode");
		}
	}

}
