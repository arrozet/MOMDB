package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.CrewRepository;
import es.uma.taw.momdb.dao.GenreRepository;
import es.uma.taw.momdb.dao.MovieRepository;
import es.uma.taw.momdb.dao.StatusRepository;
import es.uma.taw.momdb.dto.MovieDTO;
import es.uma.taw.momdb.entity.Crew;
import es.uma.taw.momdb.entity.Genre;
import es.uma.taw.momdb.entity.Movie;
import es.uma.taw.momdb.entity.Status;
import es.uma.taw.momdb.ui.Filtro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar la lógica de negocio de las operaciones sobre las películas.
 * Proporciona métodos para buscar, crear, actualizar y eliminar películas, así como
 * calcular estadísticas y generar recomendaciones.
 * 
 * @author Artur797 (Artur Vargas), projectGeorge (Jorge Repullo), edugbau (Eduardo González), amcgiluma (Juan Manuel Valenzuela)
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

    /**
     * Devuelve una lista de todas las películas.
     * @return Lista de DTO de películas.
     */
    public List<MovieDTO> listarPeliculas () {
        return this.listarPeliculas(null);
    }

    /**
     * Devuelve una lista de películas filtrada por título.
     * Si el título es nulo o vacío, devuelve todas las películas.
     * @param titulo Título por el que filtrar.
     * @return Lista de DTO de películas.
     */
    public List<MovieDTO> listarPeliculas (String titulo) {
        List<Movie> movies;

        if ((titulo == null || titulo.isEmpty())) { // sin título ni categorías
            movies = movieRepository.findAll();
        } else { // solo título
            movies = this.movieRepository.filterByTitle(titulo);
        }
        return this.entity2DTO(movies);
    }

    /**
     * Devuelve una lista de películas filtrada por varios criterios.
     * @param filtro Objeto que contiene los filtros a aplicar.
     * @return Lista de DTO de películas.
     */
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

    /**
     * Busca una película por su ID.
     * @param id El ID de la película.
     * @return El DTO de la película, o null si no se encuentra.
     */
    public MovieDTO findPeliculaById (int id) {
        Movie movie = this.movieRepository.findById(id).orElse(null);
        return movie != null ? movie.toDTO() : null;
    }

    /**
     * Guarda o actualiza la información de una película.
     * @param movie DTO con la información de la película.
     */
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

    /**
     * Crea una nueva película en la base de datos.
     * @param movie DTO con la información de la película a crear.
     */
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

    /**
     * Borra una película y todas sus referencias de equipo (crew).
     * @param id El ID de la película a borrar.
     */
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

    /**
     * Devuelve una página de películas para la paginación.
     * @param pageNumber Número de la página a obtener.
     * @param pageSize Tamaño de la página.
     * @return Página de DTO de películas.
     */
    public Page<MovieDTO> findPaginated(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Movie> moviePage = movieRepository.findAll(pageable);
        return moviePage.map(Movie::toDTO);
    }

    /**
     * Busca películas paginadas con filtros.
     * @param filtro Filtros a aplicar.
     * @param pageNumber Número de la página a obtener.
     * @param pageSize Tamaño de la página.
     * @return Página de DTO de películas.
     */
    public Page<MovieDTO> findPaginatedWithFilters(Filtro filtro, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Movie> moviePage;

        if (filtro == null) {
            moviePage = movieRepository.findAll(pageable);
        } else if (filtro.getTexto() != null && !filtro.getTexto().isBlank()) {
            moviePage = movieRepository.findByTitleContainingIgnoreCase(filtro.getTexto(), pageable);
        } else {
            BigDecimal popMin = null, popMax = null;
            if (filtro.getPopularityRange() != null && !filtro.getPopularityRange().isBlank()) {
                String[] parts = filtro.getPopularityRange().split("-");
                popMin = new BigDecimal(parts[0]);
                popMax = new BigDecimal(parts[1]);
            }
            moviePage = movieRepository.findByFiltros(
                    filtro.getGeneroId(),
                    filtro.getYear(),
                    filtro.getRating(),
                    popMin,
                    popMax,
                    pageable
            );
        }
        return moviePage.map(Movie::toDTO);
    }

    /**
     * Calcula la popularidad media de todas las películas.
     * @return La popularidad media.
     */
    public BigDecimal getAveragePopularity() {
        return movieRepository.getAveragePopularity();
    }

    /**
     * Calcula la popularidad media de las películas de un género específico.
     * @param genreId ID del género.
     * @return La popularidad media para el género.
     */
    public BigDecimal getAveragePopularityByGenre(Integer genreId) {
        return movieRepository.getAveragePopularityByGenre(genreId);
    }

    /**
     * Calcula los ingresos medios de todas las películas.
     * @return Los ingresos medios.
     */
    public Double getAverageRevenue() {
        return movieRepository.getAverageRevenue();
    }

    /**
     * Calcula los ingresos medios de las películas de un género específico.
     * @param genreId ID del género.
     * @return Los ingresos medios para el género.
     */
    public Double getAverageRevenueByGenre(Integer genreId) {
        return movieRepository.getAverageRevenueByGenre(genreId);
    }

    /**
     * Calcula la puntuación media de todas las películas.
     * @return La puntuación media.
     */
    public BigDecimal getAverageVoteAverage() {
        return movieRepository.getAverageVoteAverage();
    }

    /**
     * Calcula la puntuación media de las películas de un género específico.
     * @param genreId ID del género.
     * @return La puntuación media para el género.
     */
    public BigDecimal getAverageVoteAverageByGenre(Integer genreId) {
        return movieRepository.getAverageVoteAverageByGenre(genreId);
    }

    /**
     * Calcula el número medio de votos de todas las películas.
     * @return El número medio de votos.
     */
    public Double getAverageVoteCount() {
        return movieRepository.getAverageVoteCount();
    }

    /**
     * Calcula el número medio de votos de las películas de un género específico.
     * @param genreId ID del género.
     * @return El número medio de votos para el género.
     */
    public Double getAverageVoteCountByGenre(Integer genreId) {
        return movieRepository.getAverageVoteCountByGenre(genreId);
    }

    /**
     * Calcula la duración media de todas las películas.
     * @return La duración media.
     */
    public Double getAverageRuntime() {
        return movieRepository.getAverageRuntime();
    }

    /**
     * Calcula la duración media de las películas de un género específico.
     * @param genreId ID del género.
     * @return La duración media para el género.
     */
    public Double getAverageRuntimeByGenre(Integer genreId) {
        return movieRepository.getAverageRuntimeByGenre(genreId);
    }

    /**
     * Asigna un miembro del equipo a una película.
     * @param movie La película.
     * @param crew El miembro del equipo a asignar.
     */
    public void addCrew(Movie movie, Crew crew) {
        movie.getCrews().add(crew);
        movieRepository.save(movie);
    }

    /**
     * Reemplaza un miembro del equipo de una película por otro.
     * @param movie La película.
     * @param crewAnt El miembro del equipo antiguo.
     * @param crewNueva El miembro del equipo nuevo.
     */
    public void removeAndAddCrew(Movie movie, Crew crewAnt, Crew crewNueva) {
        movie.getCrews().remove(crewAnt);
        movie.getCrews().add(crewNueva);
        movieRepository.save(movie);
    }

    /**
     * Busca películas recomendadas basadas en los géneros de una película dada.
     * @param movieId ID de la película para la que se buscan recomendaciones.
     * @param limit Número máximo de recomendaciones a devolver.
     * @return Lista de DTO de películas recomendadas.
     */
    public List<MovieDTO> findRecommendedMovies(Integer movieId, int limit) {
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null || movie.getGenres().isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> genreIds = movie.getGenres().stream()
                .map(Genre::getId)
                .collect(Collectors.toList());
        long genreCount = genreIds.size();

        Pageable pageable = PageRequest.of(0, limit);

        List<Movie> recommendedMovies = movieRepository.findByExactGenresOrderByRating(movieId, genreIds, genreCount, pageable);

        return this.entity2DTO(recommendedMovies);
    }
}
