package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analyst")
public class AnalystController extends BaseController {

    @GetMapping("/")
    public String doInit(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().getName().equals("analista")) {
            model.addAttribute("error", "You are not authorized to access this page.");
            return "redirect:/";
        } else {
            // Aquí puedes añadir lógica específica para el analista
            return "analyst";
        }
    }
}
