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
 * Klasa reprezentuj¹ca bazê danych.
 * 
 * Umo¿liwia po³¹czenie programu z baz¹ danych, dodanie lub usuniêcie rekordów podró¿y
 * z bazy danych, usuniêcie pustego wpisu itp.
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
	 * Konstruktor ³¹czy siê z baz¹ danych oraz tworzy obiekt zapytania które zostanie wys³ane do bazy danych.
	 * @throws SQLException wyjatek nieudanego po³¹czenia z baz¹ danych
	 */
	public Database() throws SQLException  {
		connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=JDBC_database;";
		connection = DriverManager.getConnection(connectionUrl, "JDBC" , "Java1234");
		statement = connection.createStatement();    
	}
	
	/**
	 * Dodaje wpis do bazy danych zawieraj¹cy d³ugoœæ, œrednie zu¿ycie paliwa itp.
	 * @param travel obiekt klasy Travel zawieraj¹cy informacje o aktualnej podró¿y
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
	 * @param id numer wpisu ktry chcemy usunaæ
	 * @throws SQLException wyjatek nieudanej operacji na bazie danych
	 */
	public void removeTravel(int id) throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = connection.prepareStatement("DELETE FROM Travels WHERE id=?;");
		pstmt.setInt(1, id);
		pstmt.executeUpdate();
		if (pstmt != null) pstmt.close();
	}
	
	/**
	 * Usuwa z bazy danych wpisy których przebyty dystans wynosi 0.
	 * @throws SQLException wyjatek nieudanej operacji na bazie danych
	 */
	public void removeEmptyTravels() throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = connection.prepareStatement("DELETE FROM Travels WHERE distance='0';");
		pstmt.executeUpdate();
		if (pstmt != null) pstmt.close();
	}
	
	/**
	 * Aktualizuje bazê danych o wpisy z listy.
	 * @param travels lista objektow klasy Travel
	 * @throws SQLException wyjatek nieudanej operacji na bazie danych
	 */
	public void updateTravels(ArrayList<Travel> travels) throws SQLException {
		for(Travel t : travels) addTravel(t);
	}
	
	/**
	 * Pobiera z bazy danych wszystkie wpisy jako listê.
	 * @return lista objektów klasy Travel zawieraj¹ca wpisy z bazy danych
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
