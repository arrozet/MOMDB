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
public class MovieProductioncompanyId implements java.io.Serializable {
    private static final long serialVersionUID = 8304220277142340284L;
    @Column(name = "movie_id", nullable = false)
    private Integer movieId;

    @Column(name = "productionCompany_id", nullable = false)
    private Integer productioncompanyId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MovieProductioncompanyId entity = (MovieProductioncompanyId) o;
        return Objects.equals(this.productioncompanyId, entity.productioncompanyId) &&
                Objects.equals(this.movieId, entity.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productioncompanyId, movieId);
    }

}