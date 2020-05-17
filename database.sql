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

INSERT INTO Travels
VALUES ('23.4', '143456.4', '4.4', '2020-05-12 12:35', '2020-05-12 12:59');

INSERT INTO Travels
VALUES ('32.4', '132446.4', '6.4', '2020-07-12 12:38:21', '2020-07-12 13:01:21');

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