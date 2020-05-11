package Controller;

import javax.swing.SwingUtilities;
import Interface.MainFrame;

public class Main {

	public static void main(String[] args) {
		if(args.length == 0) {
			
			Car car = new Car();
			//car.start();
			
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
