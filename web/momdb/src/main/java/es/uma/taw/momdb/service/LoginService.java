package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.UserRepository;
import es.uma.taw.momdb.dto.UserDTO;
import es.uma.taw.momdb.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio para gestionar la lógica de negocio relacionada con la autenticación de usuarios.
 *
 * @author - Artur797 (Artur Vargas), arrozet (Rubén Oliva)
 */

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Autentica a un usuario comprobando sus credenciales contra la base de datos.
     *
     * @param user El nombre de usuario.
     * @param password La contraseña del usuario.
     * @return Un objeto UserDTO si la autenticación es correcta, o null en caso contrario.
     */
    public UserDTO autenticar (String user, String password) {

        User usuario = this.userRepository.checkUser(user, password);

        if (usuario != null) {
            return usuario.toDTO();
        } else {
            return null;
        }
    }

    /**
     * Determina la URL de redirección para un usuario en función de su rol.
     *
     * @param user El DTO del usuario autenticado.
     * @return Una cadena con la URL de redirección, o null si el rol no es válido.
     */
    public String getRedirectURL(UserDTO user) {
        String roleName = user.getRolename();
        switch (roleName) {
            case "admin":
                return "redirect:/admin/";
            case "analista":
                return "redirect:/analyst/";
            case "usuario":
                return "redirect:/user/";
            case "editor":
                return "redirect:/editor/";
            case "recomendador":
                return "redirect:/recommender/";
            default:
                return null;
        }
    }

}
