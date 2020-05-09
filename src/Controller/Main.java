package Controller;

import java.awt.EventQueue;

import Interface.MainFrame;

public class Main {

	public static void main(String[] args) {
		if(args.length == 0) {
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
		}
		else {
			System.out.println("Console");
		}
	}

}
