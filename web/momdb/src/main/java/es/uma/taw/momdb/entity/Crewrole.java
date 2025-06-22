package es.uma.taw.momdb.entity;

import es.uma.taw.momdb.dto.CrewRoleDTO;
import es.uma.taw.momdb.dto.DTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "crewrole")
public class Crewrole implements Serializable, DTO<CrewRoleDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "role", nullable = false)
    private String role;

    @OneToMany(mappedBy = "crewRole")
    private Set<Crew> crews = new LinkedHashSet<>();

    public CrewRoleDTO toDTO () {
        CrewRoleDTO role = new CrewRoleDTO();
        role.setId(this.id);
        role.setRole(this.role);

        return role;

    }
}