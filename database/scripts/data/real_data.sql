-- Author: arrozet (Rubén Oliva)
-- MySQL Workbench Forward Engineering (Seed Data Script)

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

USE `moviedb`;

-- Disable foreign key checks to avoid order issues during insertion
SET FOREIGN_KEY_CHECKS=0;

-- Clear existing data (optional, uncomment if needed)
-- DELETE FROM `Watchlist`;
-- DELETE FROM `Review`;
-- DELETE FROM `Movie_SpokenLanguage`;
-- DELETE FROM `Movie_ProductionCountry`;
-- DELETE FROM `Movie_ProductionCompany`;
-- DELETE FROM `Movie_Keywords`;
-- DELETE FROM `Movie_Genre`;
-- DELETE FROM `Favorite`;
-- DELETE FROM `CrewCharacter`;
-- DELETE FROM `Crew`;
-- DELETE FROM `User`;
-- DELETE FROM `Person`;
-- DELETE FROM `Movie`;
-- DELETE FROM `Status`;
-- DELETE FROM `CrewRole`;
-- DELETE FROM `Character`;
-- DELETE FROM `UserRole`;
-- DELETE FROM `Genre`;
-- DELETE FROM `Keywords`;
-- DELETE FROM `ProductionCompany`;
-- DELETE FROM `ProductionCountry`;
-- DELETE FROM `SpokenLanguage`;


-- -----------------------------------------------------
-- Populate `UserRole`
-- -----------------------------------------------------
INSERT INTO `UserRole` (`id`, `name`) VALUES
(1, 'admin'),
(2, 'editor'),
(3, 'analista'),
(4, 'recomendador'),
(5, 'usuario');

-- -----------------------------------------------------
-- Populate `User`
-- -----------------------------------------------------
INSERT INTO `User` (`id`, `username`, `email`, `password`, `profile_pic_link`, `role_id`) VALUES
(1, 'ruben_admin', 'ruben@momdb.com', 'ruben', 'https://www.famousbirthdays.com/faces/sigma-monkey-image.jpg', 1), -- Password should be hashed in a real app
(2, 'artur_editor', 'artur@momdb.com', 'artur', 'https://i1.sndcdn.com/artworks-0p6EFqCQw8e8Ejwb-VDLuNA-t500x500.jpg', 2),
(3, 'edu_analyst', 'edu@momdb.com', 'edu', 'https://forums.footballguys.com/data/avatars/l/0/525.jpg', 3),
(4, 'juanma_rec', 'juanma@momdb.com', 'juanma', 'https://media.tenor.com/j117vqw9JRoAAAAe/fucking-hrt.png', 4),
(5, 'jorge_user', 'jorge@momdb.com', 'jorge', 'https://i.imgflip.com/5hwfl9.jpg', 5);

-- -----------------------------------------------------
-- Clear tables before loading new data
-- -----------------------------------------------------
DELETE FROM `Movie_SpokenLanguage`;
DELETE FROM `Movie_ProductionCountry`;
DELETE FROM `Movie_ProductionCompany`;
DELETE FROM `Movie_Keywords`;
DELETE FROM `Movie_Genre`;
DELETE FROM `CrewCharacter`;
DELETE FROM `Crew`;
DELETE FROM `Movie`;
DELETE FROM `Status`;
DELETE FROM `CrewRole`;
DELETE FROM `Character`;
DELETE FROM `Genre`;
DELETE FROM `Keywords`;
DELETE FROM `ProductionCompany`;
DELETE FROM `ProductionCountry`;
DELETE FROM `SpokenLanguage`;
DELETE FROM `Person`;

-- -----------------------------------------------------
-- Define the path to the CSV files
-- -----------------------------------------------------
-- If 'secure_file_priv' is set to a specific directory, you must place your CSV files there.
-- If it's empty, you can load from anywhere. If it's NULL, LOAD DATA is disabled.
-- Use forward slashes '/' in the path.
SET @data_path = 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/';

-- -----------------------------------------------------
-- Populate tables from CSV files
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Populate `Status`
-- -----------------------------------------------------
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/Status.csv'
INTO TABLE `Status`
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(status_name, id);

-- -----------------------------------------------------
-- Populate `Genre`
-- -----------------------------------------------------
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/Genre.csv'
INTO TABLE `Genre`
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(id, genre);

-- -----------------------------------------------------
-- Populate `Keywords`
-- -----------------------------------------------------
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/Keywords.csv'
INTO TABLE `Keywords`
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(id, keyword);

-- -----------------------------------------------------
-- Populate `ProductionCompany`
-- -----------------------------------------------------
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/ProductionCompany.csv'
INTO TABLE `ProductionCompany`
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(id, company);

-- -----------------------------------------------------
-- Populate `ProductionCountry`
-- -----------------------------------------------------
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/ProductionCountry.csv'
INTO TABLE `ProductionCountry`
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(iso_3166_1, country);

-- -----------------------------------------------------
-- Populate `SpokenLanguage`
-- -----------------------------------------------------
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/SpokenLanguage.csv'
INTO TABLE `SpokenLanguage`
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(iso_639_1, language);

-- -----------------------------------------------------
-- Populate `CrewRole`
-- -----------------------------------------------------
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/CrewRole.csv'
INTO TABLE `CrewRole`
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(id, role);

-- -----------------------------------------------------
-- Populate `Character`
-- -----------------------------------------------------
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/Character.csv'
INTO TABLE `Character`
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(id, `character`);

-- -----------------------------------------------------
-- Populate `Person`
-- -----------------------------------------------------
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/Person.csv'
INTO TABLE `Person`
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(id, name);

-- -----------------------------------------------------
-- Populate `Movie`
-- -----------------------------------------------------
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/Movie.csv'
INTO TABLE `Movie`
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(id, original_title, budget, homepage, original_language, @overview, popularity, release_date, revenue, runtime, tagline, title, vote_average, vote_count, image_link, status_id)
SET overview = IF(@overview IS NULL, '', @overview);

-- -----------------------------------------------------
-- Populate `Crew`
-- -----------------------------------------------------
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/Crew.csv'
INTO TABLE `Crew`
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(crew_id, movie_id, person_id, crewRole_id);

-- -----------------------------------------------------
-- Populate `CrewCharacter`
-- -----------------------------------------------------
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/CrewCharacter.csv'
INTO TABLE `CrewCharacter`
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(crew_id, character_id);

-- -----------------------------------------------------
-- Populate `Movie_Genre`
-- -----------------------------------------------------
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/Movie_Genre.csv'
INTO TABLE `Movie_Genre`
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(movie_id, genre_id);

-- -----------------------------------------------------
-- Populate `Movie_Keywords`
-- -----------------------------------------------------
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/Movie_Keywords.csv'
INTO TABLE `Movie_Keywords`
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(movie_id, keywords_id);

-- -----------------------------------------------------
-- Populate `Movie_ProductionCompany`
-- -----------------------------------------------------
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/Movie_ProductionCompany.csv'
INTO TABLE `Movie_ProductionCompany`
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(movie_id, productionCompany_id);

-- -----------------------------------------------------
-- Populate `Movie_ProductionCountry`
-- -----------------------------------------------------
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/Movie_ProductionCountry.csv'
INTO TABLE `Movie_ProductionCountry`
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(movie_id, productionCountry_id);

-- -----------------------------------------------------
-- Populate `Movie_SpokenLanguage`
-- -----------------------------------------------------
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/Movie_SpokenLanguage.csv'
INTO TABLE `Movie_SpokenLanguage`
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(movie_id, spokenLanguage_id);


-- -----------------------------------------------------
-- Sample data for user interactions (optional)
-- You can uncomment these if you want some initial data for reviews, favorites, etc.
-- -----------------------------------------------------
/*
-- -----------------------------------------------------
-- Populate `Favorite` (Sample User Favorites)
-- -----------------------------------------------------
INSERT INTO `Favorite` (`movie_id`, `user_id`) VALUES
(27205, 1), -- ruben_admin likes Inception
(238, 5), -- jorge_user likes Godfather
(155, 5), -- jorge_user likes Dark Knight
(157336, 2), -- artur_editor likes Interstellar
(278, 3), -- edu_analyst likes Shawshank
(680, 4); -- juanma_rec likes Pulp Fiction

-- -----------------------------------------------------
-- Populate `Watchlist` (Sample User Watchlist)
-- -----------------------------------------------------
INSERT INTO `Watchlist` (`movie_id`, `user_id`) VALUES
(769, 1), -- ruben_admin wants to watch Blade Runner
(550, 5), -- jorge_user wants to watch Fight Club
(603, 5), -- jorge_user wants to watch The Matrix
(129, 2), -- artur_editor wants to watch Spirited Away
(49026, 3), -- edu_analyst wants to watch Parasite
(762, 4); -- juanma_rec wants to watch Goodfellas

-- -----------------------------------------------------
-- Populate `Review` (Sample User Reviews)
-- -----------------------------------------------------
INSERT INTO `Review` (`movie_id`, `user_id`, `content`, `rating`) VALUES
(27205, 5, 'Absolutely mind-bending! One of the best sci-fi films ever made.', 9.5), -- jorge reviews Inception
(238, 1, 'A cinematic masterpiece. Brando is unforgettable.', 10.0),          -- ruben reviews Godfather
(155, 3, 'Heath Ledger\'s Joker is iconic. A perfect superhero movie.', 9.0),  -- edu reviews Dark Knight
(278, 5, 'Moved me to tears. Incredible story of hope.', 9.8);               -- jorge reviews Shawshank
*/

-- Enable foreign key checks back
SET FOREIGN_KEY_CHECKS=1;

SET SQL_MODE=@OLD_SQL_MODE;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;