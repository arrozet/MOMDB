package es.uma.taw.momdb.dto;

import lombok.Data;
import java.util.List;

/*
 * @author arrozet (Rubén Oliva - 85.7%), Artur797 (Artur Vargas - 9.5%), projectGeorge (Jorge Repullo - 4.8%)
 */

@Data
public class UserDTO {
    int userId;
    int roleId;
    String rolename;
    String username;
    String email;
    String password;
    String profilePic;
    List<MovieDTO> favoriteMovies;
}
