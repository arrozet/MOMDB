package es.uma.taw.momdb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

/*
 * @author arrozet (Rubén Oliva)
 */

@Getter
@Setter
@Embeddable
public class RecommendationId implements java.io.Serializable {
    private static final long serialVersionUID = -9009626314707386050L;
    @Column(name = "recommender_id", nullable = false)
    private Integer recommenderId;

    @Column(name = "main_movie_id", nullable = false)
    private Integer mainMovieId;

    @Column(name = "recommended_movie_id", nullable = false)
    private Integer recommendedMovieId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RecommendationId entity = (RecommendationId) o;
        return Objects.equals(this.recommenderId, entity.recommenderId) &&
                Objects.equals(this.mainMovieId, entity.mainMovieId) &&
                Objects.equals(this.recommendedMovieId, entity.recommendedMovieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recommenderId, mainMovieId, recommendedMovieId);
    }

}