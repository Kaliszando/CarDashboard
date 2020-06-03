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

/**
 * Klasa reprezentuj�ca baz� danych.
 * 
 * Umo�liwia po��czenie programu z baz� danych, dodanie lub usuni�cie rekord�w podr�y
 * z bazy danych, usuni�cie pustego wpisu itp.
 * 
 * @version 1.0
 * @author Adam Kalisz
 * @author Kamil Rojszczak
 * 
 */
public class Database {
	
	private String connectionUrl;
	private Statement statement;
	private Connection connection;

	/**
	 * Konstruktor ��czy si� z baz� danych oraz tworzy obiekt zapytania kt�re zostanie wys�ane do bazy danych.
	 * @throws SQLException wyjatek nieudanego po��czenia z baz� danych
	 */
	public Database() throws SQLException  {
		connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=JDBC_database;";
		connection = DriverManager.getConnection(connectionUrl, "JDBC" , "Java1234");
		statement = connection.createStatement();    
	}
	
	/**
	 * Dodaje wpis do bazy danych zawieraj�cy d�ugo��, �rednie zu�ycie paliwa itp.
	 * @param travel obiekt klasy Travel zawieraj�cy informacje o aktualnej podr�y
	 * @throws SQLException wyjatek nieudanej operacji na bazie danych
	 */
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
	
	/**
	 * Usuwa wpis od podanym identyfikatorze.
	 * @param id numer wpisu ktry chcemy usuna�
	 * @throws SQLException wyjatek nieudanej operacji na bazie danych
	 */
	public void removeTravel(int id) throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = connection.prepareStatement("DELETE FROM Travels WHERE id=?;");
		pstmt.setInt(1, id);
		pstmt.executeUpdate();
		if (pstmt != null) pstmt.close();
	}

	public int getTravelId(String start, String stop) {
		try {
			ResultSet rs = statement.executeQuery("SELECT id FROM Travels WHERE startDate='" + start + "' AND endDate='" + stop + "';");
			int id = -1;
			while (rs.next()) {
				id = rs.getInt(1);
			}
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	/**
	 * Usuwa z bazy danych wpisy kt�rych przebyty dystans lub �rednie spalanie wynosi 0.
	 * Historia podr�y przechowuje wszystkie uruchchomienia pojazdu, nawet gdy dystans r�wny jest zeru.
	 * Mo�liwo�� usuni�cia takich wpis�w pozostaje w r�kach u�ytkownika.
	 * @throws SQLException wyjatek nieudanej operacji na bazie danych
	 */
	public void removeEmptyTravels() throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = connection.prepareStatement("DELETE FROM Travels WHERE distance='0' OR avgFuelConsumption='0';");
		pstmt.executeUpdate();
		if (pstmt != null) pstmt.close();
	}
	
	/**
	 * Aktualizuje baz� danych o wpisy z listy.
	 * @param travels lista objektow klasy Travel
	 * @throws SQLException wyjatek nieudanej operacji na bazie danych
	 */
	public void updateTravels(ArrayList<Travel> travels) throws SQLException {
		for(Travel t : travels) addTravel(t);
	}
	
	/**
	 * Pobiera z bazy danych wszystkie wpisy jako list�.
	 * @return lista objekt�w klasy Travel zawieraj�ca wpisy z bazy danych
	 * @throws SQLException wyjatek nieudanej operacji na bazie danych
	 */
	public ArrayList<Travel> retrieveTravels() throws SQLException {
		ArrayList<Travel> databaseTravels = new ArrayList<Travel>();	
		ResultSet rs = statement.executeQuery("SELECT * FROM Travels ORDER BY startDate DESC");
		
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
