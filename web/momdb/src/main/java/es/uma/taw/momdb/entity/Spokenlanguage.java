package es.uma.taw.momdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "spokenlanguage")
public class Spokenlanguage {
    @Id
    @Column(name = "iso_639_1", nullable = false, length = 5)
    private String iso6391;

    @Column(name = "language", nullable = false)
    private String language;

    @ManyToMany(mappedBy = "spokenlanguages")
    private Set<Movie> movies = new LinkedHashSet<>();

}