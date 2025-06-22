package es.uma.taw.momdb.dto;

import lombok.Data;

/*
 * @author Artur797 (Artur Vargas), arrozet (Rubén Oliva)
 */

@Data
public class UserRoleDTO implements DTOWithNameAndId<Integer> {
    private Integer id;
    private String name;
}
