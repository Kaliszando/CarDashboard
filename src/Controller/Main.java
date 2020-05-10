package Controller;

import java.awt.EventQueue;

import Interface.MainFrame;

public class Main {

	public static void main(String[] args) {
		if(args.length == 0) {
			
			Car c1 = new Car();
			System.out.println(c1.getLights().toString());
			
			c1.getLights().toggleLowBeamLights();
			System.out.println(c1.getLights().toString());
			
			c1.getLights().toggleRunningLights();
			System.out.println(c1.getLights().toString());
			
//			EventQueue.invokeLater(new Runnable() {
//				public void run() {
//					try {
//						MainFrame frame = new MainFrame();
//						frame.setVisible(true);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			});
		
			
			
			
			
			
			
			
			
			
			
		}
		else {
			System.out.println("Console");
		}
	}

}
