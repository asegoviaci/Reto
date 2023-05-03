CREATE database Hito_6_prueba;
use Hito_6_prueba;
CREATE TABLE Photographers2 (
	PhotographerId int,
	Name varchar(100),
	Awarded tinyint(1),
	PRIMARY KEY(PhotographerId)
);
CREATE TABLE Pictures2 (
	PicturesId int,
	Title varchar(100),
	Date date,
	File varchar(100),
	Visits int,
	PhotographerId int,
	PRIMARY KEY(PicturesId)
);
INSERT into Photographers  (PhotographerId, Name, Awarded) VALUES (1,'Ansel Adams',1);
INSERT into Photographers  (PhotographerId, Name, Awarded) VALUES (2,'Mark Rothko',1);
INSERT into Photographers  (PhotographerId, Name, Awarded) VALUES (3,'Van Gogh',1);
INSERT into Pictures (PicturesId , Title , Date, File, Visits, PhotographerId) VALUES (1,'anseal 1', '2023-01-01','ansealdams1.jpg',3);
INSERT into Pictures (PicturesId , Title , Date, File, Visits, PhotographerId) VALUES (1,'anseal 2', '2021-01-01','ansealdams2.jpg',2);
INSERT into Pictures (PicturesId , Title , Date, File, Visits, PhotographerId) VALUES (1,'rothko 1', '2020-01-01','rothko1.jpg',1);
INSERT into Pictures (PicturesId , Title , Date, File, Visits, PhotographerId) VALUES (1,'vangogh 1', '2021-01-01','vangogh1.jpg',4);
INSERT into Pictures (PicturesId , Title , Date, File, Visits, PhotographerId) VALUES (1,'vangogh 2', '2023-01-01','cangogh2.jpg',6);