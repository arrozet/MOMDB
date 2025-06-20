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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * @author - amcgiluma (Juan Manuel Valenzuela)
 * @co-authors -
 */
@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@RequestParam("username") String username,
                                      @RequestParam("email") String email,
                                      @RequestParam("password") String password,
                                      Model model,
                                      HttpSession session) {
        try {
            UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
            userRegistrationDTO.setUsername(username);
            userRegistrationDTO.setEmail(email);
            userRegistrationDTO.setPassword(password);

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