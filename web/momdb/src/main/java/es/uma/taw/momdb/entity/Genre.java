package es.uma.taw.momdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "genre")
public class Genre implements EntityWithNameAndId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "genre", nullable = false)
    private String genre;

    @ManyToMany
    @JoinTable(name = "movie_genre",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private Set<Movie> movies = new LinkedHashSet<>();

    @Override
    public String getName() {
        return genre;
    }
}