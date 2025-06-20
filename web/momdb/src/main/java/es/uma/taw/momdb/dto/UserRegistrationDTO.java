package es.uma.taw.momdb.dto;

import lombok.Data;
/*
 * @author - amcgiluma (Juan Manuel Valenzuela)
 * @co-authors -
 */
@Data
public class UserRegistrationDTO {
    private String username;
    private String email;
    private String password;
}