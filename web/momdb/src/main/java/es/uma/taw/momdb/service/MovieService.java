package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.GenreRepository;
import es.uma.taw.momdb.dao.MovieRepository;
import es.uma.taw.momdb.dto.MovieDTO;
import es.uma.taw.momdb.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

/*
 * @author - Artur797 (Artur Vargas)
 * @co-authors - projectGeorge (Jorge Repullo)
 */

@Service
public class MovieService extends DTOService<MovieDTO, Movie>{

    @Autowired
    protected MovieRepository movieRepository;

    @Autowired
    protected GenreRepository genreRepository;



    public List<MovieDTO> listarPeliculas () {
        return this.listarPeliculas(null);
    }

    public List<MovieDTO> listarPeliculas (String titulo) {
        List<Movie> movies;

        if ((titulo == null || titulo.isEmpty())) { // sin título ni categorías
            movies = movieRepository.findAll();
        } else { // solo título
            movies = this.movieRepository.filterByTitle(titulo);
        }
        return this.entity2DTO(movies);
    }

    public List<MovieDTO> listarPeliculasPorGenero (List<Integer> generoIds) {
        List<Movie> movies = this.movieRepository.filterByGenresId(generoIds);
        return this.entity2DTO(movies);
    }

    public MovieDTO findPeliculaById (int id) {
        Movie movie = this.movieRepository.findById(id).orElse(null);
        return movie != null ? movie.toDTO() : null;
    }
    public void saveMovie (MovieDTO movie) {
        Movie movieEntity = this.movieRepository.findById(movie.getId()).orElse(new Movie());
        movieEntity.setTitle(movie.getTitulo());
        movieEntity.setOriginalLanguage(movie.getIdiomaOriginal());
        movieEntity.setReleaseDate(movie.getFechaDeSalida());
        movieEntity.setRevenue(movie.getIngresos());
        movieEntity.setGenres(new HashSet<>(this.genreRepository.findAllById(movie.getGeneroIds())));
        movieEntity.setOverview(movie.getDescripcion());
        movieEntity.setImageLink(movie.getImageLink());
        this.movieRepository.save(movieEntity);
    }

    public void borrarPelicula (Integer id) {
        this.movieRepository.deleteById(id);
    }

}
