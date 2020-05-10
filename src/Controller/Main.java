package Controller;

import java.awt.EventQueue;

import Interface.MainFrame;

public class Main {

	public static void main(String[] args) {
		if(args.length == 0) {
			
			Car c1 = new Car();
			c1.start();
			
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						MainFrame frame = new MainFrame();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
			for(int i = 0; i < 10000000; i++) {
				System.out.println(i * i);
			}
			
			c1.stop();
			try {
				c1.calculatePeriodRunning();
			} catch (InvalidDateException e) {
				e.printStackTrace();
			}
			
			System.out.println(c1.getStartTime());
			System.out.println(c1.getStopTime());
		}
		else {
			System.out.println("Console");
		}
	}

}
