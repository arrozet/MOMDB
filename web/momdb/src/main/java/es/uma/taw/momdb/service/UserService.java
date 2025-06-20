package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.UserRepository;
import es.uma.taw.momdb.dao.UserRoleRepository;
import es.uma.taw.momdb.dto.UserDTO;
import es.uma.taw.momdb.dto.UserRegistrationDTO;
import es.uma.taw.momdb.entity.User;
import es.uma.taw.momdb.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/*
 * @author - projectGeorge (Jorge Repullo)
 * @co-authors - amcgiluma (Juan Manuel Valenzuela)
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public UserDTO updateUserProfile(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getUserId()).orElse(null);
        if (user == null) {
            return null;
        }
        user.setUsername(userDTO.getUsername());
        user.setProfilePicLink(userDTO.getProfilePic());
        userRepository.save(user);
        return user.toDTO();
    }


    public void registerUser(UserRegistrationDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()) != null) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso.");
        }
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado.");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        user.setRole(userRoleRepository.findByName("usuario"));

        userRepository.save(user);
    }

} 