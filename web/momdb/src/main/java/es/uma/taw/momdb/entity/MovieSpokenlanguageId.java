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
public class MovieSpokenlanguageId implements java.io.Serializable {
    private static final long serialVersionUID = 6643016953600526312L;
    @Column(name = "movie_id", nullable = false)
    private Integer movieId;

    @Column(name = "spokenLanguage_id", nullable = false, length = 5)
    private String spokenlanguageId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MovieSpokenlanguageId entity = (MovieSpokenlanguageId) o;
        return Objects.equals(this.movieId, entity.movieId) &&
                Objects.equals(this.spokenlanguageId, entity.spokenlanguageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, spokenlanguageId);
    }

}