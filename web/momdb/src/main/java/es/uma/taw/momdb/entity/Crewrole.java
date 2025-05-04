package es.uma.taw.momdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "crewrole")
public class Crewrole implements EntityWithNameAndId{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "role", nullable = false)
    private String role;

    @OneToMany(mappedBy = "crewRole")
    private Set<Crew> crews = new LinkedHashSet<>();

    @Override
    public String getName() {
        return role;
    }
}