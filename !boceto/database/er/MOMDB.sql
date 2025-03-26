-- MySQL Script generated based on the provided ERD
-- Tue Mar 26 17:14:42 2025
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema moviedb
-- -----------------------------------------------------
-- Schema name suggestion, replace 'moviedb' if needed
CREATE SCHEMA IF NOT EXISTS `moviedb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;
USE `moviedb` ;

-- -----------------------------------------------------
-- Table `moviedb`.`UserRole`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moviedb`.`UserRole` ;

CREATE TABLE IF NOT EXISTS `moviedb`.`UserRole` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moviedb`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moviedb`.`User` ;

CREATE TABLE IF NOT EXISTS `moviedb`.`User` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `profile_pic` BLOB NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_User_UserRole_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `fk_User_UserRole`
    FOREIGN KEY (`role_id`)
    REFERENCES `moviedb`.`UserRole` (`id`)
    ON DELETE RESTRICT -- Or NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moviedb`.`Status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moviedb`.`Status` ;

CREATE TABLE IF NOT EXISTS `moviedb`.`Status` (
  `id` INT NOT NULL, -- Assuming predefined IDs, no AUTO_INCREMENT
  `status` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `status_UNIQUE` (`status` ASC) VISIBLE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moviedb`.`Movie`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moviedb`.`Movie` ;

CREATE TABLE IF NOT EXISTS `moviedb`.`Movie` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `original_title` VARCHAR(255) NULL,
  `budget` INT NULL, -- Consider BIGINT if budget can exceed 2 billion
  `homepage` VARCHAR(255) NULL,
  `original_language` VARCHAR(255) NULL, -- Consider standard code (e.g., VARCHAR(2) or VARCHAR(5))
  `overview` VARCHAR(2000) NULL, -- Consider TEXT type
  `popularity` DECIMAL(19,4) NULL, -- Adjusted precision based on common usage
  `release_date` DATE NULL,
  `revenue` BIGINT NULL, -- Changed to BIGINT for potentially large values
  `runtime` INT NULL,
  `status_id` INT NOT NULL, -- Renamed from 'status' to avoid keyword conflict and indicate FK
  `tagline` VARCHAR(255) NULL,
  `title` VARCHAR(255) NOT NULL,
  `vote_average` DECIMAL(3,1) NULL, -- Adjusted precision for typical 0.0-10.0 scale
  `vote_count` INT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `homepage_UNIQUE` (`homepage` ASC) VISIBLE,
  INDEX `fk_Movie_Status_idx` (`status_id` ASC) VISIBLE,
  INDEX `idx_title` (`title` ASC) VISIBLE, -- Index for searching by title
  CONSTRAINT `fk_Movie_Status`
    FOREIGN KEY (`status_id`)
    REFERENCES `moviedb`.`Status` (`id`)
    ON DELETE RESTRICT -- Or NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moviedb`.`Review`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moviedb`.`Review` ;

CREATE TABLE IF NOT EXISTS `moviedb`.`Review` (
  `id` INT NOT NULL AUTO_INCREMENT, -- Kept as per diagram, see suggestions
  `movie_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `content` TEXT NULL, -- Changed from VARCHAR(255) to TEXT
  `rating` DOUBLE NULL, -- Diagram shows double(10), simplified to DOUBLE
  PRIMARY KEY (`id`),
  INDEX `fk_Review_Movie_idx` (`movie_id` ASC) VISIBLE,
  INDEX `fk_Review_User_idx` (`user_id` ASC) VISIBLE,
  UNIQUE INDEX `uq_user_movie_review` (`user_id` ASC, `movie_id` ASC) VISIBLE, -- Assuming one review per user per movie
  CONSTRAINT `fk_Review_Movie`
    FOREIGN KEY (`movie_id`)
    REFERENCES `moviedb`.`Movie` (`id`)
    ON DELETE CASCADE -- If movie deleted, delete reviews
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Review_User`
    FOREIGN KEY (`user_id`)
    REFERENCES `moviedb`.`User` (`id`)
    ON DELETE CASCADE -- If user deleted, delete their reviews
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moviedb`.`Favorite`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moviedb`.`Favorite` ;

CREATE TABLE IF NOT EXISTS `moviedb`.`Favorite` (
  `id` INT NOT NULL AUTO_INCREMENT, -- Kept as per diagram, see suggestions
  `movie_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Favorite_Movie_idx` (`movie_id` ASC) VISIBLE,
  INDEX `fk_Favorite_User_idx` (`user_id` ASC) VISIBLE,
  UNIQUE INDEX `uq_user_movie_favorite` (`user_id` ASC, `movie_id` ASC) VISIBLE, -- Primary Key candidate
  CONSTRAINT `fk_Favorite_Movie`
    FOREIGN KEY (`movie_id`)
    REFERENCES `moviedb`.`Movie` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Favorite_User`
    FOREIGN KEY (`user_id`)
    REFERENCES `moviedb`.`User` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moviedb`.`Watchlist`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moviedb`.`Watchlist` ;

CREATE TABLE IF NOT EXISTS `moviedb`.`Watchlist` (
  `id` INT NOT NULL AUTO_INCREMENT, -- Kept as per diagram, see suggestions
  `movie_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Watchlist_Movie_idx` (`movie_id` ASC) VISIBLE,
  INDEX `fk_Watchlist_User_idx` (`user_id` ASC) VISIBLE,
  UNIQUE INDEX `uq_user_movie_watchlist` (`user_id` ASC, `movie_id` ASC) VISIBLE, -- Primary Key candidate
  CONSTRAINT `fk_Watchlist_Movie`
    FOREIGN KEY (`movie_id`)
    REFERENCES `moviedb`.`Movie` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Watchlist_User`
    FOREIGN KEY (`user_id`)
    REFERENCES `moviedb`.`User` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moviedb`.`Keywords`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moviedb`.`Keywords` ;

CREATE TABLE IF NOT EXISTS `moviedb`.`Keywords` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `keyword` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `keyword_UNIQUE` (`keyword` ASC) VISIBLE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moviedb`.`Movie_Keywords`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moviedb`.`Movie_Keywords` ;

CREATE TABLE IF NOT EXISTS `moviedb`.`Movie_Keywords` (
  `movie_id` INT NOT NULL,
  `keywords_id` INT NOT NULL,
  PRIMARY KEY (`movie_id`, `keywords_id`),
  INDEX `fk_Movie_Keywords_Keywords_idx` (`keywords_id` ASC) VISIBLE,
  INDEX `fk_Movie_Keywords_Movie_idx` (`movie_id` ASC) VISIBLE,
  CONSTRAINT `fk_Movie_Keywords_Movie`
    FOREIGN KEY (`movie_id`)
    REFERENCES `moviedb`.`Movie` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Movie_Keywords_Keywords`
    FOREIGN KEY (`keywords_id`)
    REFERENCES `moviedb`.`Keywords` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moviedb`.`Genre`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moviedb`.`Genre` ;

CREATE TABLE IF NOT EXISTS `moviedb`.`Genre` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `genre` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `genre_UNIQUE` (`genre` ASC) VISIBLE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moviedb`.`Movie_Genre`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moviedb`.`Movie_Genre` ;

CREATE TABLE IF NOT EXISTS `moviedb`.`Movie_Genre` (
  `movie_id` INT NOT NULL,
  `genre_id` INT NOT NULL,
  PRIMARY KEY (`movie_id`, `genre_id`),
  INDEX `fk_Movie_Genre_Genre_idx` (`genre_id` ASC) VISIBLE,
  INDEX `fk_Movie_Genre_Movie_idx` (`movie_id` ASC) VISIBLE,
  CONSTRAINT `fk_Movie_Genre_Movie`
    FOREIGN KEY (`movie_id`)
    REFERENCES `moviedb`.`Movie` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Movie_Genre_Genre`
    FOREIGN KEY (`genre_id`)
    REFERENCES `moviedb`.`Genre` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moviedb`.`ProductionCompany`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moviedb`.`ProductionCompany` ;

CREATE TABLE IF NOT EXISTS `moviedb`.`ProductionCompany` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `company` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `company_UNIQUE` (`company` ASC) VISIBLE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moviedb`.`Movie_ProductionCompany`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moviedb`.`Movie_ProductionCompany` ;

CREATE TABLE IF NOT EXISTS `moviedb`.`Movie_ProductionCompany` (
  `movie_id` INT NOT NULL,
  `productionCompany_id` INT NOT NULL,
  PRIMARY KEY (`movie_id`, `productionCompany_id`),
  INDEX `fk_Movie_ProdComp_ProdComp_idx` (`productionCompany_id` ASC) VISIBLE,
  INDEX `fk_Movie_ProdComp_Movie_idx` (`movie_id` ASC) VISIBLE,
  CONSTRAINT `fk_Movie_ProdComp_Movie`
    FOREIGN KEY (`movie_id`)
    REFERENCES `moviedb`.`Movie` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Movie_ProdComp_ProdComp`
    FOREIGN KEY (`productionCompany_id`)
    REFERENCES `moviedb`.`ProductionCompany` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moviedb`.`ProductionCountry`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moviedb`.`ProductionCountry` ;

CREATE TABLE IF NOT EXISTS `moviedb`.`ProductionCountry` (
  `iso_3166_1` VARCHAR(5) NOT NULL, -- As per the note
  `country` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`iso_3166_1`),
  UNIQUE INDEX `country_UNIQUE` (`country` ASC) VISIBLE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moviedb`.`Movie_ProductionCountry`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moviedb`.`Movie_ProductionCountry` ;

CREATE TABLE IF NOT EXISTS `moviedb`.`Movie_ProductionCountry` (
  `movie_id` INT NOT NULL,
  `productionCountry_id` VARCHAR(5) NOT NULL, -- Matches ProductionCountry PK type
  PRIMARY KEY (`movie_id`, `productionCountry_id`),
  INDEX `fk_Movie_ProdCountry_ProdCountry_idx` (`productionCountry_id` ASC) VISIBLE,
  INDEX `fk_Movie_ProdCountry_Movie_idx` (`movie_id` ASC) VISIBLE,
  CONSTRAINT `fk_Movie_ProdCountry_Movie`
    FOREIGN KEY (`movie_id`)
    REFERENCES `moviedb`.`Movie` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Movie_ProdCountry_ProdCountry`
    FOREIGN KEY (`productionCountry_id`)
    REFERENCES `moviedb`.`ProductionCountry` (`iso_3166_1`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moviedb`.`SpokenLanguage`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moviedb`.`SpokenLanguage` ;

CREATE TABLE IF NOT EXISTS `moviedb`.`SpokenLanguage` (
  `iso_639_1` VARCHAR(5) NOT NULL, -- As per the note
  `language` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`iso_639_1`),
  UNIQUE INDEX `language_UNIQUE` (`language` ASC) VISIBLE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moviedb`.`Movie_SpokenLanguage`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moviedb`.`Movie_SpokenLanguage` ;

CREATE TABLE IF NOT EXISTS `moviedb`.`Movie_SpokenLanguage` (
  `movie_id` INT NOT NULL,
  `spokenLanguage_id` VARCHAR(5) NOT NULL, -- Matches SpokenLanguage PK type
  PRIMARY KEY (`movie_id`, `spokenLanguage_id`),
  INDEX `fk_Movie_SpokenLang_SpokenLang_idx` (`spokenLanguage_id` ASC) VISIBLE,
  INDEX `fk_Movie_SpokenLang_Movie_idx` (`movie_id` ASC) VISIBLE,
  CONSTRAINT `fk_Movie_SpokenLang_Movie`
    FOREIGN KEY (`movie_id`)
    REFERENCES `moviedb`.`Movie` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Movie_SpokenLang_SpokenLang`
    FOREIGN KEY (`spokenLanguage_id`)
    REFERENCES `moviedb`.`SpokenLanguage` (`iso_639_1`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moviedb`.`Person`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moviedb`.`Person` ;

CREATE TABLE IF NOT EXISTS `moviedb`.`Person` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moviedb`.`CrewRole`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moviedb`.`CrewRole` ;

CREATE TABLE IF NOT EXISTS `moviedb`.`CrewRole` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `role_UNIQUE` (`role` ASC) VISIBLE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moviedb`.`Crew`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moviedb`.`Crew` ;

CREATE TABLE IF NOT EXISTS `moviedb`.`Crew` (
  `crew_id` INT NOT NULL AUTO_INCREMENT, -- Surrogate key as per diagram
  `movie_id` INT NOT NULL,
  `person_id` INT NOT NULL,
  `crewRole_id` INT NOT NULL,
  -- `department` VARCHAR(255) NULL, -- This seems missing from your Crew table ERD? Added based on example script's structure, remove if not needed.
  PRIMARY KEY (`crew_id`),
  INDEX `fk_Crew_Movie_idx` (`movie_id` ASC) VISIBLE,
  INDEX `fk_Crew_Person_idx` (`person_id` ASC) VISIBLE,
  INDEX `fk_Crew_CrewRole_idx` (`crewRole_id` ASC) VISIBLE,
   UNIQUE INDEX `uq_movie_person_role` (`movie_id` ASC, `person_id` ASC, `crewRole_id` ASC) VISIBLE, -- Ensures a person doesn't have the same role multiple times in one movie via this table.
  CONSTRAINT `fk_Crew_Movie`
    FOREIGN KEY (`movie_id`)
    REFERENCES `moviedb`.`Movie` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Crew_Person`
    FOREIGN KEY (`person_id`)
    REFERENCES `moviedb`.`Person` (`id`)
    ON DELETE CASCADE -- If person deleted, remove their crew entries? Or RESTRICT?
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Crew_CrewRole`
    FOREIGN KEY (`crewRole_id`)
    REFERENCES `moviedb`.`CrewRole` (`id`)
    ON DELETE RESTRICT -- Don't delete a role if it's in use
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moviedb`.`Character`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moviedb`.`Character` ;

CREATE TABLE IF NOT EXISTS `moviedb`.`Character` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `character` VARCHAR(255) NOT NULL, -- Assuming character name
  PRIMARY KEY (`id`),
  UNIQUE INDEX `character_UNIQUE` (`character` ASC) VISIBLE) -- Should character name be unique globally? Maybe not. Consider removing.
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moviedb`.`CrewCharacter`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moviedb`.`CrewCharacter` ;

CREATE TABLE IF NOT EXISTS `moviedb`.`CrewCharacter` (
  `crew_id` INT NOT NULL,
  `character_id` INT NOT NULL,
  PRIMARY KEY (`crew_id`, `character_id`),
  INDEX `fk_CrewCharacter_Character_idx` (`character_id` ASC) VISIBLE,
  INDEX `fk_CrewCharacter_Crew_idx` (`crew_id` ASC) VISIBLE,
  CONSTRAINT `fk_CrewCharacter_Crew`
    FOREIGN KEY (`crew_id`)
    REFERENCES `moviedb`.`Crew` (`crew_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_CrewCharacter_Character`
    FOREIGN KEY (`character_id`)
    REFERENCES `moviedb`.`Character` (`id`)
    ON DELETE CASCADE -- If character is deleted (unlikely?), remove link
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;