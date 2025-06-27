package es.uma.taw.momdb.dto;

import lombok.Data;

import java.util.List;

/*
 * @author arrozet (Rubén Oliva)
 */

@Data
public class UsersFormDTO {
    List<UserDTO> users;
}
