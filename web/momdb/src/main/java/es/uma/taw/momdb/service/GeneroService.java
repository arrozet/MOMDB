package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.GenreRepository;
import es.uma.taw.momdb.dto.GenreAnalyticsDTO;
import es.uma.taw.momdb.dto.GenreDTO;
import es.uma.taw.momdb.entity.Genre;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio de las operaciones sobre los géneros cinematográficos.
 * Proporciona métodos para buscar, crear, actualizar y eliminar géneros del sistema.
 * 
 * @author arrozet (Rubén Oliva - 58.5%), edugbau (Eduardo González - 21.2%), Artur797 (Artur Vargas - 20.3%)
 */

@Service
public class GeneroService extends DTOService<GenreDTO, Genre>{

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MovieService movieService;

    /**
     * Obtiene todos los géneros.
     * @return Una lista de DTOs {@link GenreDTO}.
     */
    public List<GenreDTO> findAllGenres() {
        return entity2DTO(genreRepository.findAll());
    }

    /**
     * Busca géneros por su nombre.
     * @param genre El nombre a buscar.
     * @return Una lista de DTOs {@link GenreDTO} que contienen el nombre.
     */
    public List<GenreDTO> findGenresByGenre(String genre) {
        return entity2DTO(genreRepository.findByGenreContainingIgnoreCase(genre));
    }

    public List<GenreDTO> listarGeneros () {
        List<Genre> entities = this.genreRepository.findAll();
        return this.entity2DTO(entities);
    }

    /**
     * Busca un género por su ID.
     * @param id El ID del género a buscar.
     * @return El {@link GenreDTO} encontrado, o null si no existe.
     */
    public GenreDTO findGenre(int id) {
        Genre genre = genreRepository.findById(id).orElse(null);
        return genre != null ? genre.toDTO() : null;
    }

    /**
     * Elimina un género por su ID.
     * @param id El ID del género a eliminar.
     */
    public void deleteGenre(int id) {
        genreRepository.deleteById(id);
    }

    /**
     * Actualiza el nombre de un género.
     * @param id El ID del género a actualizar.
     * @param name El nuevo nombre para el género.
     */
    public void updateGenre(int id, String name) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre not found with id: " + id));
        genre.setGenre(name);
        genreRepository.save(genre);
    }

    /**
     * Crea un nuevo género.
     * @param name El nombre del nuevo género.
     */
    public void createGenre(String name) {
        Genre genre = new Genre();
        genre.setGenre(name);
        genreRepository.save(genre);
    }

    /**
     * Calcula y obtiene una lista de analíticas para cada género de película.
     * Para cada género, se recopilan métricas como ingresos medios, presupuesto medio,
     * duración media, popularidad media, y conteos de favoritos y watchlist.
     *
     * @return Una lista de objetos {@link GenreAnalyticsDTO}, donde cada objeto contiene las analíticas de un género.
     */
    public List<GenreAnalyticsDTO> getGenreAnalytics() {
        List<GenreDTO> genres = findAllGenres();
        List<GenreAnalyticsDTO> analyticsList = new ArrayList<>();

        for (GenreDTO genre : genres) {
            GenreAnalyticsDTO analytics = new GenreAnalyticsDTO();
            analytics.setGenreName(genre.getGenero());
            analytics.setAverageRevenue(movieService.getAverageRevenueByGenre(genre.getId()));
            analytics.setAverageBudget(movieService.getAverageBudgetByGenre(genre.getId()));
            analytics.setAverageRuntime(movieService.getAverageRuntimeByGenre(genre.getId()));
            analytics.setAveragePopularity(movieService.getAveragePopularityByGenre(genre.getId()));
            analytics.setAverageVoteCount(movieService.getAverageVoteCountByGenre(genre.getId()));
            analytics.setFavoriteCount(movieService.getFavoriteCountByGenre(genre.getId()));
            analytics.setWatchlistCount(movieService.getWatchlistCountByGenre(genre.getId()));
            analyticsList.add(analytics);
        }

        return analyticsList;
    }
}
