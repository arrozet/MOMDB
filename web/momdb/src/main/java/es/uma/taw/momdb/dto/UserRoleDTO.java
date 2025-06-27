package es.uma.taw.momdb.dto;

import lombok.Data;

/*
 * @author Artur797 (Artur Vargas - 69.2%), arrozet (Rubén Oliva - 30.8%)
 */

@Data
public class UserRoleDTO implements DTOWithNameAndId<Integer> {
    private Integer id;
    private String name;
}
