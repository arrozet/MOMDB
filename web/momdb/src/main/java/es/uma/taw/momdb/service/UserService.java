package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.UserRepository;
import es.uma.taw.momdb.dao.UserRoleRepository;
import es.uma.taw.momdb.dto.UserDTO;
import es.uma.taw.momdb.dto.UserRegistrationDTO;
import es.uma.taw.momdb.entity.User;
import es.uma.taw.momdb.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio de las operaciones sobre los usuarios.
 * Proporciona métodos para registrar, actualizar y gestionar perfiles de usuarios.
 * 
 * @author - projectGeorge (Jorge Repullo), arrozet (Rubén Oliva), amcgiluma (Juan Manuel Valenzuela)
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    /**
     * Obtiene todos los usuarios del sistema.
     * @return Una lista de entidades {@link User}.
     */
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Busca un usuario por su ID.
     * @param id El ID del usuario a buscar.
     * @return El {@link User} encontrado, o null si no existe.
     */
    public User findUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Guarda o actualiza un usuario en la base de datos.
     * @param user El usuario a guardar.
     */
    public void saveUser(User user) {
        userRepository.save(user);
    }

    /**
     * Actualiza el perfil de un usuario.
     * @param userDTO DTO con la información del perfil a actualizar.
     * @return El DTO del usuario actualizado, o null si el usuario no se encuentra.
     */
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

    /**
     * Registra un nuevo usuario en el sistema.
     * @param userDTO DTO con la información de registro.
     * @return El DTO del usuario recién creado.
     * @throws IllegalArgumentException si el nombre de usuario o el email ya existen.
     */
    public UserDTO registerUser(UserRegistrationDTO userDTO) {
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

        User savedUser = userRepository.save(user);
        return savedUser.toDTO();
    }

    /**
     * Asigna el rol de "recomendador" a un usuario.
     * @param userId ID del usuario a actualizar.
     * @return El DTO del usuario actualizado, o null si el usuario o el rol no se encuentran.
     */
    public UserDTO upgradeUserToRecommender(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        // El rol 4 es "recommender"
        UserRole recommenderRole = userRoleRepository.findById(4).orElse(null);
        if (recommenderRole == null) {
            return null;
        }
        user.setRole(recommenderRole);
        userRepository.save(user);
        return user.toDTO();
    }

}