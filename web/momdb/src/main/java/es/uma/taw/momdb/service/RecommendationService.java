package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.MovieRepository;
import es.uma.taw.momdb.dao.RecommendationRepository;
import es.uma.taw.momdb.dao.UserRepository;
import es.uma.taw.momdb.dto.MovieDTO;
import es.uma.taw.momdb.dto.RecommendationDTO;
import es.uma.taw.momdb.entity.Movie;
import es.uma.taw.momdb.entity.Recommendation;
import es.uma.taw.momdb.entity.RecommendationId;
import es.uma.taw.momdb.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar las recomendaciones de películas.
 * Permite guardar, buscar y eliminar recomendaciones hechas por los usuarios.
 *
 * @author amcgiluma (Juan Manuel Valenzuela - 69.1%), arrozet (Rubén Oliva, Javadocs - 30.9%)
 */
@Service
public class RecommendationService {

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieService movieService;

    /**
     * Guarda una nueva recomendación de una película hecha por un usuario.
     * No permite que un usuario recomiende una película a sí misma o que cree una recomendación duplicada.
     *
     * @param recommenderId El ID del usuario que hace la recomendación.
     * @param mainMovieId El ID de la película para la cual se hace la recomendación.
     * @param recommendedMovieId El ID de la película que está siendo recomendada.
     * @throws RuntimeException si el usuario o alguna de las películas no se encuentran.
     */
    public void saveRecommendation(Integer recommenderId, Integer mainMovieId, Integer recommendedMovieId) {
        if (mainMovieId.equals(recommendedMovieId)) {
            return; // No se puede recomendar una película a sí misma.
        }

        User recommender = userRepository.findById(recommenderId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + recommenderId));
        Movie mainMovie = movieRepository.findById(mainMovieId)
                .orElseThrow(() -> new RuntimeException("Película principal no encontrada con id: " + mainMovieId));
        Movie recommendedMovie = movieRepository.findById(recommendedMovieId)
                .orElseThrow(() -> new RuntimeException("Película recomendada no encontrada con id: " + recommendedMovieId));

        RecommendationId id = new RecommendationId();
        id.setRecommenderId(recommenderId);
        id.setMainMovieId(mainMovieId);
        id.setRecommendedMovieId(recommendedMovieId);

        if (recommendationRepository.existsById(id)) {
            return; // La recomendación ya existe.
        }

        Recommendation recommendation = new Recommendation();
        recommendation.setId(id);
        recommendation.setRecommender(recommender);
        recommendation.setMainMovie(mainMovie);
        recommendation.setRecommendedMovie(recommendedMovie);

        recommendationRepository.save(recommendation);
    }

    /**
     * Busca y agrega las recomendaciones de usuarios para una película específica.
     * Devuelve una lista de películas recomendadas junto con el número de veces que cada una ha sido recomendada.
     *
     * @param movieId El ID de la película principal.
     * @return Una lista de {@link RecommendationDTO} con las películas recomendadas y su contador de recomendaciones.
     */
    public List<RecommendationDTO> findAggregatedUserRecommendationsForMovie(Integer movieId) {
        List<Object[]> results = recommendationRepository.findUserRecommendationsAndCountByMainMovieId(movieId);
        return results.stream()
                .map(result -> {
                    Movie movieEntity = (Movie) result[0];
                    Long count = (Long) result[1];
                    MovieDTO movieDTO = movieService.findPeliculaById(movieEntity.getId());
                    return new RecommendationDTO(movieDTO, count);
                })
                .collect(Collectors.toList());
    }

    /**
     * Busca todas las recomendaciones que el usuario actual ha hecho para una película específica.
     *
     * @param movieId El ID de la película principal.
     * @param userId El ID del usuario actual.
     * @return Una lista de {@link RecommendationDTO} que representa las recomendaciones del usuario.
     */
    public List<RecommendationDTO> findCurrentUserRecommendationsForMovie(Integer movieId, Integer userId) {
        return recommendationRepository.findByMainMovieIdAndUserId(movieId, userId)
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Elimina una recomendación específica hecha por un usuario para una película.
     *
     * @param recommenderId El ID del usuario que hizo la recomendación.
     * @param mainMovieId El ID de la película principal.
     * @param recommendedMovieId El ID de la película recomendada que se va a eliminar.
     */
    public void deleteRecommendation(Integer recommenderId, Integer mainMovieId, Integer recommendedMovieId) {
        RecommendationId id = new RecommendationId();
        id.setRecommenderId(recommenderId);
        id.setMainMovieId(mainMovieId);
        id.setRecommendedMovieId(recommendedMovieId);

        recommendationRepository.deleteById(id);
    }

    /**
     * Convierte una entidad {@link Recommendation} a su correspondiente {@link RecommendationDTO}.
     *
     * @param entity La entidad de recomendación a convertir.
     * @return El DTO de la recomendación.
     */
    private RecommendationDTO entityToDTO(Recommendation entity) {
        return new RecommendationDTO(entity.getRecommender().toDTO(), movieService.findPeliculaById(entity.getRecommendedMovie().getId()));
    }
}