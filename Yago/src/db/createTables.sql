CREATE SCHEMA DByago;

CREATE TABLE `Actor` (
  `idActor` int(11) NOT NULL,
  `actorName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idActor`)
) ENGINE=InnoDB ;

CREATE TABLE `Director` (
  `idDirector` int(11) NOT NULL,
  `directorName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idDirector`)
) ENGINE=InnoDB ;


CREATE TABLE `Genre` (
  `idGenre` int(11) NOT NULL,
  `genreName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idGenre`)
) ENGINE=InnoDB ;



CREATE TABLE `Language` (
  `idLanguage` int(11) NOT NULL,
  `LanguageName` text,
  PRIMARY KEY (`idLanguage`)
) ENGINE=InnoDB ;



CREATE TABLE `Movie` (
  `idMovie` int(11) NOT NULL,
  `idLanguage` int(11) DEFAULT NULL,
  `idDirector` int(11) DEFAULT NULL,
  `movieName` text,
  `year` varchar(45) DEFAULT NULL,
  `youtube` varchar(45) DEFAULT NULL,
  `wiki` text,
  `duration` varchar(45) DEFAULT NULL,
  `plot` text,
  PRIMARY KEY (`idMovie`),
  KEY `idLanguage_idx` (`idLanguage`),
  KEY `idDirector_idx` (`idDirector`),
  CONSTRAINT `idDirector` FOREIGN KEY (`idDirector`) REFERENCES `Director` (`idDirector`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `idLanguage` FOREIGN KEY (`idLanguage`) REFERENCES `Language` (`idLanguage`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB ;

CREATE TABLE `ActorMovie` (
  `idActor` int(11) NOT NULL,
  `idMovie` int(11) NOT NULL,
  PRIMARY KEY (`idActor`,`idMovie`),
  KEY `idMovie_idx` (`idMovie`),
  KEY `idActor_idx` (`idActor`),
  CONSTRAINT `idActor` FOREIGN KEY (`idActor`) REFERENCES `Actor` (`idActor`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `idMovie` FOREIGN KEY (`idMovie`) REFERENCES `Movie` (`idMovie`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB ;






CREATE TABLE `GenreMovie` (
  `idGenre` int(11) NOT NULL,
  `idMovie` int(11) NOT NULL,
  KEY `idGenre_idx` (`idGenre`),
  KEY `idMovie_idx` (`idMovie`),
  CONSTRAINT `idGenre` FOREIGN KEY (`idGenre`) REFERENCES `Genre` (`idGenre`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `idMovie1` FOREIGN KEY (`idMovie`) REFERENCES `Movie` (`idMovie`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB ;




CREATE TABLE `MoviesGrades` (
  `idMovie` int(11) NOT NULL,
  `grade` double DEFAULT NULL,
  `numberOfRankers` int(11) DEFAULT NULL,
  PRIMARY KEY (`idMovie`)
) ENGINE=InnoDB ;




CREATE TABLE `Updates` (
  `idMovie` int(11) NOT NULL,
  `column` varchar(45) DEFAULT NULL,
  `newVaue` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idMovie`)
) ENGINE=InnoDB ;




CREATE TABLE `Users` (
  `idUsers` int(11) NOT NULL,
  `userName` varchar(45) DEFAULT NULL,
  `userPassword` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idUsers`)
) ENGINE=InnoDB ;




CREATE TABLE `UsersMovies` (
  `idUser` int(11) NOT NULL,
  `idMovie` int(11) NOT NULL,
  `rank` int(11) DEFAULT NULL,
  PRIMARY KEY (`idUser`,`idMovie`),
  KEY `idMovie_idx` (`idMovie`),
  KEY `idUser_idx` (`idUser`),
  CONSTRAINT `idMovie3` FOREIGN KEY (`idMovie`) REFERENCES `Movie` (`idMovie`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `idUser` FOREIGN KEY (`idUser`) REFERENCES `Users` (`idUsers`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB ;


