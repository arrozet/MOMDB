package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.UserRepository;
import es.uma.taw.momdb.dto.UserDTO;
import es.uma.taw.momdb.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author - Artur797 (Artur Vargas)
 * @co-authors -
 */

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO autenticar (String user, String password) {

        User usuario = this.userRepository.checkUser(user, password);

        if (usuario != null) {
            return usuario.toDTO();
        } else {
            return null;
        }
    }

}
