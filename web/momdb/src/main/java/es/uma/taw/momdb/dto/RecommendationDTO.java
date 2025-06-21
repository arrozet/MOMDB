package es.uma.taw.momdb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @author - amcgiluma (Juan Manuel Valenzuela)
 * @co-authors -
 */
@Data
@NoArgsConstructor
public class RecommendationDTO {
    private UserDTO recommender;
    private MovieDTO recommendedMovie;
    private Long recommendationCount;

    public RecommendationDTO(UserDTO recommender, MovieDTO recommendedMovie) {
        this.recommender = recommender;
        this.recommendedMovie = recommendedMovie;
    }

    public RecommendationDTO(MovieDTO recommendedMovie, Long recommendationCount) {
        this.recommendedMovie = recommendedMovie;
        this.recommendationCount = recommendationCount;
    }
}