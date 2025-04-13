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
INSERT INTO `User` (`id`, `username`, `email`, `password`, `profile_pic`, `role_id`) VALUES
(1, 'ruben_admin', 'ruben@momdb.com', 'ruben', NULL, 1), -- Password should be hashed in a real app
(2, 'artur_editor', 'artur@momdb.com', 'artur', NULL, 2),
(3, 'edu_analyst', 'edu@momdb.com', 'edu', NULL, 3),
(4, 'juanma_rec', 'juanma@momdb.com', 'juanma', NULL, 4),
(5, 'jorge_user', 'jorge@momdb.com', 'jorge', NULL, 5);

-- -----------------------------------------------------
-- Populate `Status`
-- -----------------------------------------------------
INSERT INTO `Status` (`id`, `status_name`) VALUES
(1, 'Released'),
(2, 'Post Production'),
(3, 'In Production'),
(4, 'Planned'),
(5, 'Rumored');

-- -----------------------------------------------------
-- Populate `Genre`
-- -----------------------------------------------------
INSERT INTO `Genre` (`id`, `genre`) VALUES
(1, 'Science Fiction'),
(2, 'Drama'),
(3, 'Action'),
(4, 'Thriller'),
(5, 'Crime'),
(6, 'Adventure'),
(7, 'Fantasy'),
(8, 'Animation'),
(9, 'Mystery');

-- -----------------------------------------------------
-- Populate `Keywords`
-- -----------------------------------------------------
INSERT INTO `Keywords` (`id`, `keyword`) VALUES
(1, 'time travel'),
(2, 'dystopia'),
(3, 'space opera'),
(4, 'artificial intelligence'),
(5, 'mafia'),
(6, 'heist'),
(7, 'superhero'),
(8, 'based on novel'),
(9, 'dream'),
(10, 'philosophical');

-- -----------------------------------------------------
-- Populate `ProductionCompany`
-- -----------------------------------------------------
INSERT INTO `ProductionCompany` (`id`, `company`) VALUES
(1, 'Paramount Pictures'),
(2, 'Warner Bros. Pictures'),
(3, 'Legendary Pictures'),
(4, 'Syncopy'),
(5, 'Universal Pictures'),
(6, 'Columbia Pictures'),
(7, 'Studio Ghibli'),
(8, 'Miramax');

-- -----------------------------------------------------
-- Populate `ProductionCountry`
-- -----------------------------------------------------
INSERT INTO `ProductionCountry` (`iso_3166_1`, `country`) VALUES
('US', 'United States of America'),
('GB', 'United Kingdom'),
('JP', 'Japan'),
('FR', 'France'),
('DE', 'Germany'),
('KR', 'South Korea');

-- -----------------------------------------------------
-- Populate `SpokenLanguage`
-- -----------------------------------------------------
INSERT INTO `SpokenLanguage` (`iso_639_1`, `language`) VALUES
('en', 'English'),
('es', 'Español'),
('fr', 'Français'),
('ja', '日本語'),
('it', 'Italiano'),
('ko', '한국어/조선말');

-- -----------------------------------------------------
-- Populate `CrewRole`
-- -----------------------------------------------------
INSERT INTO `CrewRole` (`id`, `role`) VALUES
(1, 'Director'),
(2, 'Actor'),
(3, 'Producer'),
(4, 'Writer'),
(5, 'Screenplay'),
(6, 'Director of Photography'),
(7, 'Composer'),
(8, 'Editor');

-- -----------------------------------------------------
-- Populate `Character` (Sample Characters)
-- -----------------------------------------------------
INSERT INTO `Character` (`id`, `character`) VALUES
(1, 'Dominic Cobb'),
(2, 'Arthur'),
(3, 'Ariadne'),
(4, 'Vito Corleone'),
(5, 'Michael Corleone'),
(6, 'Cooper'),
(7, 'Brand'),
(8, 'Bruce Wayne / Batman'),
(9, 'The Joker'),
(10, 'Rick Deckard'),
(11, 'Roy Batty'),
(12, 'Vincent Vega'),
(13, 'Jules Winnfield'),
(14, 'Tyler Durden'),
(15, 'Narrator'),
(16, 'Forrest Gump'),
(17, 'Neo'),
(18, 'Andy Dufresne'),
(19, 'Ellis Boyd Redding'),
(20, 'Colonel Hans Landa');


-- -----------------------------------------------------
-- Populate `Person` (Sample Directors, Actors, etc.)
-- -----------------------------------------------------
INSERT INTO `Person` (`id`, `name`) VALUES
(1, 'Christopher Nolan'),
(2, 'Leonardo DiCaprio'),
(3, 'Joseph Gordon-Levitt'),
(4, 'Elliot Page'),
(5, 'Francis Ford Coppola'),
(6, 'Marlon Brando'),
(7, 'Al Pacino'),
(8, 'Matthew McConaughey'),
(9, 'Anne Hathaway'),
(10, 'Jessica Chastain'),
(11, 'Christian Bale'),
(12, 'Heath Ledger'),
(13, 'Aaron Eckhart'),
(14, 'Ridley Scott'),
(15, 'Harrison Ford'),
(16, 'Rutger Hauer'),
(17, 'Sean Young'),
(18, 'Quentin Tarantino'),
(19, 'John Travolta'),
(20, 'Samuel L. Jackson'),
(21, 'Uma Thurman'),
(22, 'David Fincher'),
(23, 'Brad Pitt'),
(24, 'Edward Norton'),
(25, 'Helena Bonham Carter'),
(26, 'Robert Zemeckis'),
(27, 'Tom Hanks'),
(28, 'Robin Wright'),
(29, 'Lana Wachowski'),
(30, 'Lilly Wachowski'),
(31, 'Keanu Reeves'),
(32, 'Laurence Fishburne'),
(33, 'Carrie-Anne Moss'),
(34, 'Frank Darabont'),
(35, 'Tim Robbins'),
(36, 'Morgan Freeman'),
(37, 'Hans Zimmer'), -- Composer
(38, 'Wally Pfister'), -- DP
(39, 'Lee Smith'); -- Editor

-- -----------------------------------------------------
-- Populate `Movie` (15 Movies)
-- -----------------------------------------------------

INSERT INTO `Movie` (`id`, `original_title`, `budget`, `homepage`, `original_language`, `overview`, `popularity`, `release_date`, `revenue`, `runtime`, `status_id`, `tagline`, `title`, `vote_average`, `vote_count`) VALUES
(1, 'Inception', 160000000, 'https://www.warnerbros.com/movies/inception/', 'en', 'Cobb, a skilled thief who commits corporate espionage by infiltrating the subconscious of his targets, is offered a chance to regain his old life as payment for a task considered to be impossible: "inception", the implantation of another person\'s idea into a target\'s subconscious.', 167.58, '2010-07-15', 829895144, 148, 1, 'Your mind is the scene of the crime.', 'Inception', 8.3, 33600),
(2, 'The Godfather', 6000000, 'https://www.paramountmovies.com/movies/the-godfather', 'en', 'Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family. When organized crime family patriarch, Vito Corleone barely survives an attempt on his life, his youngest son, Michael steps in to take care of the would-be killers, launching a campaign of bloody revenge.', 105.73, '1972-03-14', 245066411, 175, 1, 'An offer you can\'t refuse.', 'The Godfather', 8.7, 17800),
(3, 'Interstellar', 165000000, 'https://www.paramountmovies.com/movies/interstellar', 'en', 'The adventures of a group of explorers who make use of a newly discovered wormhole to surpass the limitations on human space travel and conquer the vast distances involved in an interstellar voyage.', 183.91, '2014-11-05', 677471339, 169, 1, 'Mankind was born on Earth. It was never meant to die here.', 'Interstellar', 8.4, 30100),
(4, 'The Dark Knight', 185000000, 'https://www.warnerbros.com/movies/dark-knight/', 'en', 'Batman raises the stakes in his war on crime. With the help of Lt. Jim Gordon and District Attorney Harvey Dent, Batman sets out to dismantle the remaining criminal organizations that plague the streets. The partnership proves to be effective, but they soon find themselves prey to a reign of chaos unleashed by a rising criminal mastermind known to the terrified citizens of Gotham as the Joker.', 180.39, '2008-07-16', 1004558444, 152, 1, 'Why So Serious?', 'The Dark Knight', 8.5, 29700),
(5, 'Blade Runner', 28000000, 'https://www.warnerbros.com/movies/blade-runner', 'en', 'In the smog-choked dystopian Los Angeles of 2019, blade runner Rick Deckard is called out of retirement to terminate a quartet of replicants who have escaped to Earth seeking their creator.', 89.21, '1982-06-25', 33800000, 117, 1, 'Man has made his match... now it\'s his problem.', 'Blade Runner', 7.9, 12300),
(6, 'Pulp Fiction', 8000000, 'https://www.miramax.com/movie/pulp-fiction/', 'en', 'A burger-loving hit man, his philosophical partner, a drug-addled gangster\'s moll and a washed-up boxer converge in this sprawling, comedic crime caper. Their adventures unfurl in three stories that ingeniously trip back and forth in time.', 95.34, '1994-09-10', 213928762, 154, 1, 'You won\'t know the facts until you\'ve seen the fiction.', 'Pulp Fiction', 8.5, 25000),
(7, 'Fight Club', 63000000, 'https://www.20thcenturystudios.com/movies/fight-club', 'en', 'A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground "fight clubs" forming in every town, until somebody happens to break the first rule.', 101.63, '1999-10-15', 100853753, 139, 1, 'Mischief. Mayhem. Soap.', 'Fight Club', 8.4, 26400),
(8, 'Forrest Gump', 55000000, 'https://www.paramountmovies.com/movies/forrest-gump', 'en', 'A man with a low IQ has accomplished great things in his life and been present during significant historical events—in each case, far exceeding what anyone imagined he could do. But despite all he has achieved, his one true love eludes him.', 98.77, '1994-07-06', 677945399, 142, 1, 'Life is like a box of chocolates...you never know what you\'re gonna get.', 'Forrest Gump', 8.5, 24500),
(9, 'The Matrix', 63000000, 'https://www.warnerbros.com/movies/matrix', 'en', 'Set in the 22nd century, The Matrix tells the story of a computer hacker who joins a group of underground insurgents fighting the vast and powerful computers who now rule the earth.', 110.12, '1999-03-30', 463517383, 136, 1, 'Welcome to the Real World.', 'The Matrix', 8.2, 23000),
(10, 'The Shawshank Redemption', 25000000, 'https://www.warnerbros.com/movies/shawshank-redemption', 'en', 'Framed in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.', 115.96, '1994-09-23', 28341469, 142, 1, 'Fear can hold you prisoner. Hope can set you free.', 'The Shawshank Redemption', 8.7, 23500),
(11, 'Spirited Away', 19000000, 'https://www.ghibli.jp/works/chihiro/#frame', 'ja', 'A young girl, Chihiro, becomes trapped in a strange new world of spirits. When her parents undergo a mysterious transformation, she must call upon the courage she never knew she had to free her family.', 91.88, '2001-07-20', 355760000, 125, 1, 'The Tale of Chihiro and Haku', 'Spirited Away', 8.5, 14100),
(12, 'Parasite', 11400000, 'https://www.parasite-movie.com/', 'ko', 'All unemployed, Ki-taek\'s family takes peculiar interest in the wealthy and glamorous Parks for their livelihood until they get entangled in an unexpected incident.', 78.65, '2019-05-30', 258760000, 132, 1, 'Act like you own the place.', 'Parasite', 8.5, 15600),
(13, 'The Lord of the Rings: The Return of the King', 94000000, 'https://www.warnerbros.com/movies/lord-rings-return-king/', 'en', 'Aragorn is revealed as the heir to the ancient kings as he, Gandalf and the other members of the broken fellowship struggle to save Gondor from Sauron\'s forces. Meanwhile, Frodo and Sam take the Ring closer to the fires of Mount Doom.', 135.23, '2003-12-01', 1118888979, 201, 1, 'The eye of the enemy is moving.', 'The Lord of the Rings: The Return of the King', 8.5, 21500),
(14, 'Inglourious Basterds', 70000000, 'https://www.focusfeatures.com/inglourious_basterds', 'en', 'In Nazi-occupied France during World War II, a plan to assassinate Nazi leaders by a group of Jewish U.S. soldiers coincides with a theatre owner\'s vengeful plans for the same.', 88.97, '2009-08-19', 321455689, 153, 1, 'Once upon a time in Nazi occupied France...', 'Inglourious Basterds', 8.2, 20100),
(15, 'Goodfellas', 25000000, 'https://www.warnerbros.com/movies/goodfellas', 'en', 'The true story of Henry Hill, a half-Irish, half-Sicilian Brooklyn kid who is adopted by neighbourhood gangsters at an early age and climbs the ranks of a Mafia family under the guidance of Jimmy Conway.', 75.43, '1990-09-12', 46836394, 145, 1, 'Three Decades of Life in the Mafia.', 'Goodfellas', 8.5, 11100);

-- -----------------------------------------------------
-- Populate `Crew` (Associating People with Movies and Roles)
-- Assumes Movie IDs 1-15, Person IDs 1-39, CrewRole IDs 1-8
-- -----------------------------------------------------
-- Inception (Movie ID: 1)
INSERT INTO `Crew` (`movie_id`, `person_id`, `crewRole_id`) VALUES
(1, 1, 1),  -- Christopher Nolan (Director)
(1, 1, 3),  -- Christopher Nolan (Producer)
(1, 1, 4),  -- Christopher Nolan (Writer)
(1, 2, 2),  -- Leonardo DiCaprio (Actor) - Crew ID will be 4
(1, 3, 2),  -- Joseph Gordon-Levitt (Actor) - Crew ID will be 5
(1, 4, 2),  -- Elliot Page (Actor) - Crew ID will be 6
(1, 37, 7), -- Hans Zimmer (Composer)
(1, 38, 6), -- Wally Pfister (DP)
(1, 39, 8); -- Lee Smith (Editor)

-- The Godfather (Movie ID: 2)
INSERT INTO `Crew` (`movie_id`, `person_id`, `crewRole_id`) VALUES
(2, 5, 1),  -- Francis Ford Coppola (Director)
(2, 5, 5),  -- Francis Ford Coppola (Screenplay)
(2, 6, 2),  -- Marlon Brando (Actor) - Crew ID will be 12
(2, 7, 2);  -- Al Pacino (Actor) - Crew ID will be 13

-- Interstellar (Movie ID: 3)
INSERT INTO `Crew` (`movie_id`, `person_id`, `crewRole_id`) VALUES
(3, 1, 1),  -- Christopher Nolan (Director)
(3, 1, 4),  -- Christopher Nolan (Writer)
(3, 8, 2),  -- Matthew McConaughey (Actor) - Crew ID will be 16
(3, 9, 2),  -- Anne Hathaway (Actor) - Crew ID will be 17
(3, 10, 2), -- Jessica Chastain (Actor) - Crew ID will be 18
(3, 37, 7); -- Hans Zimmer (Composer)

-- The Dark Knight (Movie ID: 4)
INSERT INTO `Crew` (`movie_id`, `person_id`, `crewRole_id`) VALUES
(4, 1, 1),  -- Christopher Nolan (Director)
(4, 1, 5),  -- Christopher Nolan (Screenplay)
(4, 11, 2), -- Christian Bale (Actor) - Crew ID will be 21
(4, 12, 2), -- Heath Ledger (Actor) - Crew ID will be 22
(4, 13, 2), -- Aaron Eckhart (Actor)
(4, 37, 7), -- Hans Zimmer (Composer)
(4, 38, 6), -- Wally Pfister (DP)
(4, 39, 8); -- Lee Smith (Editor)

-- Blade Runner (Movie ID: 5)
INSERT INTO `Crew` (`movie_id`, `person_id`, `crewRole_id`) VALUES
(5, 14, 1), -- Ridley Scott (Director)
(5, 15, 2), -- Harrison Ford (Actor) - Crew ID will be 28
(5, 16, 2), -- Rutger Hauer (Actor) - Crew ID will be 29
(5, 17, 2); -- Sean Young (Actor)

-- Pulp Fiction (Movie ID: 6)
INSERT INTO `Crew` (`movie_id`, `person_id`, `crewRole_id`) VALUES
(6, 18, 1), -- Quentin Tarantino (Director)
(6, 18, 4), -- Quentin Tarantino (Writer)
(6, 19, 2), -- John Travolta (Actor) - Crew ID will be 33
(6, 20, 2), -- Samuel L. Jackson (Actor) - Crew ID will be 34
(6, 21, 2); -- Uma Thurman (Actor)

-- Fight Club (Movie ID: 7)
INSERT INTO `Crew` (`movie_id`, `person_id`, `crewRole_id`) VALUES
(7, 22, 1), -- David Fincher (Director)
(7, 23, 2), -- Brad Pitt (Actor) - Crew ID will be 36
(7, 24, 2), -- Edward Norton (Actor) - Crew ID will be 37
(7, 25, 2); -- Helena Bonham Carter (Actor)

-- Forrest Gump (Movie ID: 8)
INSERT INTO `Crew` (`movie_id`, `person_id`, `crewRole_id`) VALUES
(8, 26, 1), -- Robert Zemeckis (Director)
(8, 27, 2), -- Tom Hanks (Actor) - Crew ID will be 40
(8, 28, 2); -- Robin Wright (Actor)

-- The Matrix (Movie ID: 9)
INSERT INTO `Crew` (`movie_id`, `person_id`, `crewRole_id`) VALUES
(9, 29, 1), -- Lana Wachowski (Director)
(9, 30, 1), -- Lilly Wachowski (Director)
(9, 29, 4), -- Lana Wachowski (Writer)
(9, 30, 4), -- Lilly Wachowski (Writer)
(9, 31, 2), -- Keanu Reeves (Actor) - Crew ID will be 46
(9, 32, 2), -- Laurence Fishburne (Actor)
(9, 33, 2); -- Carrie-Anne Moss (Actor)

-- The Shawshank Redemption (Movie ID: 10)
INSERT INTO `Crew` (`movie_id`, `person_id`, `crewRole_id`) VALUES
(10, 34, 1), -- Frank Darabont (Director)
(10, 34, 5), -- Frank Darabont (Screenplay)
(10, 35, 2), -- Tim Robbins (Actor) - Crew ID will be 51
(10, 36, 2); -- Morgan Freeman (Actor) - Crew ID will be 52

-- ... (Continue for movies 11-15 with relevant crew)
-- For brevity, I'll skip detailed crew for 11-15 but you'd add similar entries.
-- Example for Inglourious Basterds (Movie ID: 14)
INSERT INTO `Crew` (`movie_id`, `person_id`, `crewRole_id`) VALUES
(14, 18, 1), -- Quentin Tarantino (Director)
(14, 18, 4), -- Quentin Tarantino (Writer)
(14, 23, 2); -- Brad Pitt (Actor)
-- Add Christoph Waltz (Actor) - Assumes Person ID 40 for Waltz, Character ID 20 for Landa
-- INSERT INTO `Person` (`id`, `name`) VALUES (40, 'Christoph Waltz');
-- INSERT INTO `Crew` (`movie_id`, `person_id`, `crewRole_id`) VALUES (14, 40, 2); -- Crew ID will be e.g. 56

-- -----------------------------------------------------
-- Populate `CrewCharacter` (Linking Actors in Crew to Characters)
-- Uses the *assumed* Crew IDs generated above
-- -----------------------------------------------------
-- Inception Actors
INSERT INTO `CrewCharacter` (`crew_id`, `character_id`) VALUES
(4, 1),  -- DiCaprio -> Dominic Cobb
(5, 2),  -- Gordon-Levitt -> Arthur
(6, 3);  -- Page -> Ariadne

-- Godfather Actors
INSERT INTO `CrewCharacter` (`crew_id`, `character_id`) VALUES
(12, 4), -- Brando -> Vito Corleone
(13, 5); -- Pacino -> Michael Corleone

-- Interstellar Actors
INSERT INTO `CrewCharacter` (`crew_id`, `character_id`) VALUES
(16, 6), -- McConaughey -> Cooper
(17, 7); -- Hathaway -> Brand

-- Dark Knight Actors
INSERT INTO `CrewCharacter` (`crew_id`, `character_id`) VALUES
(21, 8), -- Bale -> Bruce Wayne / Batman
(22, 9); -- Ledger -> The Joker

-- Blade Runner Actors
INSERT INTO `CrewCharacter` (`crew_id`, `character_id`) VALUES
(28, 10), -- Ford -> Rick Deckard
(29, 11); -- Hauer -> Roy Batty

-- Pulp Fiction Actors
INSERT INTO `CrewCharacter` (`crew_id`, `character_id`) VALUES
(33, 12), -- Travolta -> Vincent Vega
(34, 13); -- Jackson -> Jules Winnfield

-- Fight Club Actors
INSERT INTO `CrewCharacter` (`crew_id`, `character_id`) VALUES
(36, 14), -- Pitt -> Tyler Durden
(37, 15); -- Norton -> Narrator

-- Forrest Gump Actor
INSERT INTO `CrewCharacter` (`crew_id`, `character_id`) VALUES
(40, 16); -- Hanks -> Forrest Gump

-- The Matrix Actor
INSERT INTO `CrewCharacter` (`crew_id`, `character_id`) VALUES
(46, 17); -- Reeves -> Neo

-- Shawshank Redemption Actors
INSERT INTO `CrewCharacter` (`crew_id`, `character_id`) VALUES
(51, 18), -- Robbins -> Andy Dufresne
(52, 19); -- Freeman -> Ellis Boyd Redding

-- Example: Inglourious Basterds Actor (Waltz as Landa)
-- Assumes Waltz's Crew ID for movie 14 is 56 (see comment in Crew population)
-- INSERT INTO `CrewCharacter` (`crew_id`, `character_id`) VALUES (56, 20);

-- -----------------------------------------------------
-- Populate `Movie_Genre`
-- -----------------------------------------------------
INSERT INTO `Movie_Genre` (`movie_id`, `genre_id`) VALUES
(1, 1), (1, 3), (1, 4), (1, 6), -- Inception (Sci-Fi, Action, Thriller, Adventure)
(2, 2), (2, 5),                -- Godfather (Drama, Crime)
(3, 1), (3, 2), (3, 6),        -- Interstellar (Sci-Fi, Drama, Adventure)
(4, 2), (4, 3), (4, 5), (4, 4),-- Dark Knight (Drama, Action, Crime, Thriller)
(5, 1), (5, 2), (5, 4),        -- Blade Runner (Sci-Fi, Drama, Thriller)
(6, 4), (6, 5),                -- Pulp Fiction (Thriller, Crime)
(7, 2),                        -- Fight Club (Drama)
(8, 2), (8, 3),                -- Forrest Gump (Drama, Comedy - Need Comedy Genre?)
(9, 1), (9, 3),                -- Matrix (Sci-Fi, Action)
(10, 2), (10, 5),               -- Shawshank (Drama, Crime)
(11, 8), (11, 7), (11, 9),     -- Spirited Away (Animation, Fantasy, Mystery)
(12, 2), (12, 4),               -- Parasite (Drama, Thriller)
(13, 6), (13, 7), (13, 3),     -- LOTR (Adventure, Fantasy, Action)
(14, 2), (14, 3), (14, 6),     -- Inglourious Basterds (Drama, Action, Adventure - War?)
(15, 2), (15, 5);               -- Goodfellas (Drama, Crime)

-- -----------------------------------------------------
-- Populate `Movie_Keywords`
-- -----------------------------------------------------
INSERT INTO `Movie_Keywords` (`movie_id`, `keywords_id`) VALUES
(1, 6), (1, 9), (1, 4),        -- Inception (Heist, Dream, AI)
(2, 5),                        -- Godfather (Mafia)
(3, 1), (3, 3),                -- Interstellar (Time Travel, Space Opera)
(4, 7), (4, 2),                -- Dark Knight (Superhero, Dystopia?)
(5, 2), (5, 4), (5, 10),       -- Blade Runner (Dystopia, AI, Philosophical)
(6, 5), (6, 6),                -- Pulp Fiction (Mafia, Heist)
(7, 10),                       -- Fight Club (Philosophical)
(9, 4), (9, 2), (9, 10),       -- Matrix (AI, Dystopia, Philosophical)
(10, 8),                       -- Shawshank (Based on Novel?)
(14, 5);                       -- Inglourious Basterds (Mafia - Gangsters?)


-- -----------------------------------------------------
-- Populate `Movie_ProductionCompany`
-- -----------------------------------------------------
INSERT INTO `Movie_ProductionCompany` (`movie_id`, `productionCompany_id`) VALUES
(1, 2), (1, 3), (1, 4),        -- Inception (Warner, Legendary, Syncopy)
(2, 1),                        -- Godfather (Paramount)
(3, 1), (3, 2), (3, 3), (3, 4),-- Interstellar (Paramount, Warner, Legendary, Syncopy)
(4, 2), (4, 3), (4, 4),        -- Dark Knight (Warner, Legendary, Syncopy)
(5, 2),                        -- Blade Runner (Warner)
(6, 8),                        -- Pulp Fiction (Miramax)
(7, 5),                        -- Fight Club (Fox - Need Fox? Using Universal as placeholder)
(8, 1),                        -- Forrest Gump (Paramount)
(9, 2),                        -- Matrix (Warner)
(10, 6),                       -- Shawshank (Columbia)
(11, 7),                       -- Spirited Away (Ghibli)
(12, 1),                       -- Parasite (Using Paramount as Placeholder)
(13, 2),                       -- LOTR (New Line / Warner)
(14, 5), (14, 8),              -- Inglourious Basterds (Universal, Miramax)
(15, 2);                       -- Goodfellas (Warner)

-- -----------------------------------------------------
-- Populate `Movie_ProductionCountry`
-- -----------------------------------------------------
INSERT INTO `Movie_ProductionCountry` (`movie_id`, `productionCountry_id`) VALUES
(1, 'US'), (1, 'GB'),          -- Inception
(2, 'US'),                     -- Godfather
(3, 'US'), (3, 'GB'),          -- Interstellar
(4, 'US'), (4, 'GB'),          -- Dark Knight
(5, 'US'),                     -- Blade Runner
(6, 'US'),                     -- Pulp Fiction
(7, 'US'), (7, 'DE'),          -- Fight Club
(8, 'US'),                     -- Forrest Gump
(9, 'US'),                     -- Matrix
(10, 'US'),                    -- Shawshank
(11, 'JP'),                    -- Spirited Away
(12, 'KR'),                    -- Parasite
(13, 'US'),                    -- LOTR (Also NZ)
(14, 'US'), (14, 'DE'),        -- Inglourious Basterds
(15, 'US');                    -- Goodfellas

-- -----------------------------------------------------
-- Populate `Movie_SpokenLanguage`
-- -----------------------------------------------------
INSERT INTO `Movie_SpokenLanguage` (`movie_id`, `spokenLanguage_id`) VALUES
(1, 'en'), (1, 'ja'), (1, 'fr'), -- Inception
(2, 'en'), (2, 'it'),          -- Godfather
(3, 'en'),                     -- Interstellar
(4, 'en'),                     -- Dark Knight
(5, 'en'),                     -- Blade Runner
(6, 'en'), (6, 'es'), (6, 'fr'), -- Pulp Fiction
(7, 'en'),                     -- Fight Club
(8, 'en'),                     -- Forrest Gump
(9, 'en'),                     -- Matrix
(10, 'en'),                    -- Shawshank
(11, 'ja'),                    -- Spirited Away
(12, 'ko'), (12, 'en'),        -- Parasite
(13, 'en'),                    -- LOTR (Also Elvish etc.)
(14, 'en'), (14, 'de'), (14, 'fr'), (14, 'it'), -- Inglourious Basterds
(15, 'en'), (15, 'it');        -- Goodfellas

-- -----------------------------------------------------
-- Populate `Favorite` (Sample User Favorites)
-- -----------------------------------------------------
INSERT INTO `Favorite` (`movie_id`, `user_id`) VALUES
(1, 1), -- ruben_admin likes Inception
(2, 5), -- jorge_user likes Godfather
(4, 5), -- jorge_user likes Dark Knight
(3, 2), -- artur_editor likes Interstellar
(10, 3), -- edu_analyst likes Shawshank
(6, 4); -- juanma_rec likes Pulp Fiction

-- -----------------------------------------------------
-- Populate `Watchlist` (Sample User Watchlist)
-- -----------------------------------------------------
INSERT INTO `Watchlist` (`movie_id`, `user_id`) VALUES
(5, 1), -- ruben_admin wants to watch Blade Runner
(7, 5), -- jorge_user wants to watch Fight Club
(9, 5), -- jorge_user wants to watch The Matrix
(11, 2), -- artur_editor wants to watch Spirited Away
(12, 3), -- edu_analyst wants to watch Parasite
(15, 4); -- juanma_rec wants to watch Goodfellas

-- -----------------------------------------------------
-- Populate `Review` (Sample User Reviews)
-- -----------------------------------------------------
INSERT INTO `Review` (`movie_id`, `user_id`, `content`, `rating`) VALUES
(1, 5, 'Absolutely mind-bending! One of the best sci-fi films ever made.', 9.5), -- jorge reviews Inception
(2, 1, 'A cinematic masterpiece. Brando is unforgettable.', 10.0),          -- ruben reviews Godfather
(4, 3, 'Heath Ledger\'s Joker is iconic. A perfect superhero movie.', 9.0),  -- edu reviews Dark Knight
(10, 5, 'Moved me to tears. Incredible story of hope.', 9.8);               -- jorge reviews Shawshank

-- Enable foreign key checks back
SET FOREIGN_KEY_CHECKS=1;

SET SQL_MODE=@OLD_SQL_MODE;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;