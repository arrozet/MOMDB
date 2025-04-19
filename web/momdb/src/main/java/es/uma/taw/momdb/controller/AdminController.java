package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.dao.UserRepository;
import es.uma.taw.momdb.dao.UserRoleRepository;
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

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Autowired private UserRepository userRepository;
    @Autowired private UserRoleRepository userRoleRepository;

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
            List<UserRole> userRoles = this.userRoleRepository.findAll();
            model.addAttribute("userRoles", userRoles);
            return "admin";
        }
    }

    @PostMapping("/changeUser")
    public String doChangeUser(@RequestParam Map<String,String> allParams, HttpSession session, Model model) {
        if(!isAdmin(session)) {
            /*
            TODO: este error no se ve en la página de login. Supongo que es porque model se borra al hacer redirect. Preguntar
            */
            model.addAttribute("error", "You are not authorized to access this page.");
            return "redirect:/";
        }
        else{
            // Con Map<String,String> allParams, obtengo todos los parámetros del formulario. Como tengo varios
            // select, y el nombre de cada uno es el id del usuario y el valor es el id del rol
            // debo hacerlo asi
            for(Map.Entry<String,String> entry : allParams.entrySet()) {
                int userId = Integer.parseInt(entry.getKey());
                int roleId = Integer.parseInt(entry.getValue());

                // Busco usuario y rol
                User user = this.userRepository.findById(userId).orElse(null);
                UserRole role = this.userRoleRepository.findById(roleId).orElse(null);

                if(user != null && role != null) {
                    user.setRole(role);
                    this.userRepository.save(user);
                }
            }
            return "redirect:/admin/";
        }
    }
}
