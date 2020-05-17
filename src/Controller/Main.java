package Controller;

import java.sql.SQLException;

import javax.swing.SwingUtilities;

import Data.Database;
import Interface.MainFrame;

public class Main {

	public static void main(String[] args) {
		if(args.length == 0) {
			
			Car car = new Car();
			car.start();
			
			
			try {
				Database.connectToDatabase();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new MainFrame(car);
				}
			});
			
		}
		else {
			System.out.println("Console");
		}
	}

}
