package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.dao.UserRepository;
import es.uma.taw.momdb.dto.UserDTO;
import es.uma.taw.momdb.entity.User;
import es.uma.taw.momdb.entity.UserRole;
import es.uma.taw.momdb.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

/*
 * @author - arrozet (Rubén Oliva)
 * @co-authors - Artur797 (Artur Vargas), edugbau (Eduardo González), projectGeorge (Jorge Repullo)
 */

@Controller
@RequestMapping("/")
public class LoginController {
    @Autowired private LoginService loginService;

    @GetMapping("/")
    public String doLogin(){
        return "login";
    }

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
            String roleName = user.getRolename();
            
            if(roleName.equals("admin")){
                return "redirect:/admin/";
            } if(roleName.equals("analista")){
                return "redirect:/analyst/";
            } if (roleName.equals("usuario")){
                return "redirect:/user/";
            } if (roleName.equals("editor")) {
                return "redirect:/editor/";
            }

            // TODO: falta la lógica para el resto de roles
            else{
                model.addAttribute("error", "Invalid username or password");
                return "login";
            }
        }

    }

    @GetMapping("/logout")
    public String doLogout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
    //TODO: implementar logout
}
