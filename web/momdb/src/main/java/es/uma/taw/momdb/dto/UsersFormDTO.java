package es.uma.taw.momdb.dto;

import lombok.Data;

import java.util.List;

@Data
public class UsersFormDTO {
    List<UserDTO> users;
}
