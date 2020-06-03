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
WHERE distance='0' OR avgFuelConsumption='0';

DELETE FROM Travels WHERE distance = 0;
DELETE FROM Travels WHERE id = 24;
DELETE FROM Travels WHERE mileageTotal = 0;
DELETE FROM Travels WHERE avgFuelConsumption = 0;

SELECT * FROM Travels
ORDER BY startDate;

SELECT id FROM Travels
WHERE startDate='2020-06-01 23:51:49' AND endDate='2020-06-01 23:51:52';

SELECT MIN(Travels.startDate) FROM Travels;

SELECT * FROM Travels
WHERE id LIKE '0';

DROP TABLE Travels