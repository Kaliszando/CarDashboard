package Data;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

public class Database {

	public static void connectToDatabase() throws SQLException  {
		
		String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=JDBC_database;";
		
//		String username = JOptionPane.showInputDialog("Enter database login");
//		String password = JOptionPane.showInputDialog("Enter database password");

		//Connection connection = DriverManager.getConnection(connectionUrl, username , password);
		Connection connection = DriverManager.getConnection(connectionUrl, "JDBC" , "Java1234");

		Statement statement = connection.createStatement();
		
		ResultSet rs = statement.executeQuery("SELECT * FROM Travels");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0");

	    while (rs.next()) {
	        int id = rs.getInt(1);
	        float distance = rs.getFloat(2);
	        float totalMileage = rs.getFloat(3);
	        float avgFuelConsumption = rs.getFloat(4);
	        LocalDateTime endDate = LocalDateTime.parse(rs.getString(5), formatter);
	        System.out.println(id + " " + distance + " " + totalMileage + " " + avgFuelConsumption + " " + endDate);
	    }
	    
	}
}
