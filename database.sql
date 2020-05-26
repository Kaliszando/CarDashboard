CREATE DATABASE JDBC_database

USE JDBC_database

EXEC sp_helpdb @dbname = 'JDBC_database'

CREATE TABLE Travels (
	id					INT PRIMARY KEY IDENTITY(0, 1),
	distance			FLOAT NOT NULL,
	mileageTotal		FLOAT NOT NULL,
	avgFuelConsumption	FLOAT NOT NULL,
	startDate			DATETIME NOT NULL,
	endDate				DATETIME NOT NULL,
)

SELECT * FROM Travels
WHERE distance='0';

DELETE FROM Travels WHERE distance = 0;

SELECT * FROM Travels
ORDER BY startDate;

SELECT * FROM Travels
WHERE startDate LIKE MIN(Travels.startDate);

SELECT MIN(Travels.startDate) FROM Travels;

SELECT * FROM Travels
WHERE id LIKE '0';

DROP TABLE Travels