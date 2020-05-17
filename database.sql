CREATE DATABASE JDBC_database

USE JDBC_database

EXEC sp_helpdb @dbname = 'JDBC_database'

CREATE TABLE Travels (
	id					INT PRIMARY KEY,
	distance			FLOAT NOT NULL,
	mileageTotal		FLOAT NOT NULL,
	avgFuelConsumption	FLOAT NOT NULL,
	startDate			SMALLDATETIME NOT NULL,
	endDate				SMALLDATETIME NOT NULL,
)

INSERT INTO Travels
VALUES ('0', '23.4', '143456.4', '4.4', '2020-05-12 12:35', '2020-05-12 12:59');

INSERT INTO Travels
VALUES ('1', '32.4', '132446.4', '6.4', '2020-07-12 12:38:21');

SELECT * FROM Travels

SELECT * FROM Travels
WHERE id LIKE '0';

DROP TABLE Travels