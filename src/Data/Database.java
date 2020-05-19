package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Database {
	
	private String connectionUrl;
	private Statement statement;
	private Connection connection;

	public Database() throws SQLException  {
		
		connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=JDBC_database;";
		connection = DriverManager.getConnection(connectionUrl, "JDBC" , "Java1234");
		statement = connection.createStatement();
	    
	}
	
	public void addTravel(Travel travel) throws SQLException {
		PreparedStatement pstmt = null;
		
		pstmt = connection.prepareStatement("INSERT INTO Travels VALUES (?, ?, ?, ?, ?);");

		pstmt.setFloat(1, travel.getLength());
		pstmt.setFloat(2, travel.getMileage());
		pstmt.setFloat(3, travel.getAvgFuelConsumption());
		pstmt.setString(4, travel.getStartDateString());
		pstmt.setString(5, travel.getEndDateString());
		
		pstmt.executeUpdate();
		
		if (pstmt != null) pstmt.close();
	}
	
	public void removeTravel(int id) throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = connection.prepareStatement("DELETE FROM Travels WHERE id=?;");
		pstmt.setInt(1, id);
		pstmt.executeUpdate();
		if (pstmt != null) pstmt.close();
	}
	
	public void removeEmptyTravels() throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = connection.prepareStatement("DELETE FROM Travels WHERE distance='0';");
		pstmt.executeUpdate();
		if (pstmt != null) pstmt.close();
	}
	
	public void updateTravels(ArrayList<Travel> travels) throws SQLException {
		for(Travel t : travels) addTravel(t);
	}
	
	public ArrayList<Travel> retrieveTravels() throws SQLException {
		ArrayList<Travel> databaseTravels = new ArrayList<Travel>();	
		ResultSet rs = statement.executeQuery("SELECT * FROM Travels");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0");

	    while (rs.next()) {
	        float distance = rs.getFloat(2);
	        float totalMileage = rs.getFloat(3);
	        float avgFuelConsumption = rs.getFloat(4);
	        LocalDateTime startDate = LocalDateTime.parse(rs.getString(5), formatter);
	        LocalDateTime endDate = LocalDateTime.parse(rs.getString(6), formatter);
	        
	        Travel newTravel = new Travel(distance, totalMileage, avgFuelConsumption, startDate);
	        newTravel.setEndDate(endDate);
	        databaseTravels.add(newTravel);
	    }
		
		return databaseTravels;
	}
}
