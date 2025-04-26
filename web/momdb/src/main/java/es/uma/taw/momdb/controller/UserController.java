package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.entity.Movie;
import es.uma.taw.momdb.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

    @GetMapping("/")
    public String doInit(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "You are not authorized to access this page.");
            return "redirect:/";
        } else {
            // Lógica para el usuario
            model.addAttribute("user", user);
            return "user";
        }
    }
}
