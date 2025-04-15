package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.dao.UserRepository;
import es.uma.taw.momdb.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Autowired private UserRepository userRepository;

    @GetMapping("/")
    public String doInit(HttpSession session, Model model) {
        if(!isAdmin(session)) {
            /*
            TODO: este error no se ve en la página de login. Supongo que es porque model se borra al hacer redirect. Preguntar
            */
            model.addAttribute("error", "You are not authorized to access this page.");
            return "redirect:/";
        }
        else {
            List<User> users = this.userRepository.findAll();
            model.addAttribute("users", users);
            return "admin";
        }
    }
}
