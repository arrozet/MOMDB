package es.uma.taw.momdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/*
 * @author arrozet (Rubén Oliva)
 */

@Getter
@Setter
@Entity
@Table(name = "recommendation")
public class Recommendation {
    @EmbeddedId
    private RecommendationId id;

    @MapsId("recommenderId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "recommender_id", nullable = false)
    private User recommender;

    @MapsId("mainMovieId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "main_movie_id", nullable = false)
    private Movie mainMovie;

    @MapsId("recommendedMovieId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "recommended_movie_id", nullable = false)
    private Movie recommendedMovie;

}