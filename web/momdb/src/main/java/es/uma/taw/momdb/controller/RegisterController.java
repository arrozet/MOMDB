package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.dto.UserDTO;
import es.uma.taw.momdb.dto.UserRegistrationDTO;
import es.uma.taw.momdb.service.LoginService;
import es.uma.taw.momdb.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controlador para gestionar el registro de nuevos usuarios.
 *
 * @author amcgiluma (Juan Manuel Valenzuela - 74.4%), arrozet (Rubén Oliva, Javadocs - 25.6%)
 */
@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    /**
     * Muestra el formulario de registro.
     *
     * @param model El modelo para la vista.
     * @return El nombre de la vista "register".
     */
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserRegistrationDTO());
        return "register";
    }

    /**
     * Procesa la petición de registro de un nuevo usuario.
     * Si el registro es exitoso, inicia la sesión del usuario y lo redirige a su página principal.
     * Si hay un error, muestra el mensaje correspondiente en el formulario de registro.
     *
     * @param userRegistrationDTO DTO con los datos del formulario de registro.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return Una redirección a la página principal del usuario o la vista de registro si hay errores.
     */
    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("user") UserRegistrationDTO userRegistrationDTO,
                                      Model model,
                                      HttpSession session) {
        try {
            // 1. Registra el usuario y obtén su DTO
            UserDTO newUser = userService.registerUser(userRegistrationDTO);

            // 2. Inicia sesión guardando el usuario en la sesión
            session.setAttribute("user", newUser);

            // 3. Obtén la URL de redirección (p. ej., a la lista de películas)
            String redirectURL = loginService.getRedirectURL(newUser);

            if (redirectURL == null) {
                model.addAttribute("error", "Rol de usuario no válido para la redirección.");
                return "login";
            }

            // 4. Redirige al usuario a su página principal
            return redirectURL;

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}