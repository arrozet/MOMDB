package es.uma.taw.momdb.dto;

import lombok.Data;

/*
 * @author - arrozet (Rubén Oliva)
 * @co-authors - Artur797 (Artur Vargas)
 */

@Data
public class UserDTO {
    int userId;
    int roleId;
    String rolename;
    String username;
    String profilePic;
}
