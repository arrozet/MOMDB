package es.uma.taw.momdb.entity;

import es.uma.taw.momdb.dto.DTO;
import es.uma.taw.momdb.dto.PersonDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "person")
public class Person implements DTO<PersonDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "person")
    private Set<Crew> crews = new LinkedHashSet<>();

    public PersonDTO toDTO() {
        PersonDTO person = new PersonDTO();
        person.setId(this.id);
        person.setName(this.name);

        return person;
    }

}