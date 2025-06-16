package es.uma.taw.momdb.entity;

import es.uma.taw.momdb.dto.DTO;
import es.uma.taw.momdb.dto.UserDTO;
import es.uma.taw.momdb.dto.UserRoleDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "userrole")
public class UserRole implements EntityWithNameAndId, Serializable, DTO<UserRoleDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "role")
    private Set<User> users = new LinkedHashSet<>();

    public UserRoleDTO toDTO () {
        UserRoleDTO role = new UserRoleDTO();
        role.setId(this.id);
        role.setName(this.name);

        return role;

    }

}