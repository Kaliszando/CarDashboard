package Interface;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;
import java.awt.event.*;

public class DashboardPanel extends JPanel {
	
	private JLabel dashboard, bigGuageL, bigGuageR;
	private int squareX = 50;
	private int squareY = 50;
	private int squareW = 20;
	private int squareH = 20;
	
	public DashboardPanel() {
		setLayout(null);
		
		setBorder(BorderFactory.createLineBorder(Color.RED));
		
		dashboard = new JLabel(new ImageIcon("img/dashboard.png"));
		bigGuageL = new JLabel(new ImageIcon("img/bigGuage.png"));
		bigGuageR = new JLabel(new ImageIcon("img/bigGuage.png"));
		
		bigGuageL.setBounds(236, 255, 127, 101);
		bigGuageR.setBounds(568, 255, 127, 101);
		dashboard.setBounds(0, 0, 1000, 515);		
		
		add(bigGuageR);
		add(bigGuageL);
		add(dashboard);
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				moveSquare(e.getX(), e.getY());
			}
		});
		
		addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				moveSquare(e.getX(), e.getY());
			}
		});
	}
	
	public void moveSquare(int x, int y) {
		int OFFSET = 1;
		if(squareX != x || squareY != y) {
			repaint(squareX, squareY, squareW + OFFSET, squareH + OFFSET);
			squareX = x;
			squareY = y;
			repaint(squareX, squareY, squareW + OFFSET, squareH + OFFSET);
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString("This is my custom panel", 10, 10);
		g.setColor(Color.RED);
		g.fillRect(squareX, squareY, squareW, squareH);
		//g.setColor(Color.BLACK);
		//g.fillRect(squareX, squareY, squareW, squareH);
	}
}
