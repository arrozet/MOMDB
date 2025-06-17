package es.uma.taw.momdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "productioncountry")
public class Productioncountry implements EntityWithNameAndId<String>{
    @Id
    @Column(name = "iso_3166_1", nullable = false, length = 5)
    private String iso31661;

    @Column(name = "country", nullable = false)
    private String country;

    @ManyToMany(mappedBy = "productioncountries")
    private Set<Movie> movies = new LinkedHashSet<>();

    @Override
    public String getId(){
        return iso31661;
    }

    @Override
    public String getName() {
        return country;
    }
}