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
public class MovieKeywordId implements java.io.Serializable {
    private static final long serialVersionUID = 6085169689300101931L;
    @Column(name = "movie_id", nullable = false)
    private Integer movieId;

    @Column(name = "keywords_id", nullable = false)
    private Integer keywordsId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MovieKeywordId entity = (MovieKeywordId) o;
        return Objects.equals(this.keywordsId, entity.keywordsId) &&
                Objects.equals(this.movieId, entity.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keywordsId, movieId);
    }

}