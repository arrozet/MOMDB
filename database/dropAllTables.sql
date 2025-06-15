-- Script to drop all tables from the moviedb database

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

USE `moviedb`;

-- Disable foreign key checks to allow dropping tables without order issues
SET FOREIGN_KEY_CHECKS=0;

-- Drop all tables
DROP TABLE IF EXISTS `Watchlist`;
DROP TABLE IF EXISTS `Review`;
DROP TABLE IF EXISTS `Movie_SpokenLanguage`;
DROP TABLE IF EXISTS `Movie_ProductionCountry`;
DROP TABLE IF EXISTS `Movie_ProductionCompany`;
DROP TABLE IF EXISTS `Movie_Keywords`;
DROP TABLE IF EXISTS `Movie_Genre`;
DROP TABLE IF EXISTS `Favorite`;
DROP TABLE IF EXISTS `CrewCharacter`;
DROP TABLE IF EXISTS `Crew`;
DROP TABLE IF EXISTS `User`;
DROP TABLE IF EXISTS `Person`;
DROP TABLE IF EXISTS `Movie`;
DROP TABLE IF EXISTS `Status`;
DROP TABLE IF EXISTS `CrewRole`;
DROP TABLE IF EXISTS `Character`;
DROP TABLE IF EXISTS `UserRole`;
DROP TABLE IF EXISTS `Genre`;
DROP TABLE IF EXISTS `Keywords`;
DROP TABLE IF EXISTS `ProductionCompany`;
DROP TABLE IF EXISTS `ProductionCountry`;
DROP TABLE IF EXISTS `SpokenLanguage`;

-- Tables with alternative names (lowercase and underscores)
DROP TABLE IF EXISTS `movie_favorite_users`;
DROP TABLE IF EXISTS `movie_genres`;
DROP TABLE IF EXISTS `movie_productioncompanies`;
DROP TABLE IF EXISTS `movie_productioncountries`;
DROP TABLE IF EXISTS `movie_spokenlanguages`;
DROP TABLE IF EXISTS `movie_watchlist_users`;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS=1;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS; 