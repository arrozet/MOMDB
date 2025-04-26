package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.dao.UserRepository;
import es.uma.taw.momdb.entity.User;
import es.uma.taw.momdb.entity.UserRole;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Controller
@RequestMapping("/")
public class LoginController {
    @Autowired private UserRepository userRepository;

    @GetMapping("/")
    public String doLogin(){
        return "login";
    }

    @PostMapping("/authenticate")
    public String doAuthenticate(@RequestParam("username") String username, @RequestParam("password") String password,
                                 Model model, HttpSession session){
        User user = userRepository.checkUser(username, password);
        if(user == null){
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
        else{
            session.setAttribute("user", user);
            String roleName = user.getRole().getName();
            
            if(roleName.equals("admin")){
                return "redirect:/admin/";
            } if(roleName.equals("analista")){
                return "redirect:/analyst/";
            } if (roleName.equals("usuario")){
                return "redirect:/user/";
            }

            // TODO: falta la lógica para el resto de roles
            else{
                model.addAttribute("error", "Invalid username or password");
                return "login";
            }
        }

    }
    //TODO: implementar logout
}
