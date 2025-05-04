package es.uma.taw.momdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "productioncompany")
public class Productioncompany implements EntityWithNameAndId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "company", nullable = false)
    private String company;

    @ManyToMany(mappedBy = "productioncompanies")
    private Set<Movie> movies = new LinkedHashSet<>();

    @Override
    public String getName() {
        return company;
    }

}