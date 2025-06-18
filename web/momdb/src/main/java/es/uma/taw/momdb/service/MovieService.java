package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.CrewRepository;
import es.uma.taw.momdb.dao.GenreRepository;
import es.uma.taw.momdb.dao.MovieRepository;
import es.uma.taw.momdb.dao.StatusRepository;
import es.uma.taw.momdb.dto.MovieDTO;
import es.uma.taw.momdb.entity.Crew;
import es.uma.taw.momdb.entity.Movie;
import es.uma.taw.momdb.entity.Status;
import es.uma.taw.momdb.ui.Filtro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Autowired
    protected StatusRepository statusRepository;

    @Autowired
    protected CrewRepository crewRepository;

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

    public List<MovieDTO> listarPeliculasBySelectFilters(Filtro filtro) {
        BigDecimal popMin = null, popMax = null;
        if (filtro.getPopularityRange() != null && !filtro.getPopularityRange().isBlank()) {
            String[] parts = filtro.getPopularityRange().split("-");
            popMin = new BigDecimal(parts[0]);
            popMax = new BigDecimal(parts[1]);
        }
        List<Movie> movies = movieRepository.findByFiltros(
                filtro.getGeneroId(),
                filtro.getYear(),
                filtro.getRating(),
                popMin,
                popMax
        );
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

    public void crearPelicula(MovieDTO movie) {
        Movie movieEntity = new Movie();
        movieEntity.setTitle(movie.getTitulo());
        movieEntity.setOriginalTitle(movie.getTitulo()); // Usamos el mismo título para el título original
        movieEntity.setOriginalLanguage(movie.getIdiomaOriginal());
        movieEntity.setReleaseDate(movie.getFechaDeSalida());
        movieEntity.setRevenue(movie.getIngresos());
        movieEntity.setGenres(new HashSet<>(this.genreRepository.findAllById(movie.getGeneroIds())));
        movieEntity.setOverview(movie.getDescripcion());
        movieEntity.setImageLink(movie.getImageLink());
        movieEntity.setVoteCount(0);
        movieEntity.setVoteAverage(new BigDecimal("0.0"));
        movieEntity.setPopularity(new BigDecimal("0.0"));
        // Buscar el status "Released" que es el más común para películas
        Status status = statusRepository.findById(1).orElse(null);
        if (status == null) {
            // Si no existe, crear uno por defecto
            status = new Status();
            status.setId(1);
            status.setStatusName("Released");
            statusRepository.save(status);
        }
        movieEntity.setStatus(status);
        this.movieRepository.save(movieEntity);
    }

    public void borrarPelicula (Integer id) {
        Movie movie = this.movieRepository.findById(id).orElse(null);

        Set<Crew> crews = new HashSet<>(movie.getCrews());
        //Es necesario borrar las crew a mano porque da transient error
        // porque no está el borrado en cascada en la entity con crew (Si se pone se me rompe lo demás asi que se hace a mano)
        for (Crew crew : crews) {
            this.crewRepository.delete(crew);
        }

        // Finalmente eliminamos la película
        this.movieRepository.delete(movie);

    }

}
