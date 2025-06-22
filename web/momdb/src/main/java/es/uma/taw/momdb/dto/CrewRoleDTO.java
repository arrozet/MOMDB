package es.uma.taw.momdb.dto;

import lombok.Data;

/*
 * @author arrozet (Rubén Oliva), Artur797 (Artur Vargas)
 */

@Data
public class CrewRoleDTO implements DTOWithNameAndId<Integer> {
    private Integer id;
    private String role;

    public String getName() {
        return role;
    }
}
