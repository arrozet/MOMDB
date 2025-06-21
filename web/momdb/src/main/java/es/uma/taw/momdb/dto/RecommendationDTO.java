package es.uma.taw.momdb.dto;

import lombok.Data;

/*
 * @author - amcgiluma (Juan Manuel Valenzuela)
 * @co-authors -
 */
@Data
public class RecommendationDTO {
    private UserDTO recommender;
    private MovieDTO recommendedMovie;
}