package es.uma.taw.momdb.dto;

import lombok.Data;

/*
 * @author amcgiluma (Juan Manuel Valenzuela)
 */
@Data
public class UserRegistrationDTO {
    private String username;
    private String email;
    private String password;
}