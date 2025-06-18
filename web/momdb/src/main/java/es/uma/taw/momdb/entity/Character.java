package es.uma.taw.momdb.entity;

import es.uma.taw.momdb.dto.CharacterDTO;
import es.uma.taw.momdb.dto.DTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "`character`")
public class Character implements DTO<CharacterDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "`character`", nullable = false)
    private String character;

    @ManyToMany
    @JoinTable(name = "crewcharacter",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "crew_id"))
    private Set<Crew> crews = new LinkedHashSet<>();

    public CharacterDTO toDTO() {
        CharacterDTO c = new CharacterDTO();
        c.setId(this.id);
        c.setCharacterName(this.character);
        return c;
    }
}