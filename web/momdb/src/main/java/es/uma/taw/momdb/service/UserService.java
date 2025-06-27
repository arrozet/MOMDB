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
 * @author arrozet (Rubén Oliva - 67.2%), amcgiluma (Juan Manuel Valenzuela - 22.1%), projectGeorge (Jorge Repullo - 10.8%)
 */
@Service
public class UserService extends DTOService<UserDTO, User> {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    /**
     * Obtiene todos los usuarios del sistema.
     * @return Una lista de DTOs {@link UserDTO}.
     */
    public List<UserDTO> findAllUsers() {
        return entity2DTO(userRepository.findAll());
    }

    /**
     * Busca usuarios cuyo nombre de usuario contenga el texto proporcionado.
     * @param username El texto a buscar en el nombre de usuario.
     * @return Una lista de DTOs {@link UserDTO}.
     */
    public List<UserDTO> findUsersByUsername(String username) {
        return entity2DTO(userRepository.findByUsernameContainingIgnoreCase(username));
    }

    /**
     * Busca un usuario por su ID.
     * @param id El ID del usuario a buscar.
     * @return El {@link UserDTO} encontrado, o null si no existe.
     */
    public UserDTO findUser(int id) {
        User user = userRepository.findById(id).orElse(null);
        return user != null ? user.toDTO() : null;
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

    public void updateUserRole(int userId, int roleId) {
        User user = userRepository.findById(userId).orElse(null);
        UserRole role = userRoleRepository.findById(roleId).orElse(null);

        if (user != null && role != null) {
            user.setRole(role);
            userRepository.save(user);
        }
    }

    /**
     * Borra un usuario por su ID.
     * @param userId El ID del usuario a borrar.
     */
    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }

    /**
     * Guarda un usuario (nuevo o existente) desde el panel de administración.
     * @param userDTO DTO con la información del usuario.
     * @throws IllegalArgumentException si el nombre de usuario o email ya existen.
     */
    public void saveUserFromAdminPanel(UserDTO userDTO) {
        // Si el ID es 0, es un usuario nuevo
        if (userDTO.getUserId() == 0) {
            if (userRepository.findByUsername(userDTO.getUsername()) != null) {
                throw new IllegalArgumentException("Username already exists.");
            }
            if (userRepository.findByEmail(userDTO.getEmail()) != null) {
                throw new IllegalArgumentException("Email already exists.");
            }
            if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
                throw new IllegalArgumentException("Password is required for new users.");
            }

            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword()); // En un caso real, hashearíamos la contraseña
            UserRole role = userRoleRepository.findById(userDTO.getRoleId()).orElse(null);
            user.setRole(role);
            user.setProfilePicLink(userDTO.getProfilePic());
            userRepository.save(user);
        } else { // Si no, es una actualización
            User user = userRepository.findById(userDTO.getUserId()).orElse(null);
            if (user == null) {
                throw new IllegalArgumentException("User not found.");
            }

            // Comprobar si el nuevo username o email ya existen en OTRO usuario
            User byUsername = userRepository.findByUsername(userDTO.getUsername());
            if (byUsername != null && byUsername.getId() != user.getId()) {
                throw new IllegalArgumentException("Username already exists.");
            }
            User byEmail = userRepository.findByEmail(userDTO.getEmail());
            if (byEmail != null && byEmail.getId() != user.getId()) {
                throw new IllegalArgumentException("Email already exists.");
            }

            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                user.setPassword(userDTO.getPassword()); // En un caso real, hashearíamos la contraseña
            }
            UserRole role = userRoleRepository.findById(userDTO.getRoleId()).orElse(null);
            user.setRole(role);
            user.setProfilePicLink(userDTO.getProfilePic());
            userRepository.save(user);
        }
    }
}