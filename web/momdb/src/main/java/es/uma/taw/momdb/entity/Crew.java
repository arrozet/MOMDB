package es.uma.taw.momdb.entity;

import es.uma.taw.momdb.dto.CharacterDTO;
import es.uma.taw.momdb.dto.CrewDTO;
import es.uma.taw.momdb.dto.DTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.*;
import java.util.stream.Collectors;

/*
 * @author arrozet (Rubén Oliva - 53.2%), Artur797 (Artur Vargas - 24.2%), projectGeorge (Jorge Repullo - 22.6%)
 */

@Getter
@Setter
@Entity
@Table(name = "crew")
public class Crew implements DTO<CrewDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crew_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "crewRole_id", referencedColumnName = "id", nullable = false)
    private Crewrole crewRole;

    @ManyToMany(mappedBy = "crews", fetch = FetchType.EAGER)
    private Set<Character> characters = new LinkedHashSet<>();

    public CrewDTO toDTO() {
        CrewDTO c = new CrewDTO();
        c.setId(this.id);
        c.setPersona(this.person.getName());
        c.setPersonaId(this.person.getId());
        c.setPeliculaId(this.movie.getId());
        c.setRol(this.crewRole.getRole());
        c.setRolId(this.crewRole.getId());

        // Convert characters to DTOs
        List<CharacterDTO> personajes = this.characters.stream()
                .sorted(Comparator.comparing(Character::getId)) // o Character::getId
                .map(Character::toDTO)
                .collect(Collectors.toList());

        c.setPersonajes(personajes);

        return c;
    }
}