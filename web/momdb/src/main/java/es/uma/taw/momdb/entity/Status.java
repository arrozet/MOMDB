package es.uma.taw.momdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

/*
 * @author arrozet (Rubén Oliva)
 */

@Getter
@Setter
@Entity
@Table(name = "status")
public class Status {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "status_name", nullable = false)
    private String statusName;

    @OneToMany(mappedBy = "status")
    private Set<Movie> movies = new LinkedHashSet<>();

}