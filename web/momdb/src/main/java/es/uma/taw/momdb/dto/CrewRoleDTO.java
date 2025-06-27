package es.uma.taw.momdb.dto;

import lombok.Data;

/*
 * @author arrozet (Rubén Oliva - 64.7%), Artur797 (Artur Vargas - 35.3%)
 */
@Data
public class CrewRoleDTO implements DTOWithNameAndId<Integer> {
    private Integer id;
    private String role;

    public String getName() {
        return role;
    }
}
