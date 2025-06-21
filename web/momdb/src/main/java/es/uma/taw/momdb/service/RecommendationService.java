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

    public List<RecommendationDTO> findCurrentUserRecommendationsForMovie(Integer movieId, Integer userId) {
        return recommendationRepository.findByMainMovieIdAndUserId(movieId, userId)
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    public void deleteRecommendation(Integer recommenderId, Integer mainMovieId, Integer recommendedMovieId) {
        RecommendationId id = new RecommendationId();
        id.setRecommenderId(recommenderId);
        id.setMainMovieId(mainMovieId);
        id.setRecommendedMovieId(recommendedMovieId);

        recommendationRepository.deleteById(id);
    }

    private RecommendationDTO entityToDTO(Recommendation entity) {
        return new RecommendationDTO(entity.getRecommender().toDTO(), movieService.findPeliculaById(entity.getRecommendedMovie().getId()));
    }
}