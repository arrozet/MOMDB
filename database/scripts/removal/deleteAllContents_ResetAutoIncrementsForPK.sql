-- Author: arrozet (Rubén Oliva)
-- Script to delete all data from moviedb tables

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

USE `moviedb`;

-- Disable foreign key checks to allow deletion in any order
SET FOREIGN_KEY_CHECKS=0;

-- Delete data from all tables
DELETE FROM `Watchlist`;
DELETE FROM `Review`;
DELETE FROM `Movie_SpokenLanguage`;
DELETE FROM `Movie_ProductionCountry`;
DELETE FROM `Movie_ProductionCompany`;
DELETE FROM `Movie_Keywords`;
DELETE FROM `Movie_Genre`;
DELETE FROM `Favorite`;
DELETE FROM `CrewCharacter`;
DELETE FROM `Crew`;
DELETE FROM `User`;
DELETE FROM `Person`;
DELETE FROM `Movie`;
DELETE FROM `Status`;
DELETE FROM `CrewRole`;
DELETE FROM `Character`;
DELETE FROM `UserRole`;
DELETE FROM `Genre`;
DELETE FROM `Keywords`;
DELETE FROM `ProductionCompany`;
DELETE FROM `ProductionCountry`;
DELETE FROM `SpokenLanguage`;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS=1;

-- Optional: Reset auto-increment counters (Uncomment if needed)
ALTER TABLE `Character` AUTO_INCREMENT = 1;
ALTER TABLE `CrewRole` AUTO_INCREMENT = 1;
ALTER TABLE `Movie` AUTO_INCREMENT = 1;
ALTER TABLE `Person` AUTO_INCREMENT = 1;
ALTER TABLE `Crew` AUTO_INCREMENT = 1;
ALTER TABLE `UserRole` AUTO_INCREMENT = 1;
ALTER TABLE `User` AUTO_INCREMENT = 1;
ALTER TABLE `Genre` AUTO_INCREMENT = 1;
ALTER TABLE `Keywords` AUTO_INCREMENT = 1;
ALTER TABLE `ProductionCompany` AUTO_INCREMENT = 1;
-- Note: Tables with non-integer or non-auto-increment PKs don't need resetting (Status, ProductionCountry, SpokenLanguage, etc.)

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;