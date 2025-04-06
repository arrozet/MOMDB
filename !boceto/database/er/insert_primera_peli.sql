INSERT INTO moviedb.status (
	id,
    status_name
) VALUES (
	1,
    'On air'
); 

INSERT INTO moviedb.movie (
    original_title,
    budget,
    homepage,
    original_language,
    overview,
    popularity,
    release_date,
    revenue,
    runtime,
    status_id,
    tagline,
    title,
    vote_average,
    vote_count
) VALUES (
    'The Godfather',
    6000000,
    'https://www.paramount.com/movies/the-godfather',
    'en',
    'The story spans the years from 1945 to 1955 and chronicles the fictional Italian-American Corleone crime family. When organized crime family patriarch Vito Corleone barely survives an attempt on his life, his youngest son, Michael, steps in to take care of the would-be killers, launching a campaign of bloody revenge.',
    120.1234,
    '1972-03-24',
    245066411,
    175,
    1, -- Asumo que 1 significa "Released" o estado similar
    'An offer you can''t refuse.',
    'El Padrino',
    8.7,
    16836
);

SELECT * from moviedb.movie