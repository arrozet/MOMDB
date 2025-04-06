package es.uma.taw.momdb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class MovieProductioncountryId implements java.io.Serializable {
    private static final long serialVersionUID = -1272605942448474034L;
    @Column(name = "movie_id", nullable = false)
    private Integer movieId;

    @Column(name = "productionCountry_id", nullable = false, length = 5)
    private String productioncountryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MovieProductioncountryId entity = (MovieProductioncountryId) o;
        return Objects.equals(this.movieId, entity.movieId) &&
                Objects.equals(this.productioncountryId, entity.productioncountryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, productioncountryId);
    }

}