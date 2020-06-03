package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Controller.Car;
import Data.Travel;

/**
 * Klasa reprezentuje okienko dialogowe historii podró¿y.
 * 
 * @version 1.0
 * @author Adam Kalisz
 * @author Kamil Rojszczak
 * 
 */
public class TravelHistoryFrame extends JFrame implements ActionListener {

	private JButton JBupdate, JBremoveEmpty, JBremoveSelected;
	private ArrayList<Travel> travels = null;
	private Car car;
	private DefaultTableModel model;
	private DecimalFormat df3, df2, df1;
	private JTable jtable;
	
	/**
	 * Tworzy okno podró¿y oraz dodaje rekordy zapisane w bazie danych.
	 * @param title tytu³ okienka dialogowego
	 * @param car obiekt klasy Car
	 */
	public TravelHistoryFrame(String title, Car car) {
		super(title);
		setLayout(new BorderLayout());
		setSize(620, 300);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2 - this.getWidth()/2, dim.height/2 - this.getHeight()/2);
		this.car = car;
		
		df3 = new DecimalFormat("0.###");
		df2 = new DecimalFormat("0.##");
		df1 = new DecimalFormat("0.#");
		
		// Buttons
		JBupdate = new JButton("Update table");
		JBremoveEmpty = new JButton("Remove empty rows");
		JBremoveSelected = new JButton("Remove selected row");
		
		JBupdate.addActionListener(this);
		JBremoveEmpty.addActionListener(this);
		JBremoveSelected.addActionListener(this);
		
		JPanel jp = new JPanel();
		jp.add(JBupdate);
		jp.add(JBremoveEmpty);
		jp.add(JBremoveSelected);
		add(jp, BorderLayout.NORTH);
		
		// Table		
		jtable = new JTable();
		Object[] columns = {"Distance [km]","Total mileage [km]","Fuel consumption [l/h]","Start time","Stop time"};
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		jtable.setModel(model);
		
		JScrollPane jscroll = new JScrollPane(jtable);
		add(jscroll);
		updateTable();
		
		jtable.getColumnModel().getColumn(0).setPreferredWidth(90);
		jtable.getColumnModel().getColumn(1).setPreferredWidth(110);
		jtable.getColumnModel().getColumn(2).setPreferredWidth(140);
		jtable.getColumnModel().getColumn(3).setPreferredWidth(140);
		jtable.getColumnModel().getColumn(4).setPreferredWidth(140);
		
	}
	
	// TODO
	/**
	 * Aktualizuje tabelê historii podró¿y z bazy danych.
	 */
	public void updateTable() {
		try {
			travels = car.getDb().retrieveTravels();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int count = model.getRowCount();
		if(count > 0) {
			for(int i = count - 1; i >= 0; i--) model.removeRow(i);			
		}
		
		for(Travel t : travels) {
			Object[] row = new Object[5];
			row[0] = df3.format(t.getLength());
			row[1] = df2.format(t.getMileage());
			row[2] = df1.format(t.getAvgFuelConsumption());
			row[3] = t.getStartDateString();
			row[4] = t.getEndDateString();
			model.addRow(row);
		}
	}
	
	//TODO
	/**
	 * Obs³uga zdarzeñ przycisków i wywo³anie metod im przypisanym.
	 * @param e powsta³e zdarzenie
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == JBupdate) {
			updateTable();
		}
		if(e.getSource() == JBremoveEmpty) {
			try {
				car.getDb().removeEmptyTravels();
				updateTable();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		if(e.getSource()  == JBremoveSelected) {
			int i = jtable.getSelectedRow();
			if(i >= 0) {
				int id = car.getDb().getTravelId(model.getValueAt(i, 3).toString(), model.getValueAt(i, 4).toString());
				model.removeRow(i);
				try {
					car.getDb().removeTravel(id);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				updateTable();
			}
		}
	}

}
