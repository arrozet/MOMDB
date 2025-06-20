package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.dto.UserDTO;
import es.uma.taw.momdb.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para gestionar las operaciones de login, autenticación y logout de usuarios.
 *
 * @author - arrozet (Rubén Oliva)
 * @co-authors - Artur797 (Artur Vargas), edugbau (Eduardo González), projectGeorge (Jorge Repullo), amcgiluma  (Juan Manuel Valenzuela)
 */

@Controller
@RequestMapping("/")
public class LoginController {
    @Autowired private LoginService loginService;

    /**
     * Muestra la página de login.
     *
     * @return El nombre de la vista de login.
     */
    @GetMapping(value = {"/", "/login"})
    public String doLogin(){
        return "login";
    }

    /**
     * Autentica a un usuario a partir de su nombre y contraseña. Si la autenticación
     * tiene éxito, guarda al usuario en la sesión y le redirige a su página principal.
     * En caso contrario, le devuelve a la página de login con un mensaje de error.
     *
     * @param username El nombre del usuario.
     * @param password La contraseña del usuario.
     * @param model El modelo de Spring.
     * @param session La sesión HTTP.
     * @return Una cadena de redirección a la página del usuario o a la página de login.
     */
    @PostMapping("/authenticate")
    public String doAuthenticate(@RequestParam("username") String username, @RequestParam("password") String password,
                                 Model model, HttpSession session){
        UserDTO user = this.loginService.autenticar(username, password);
        if(user == null){
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
        else{
            session.setAttribute("user", user);
            String redirectURL = this.loginService.getRedirectURL(user);

            if(redirectURL == null){
                model.addAttribute("error", "Invalid username or password");
                return "login";
            } else {
                return redirectURL;
            }
        }

    }

    /**
     * Cierra la sesión del usuario y le redirige a la página de login.
     *
     * @param session La sesión HTTP que se va a invalidar.
     * @return Una cadena de redirección a la página de login.
     */
    @GetMapping("/logout")
    public String doLogout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}
