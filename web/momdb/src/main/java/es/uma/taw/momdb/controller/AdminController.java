package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.dao.UserRepository;
import es.uma.taw.momdb.dao.UserRoleRepository;
import es.uma.taw.momdb.dto.UserDTO;
import es.uma.taw.momdb.dto.UsersFormDTO;
import es.uma.taw.momdb.entity.User;
import es.uma.taw.momdb.entity.UserRole;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Autowired private UserRepository userRepository;
    @Autowired private UserRoleRepository userRoleRepository;

    @GetMapping("/")
    public String doInit(HttpSession session,
                         Model model) {
        if(!isAdmin(session)) {
            /*
            TODO: este error no se ve en la página de login. Supongo que es porque model se borra al hacer redirect. Preguntar
            */
            model.addAttribute("error", "You are not authorized to access this page.");
            return "redirect:/";
        }
        else {
            // Cojo todos los usuarios
            List<User> users = this.userRepository.findAll();

            // Preparo DTO para formulario
            UsersFormDTO usersFormDTO = new UsersFormDTO();
            List<UserDTO> userDTOs = new ArrayList<>();

            // Relleno usuarios
            for(User u : users) {
                UserDTO dto = new UserDTO();
                dto.setUserId(u.getId());
                dto.setRoleId(u.getRole().getId());
                dto.setUsername(u.getUsername());

                userDTOs.add(dto);
            }
            usersFormDTO.setUsers(userDTOs);

            // Añado lo necesario al modelo
            model.addAttribute("usersForm", usersFormDTO);
            model.addAttribute("userRoles", this.userRoleRepository.findAll());

            return "admin";
        }
    }

    @PostMapping("/changeUser")
    public String doChangeUser(@ModelAttribute("usersForm") UsersFormDTO usersForm,
                               HttpSession session, Model model) {
        if(!isAdmin(session)) {
            /*
            TODO: este error no se ve en la página de login. Supongo que es porque model se borra al hacer redirect. Preguntar
            */
            model.addAttribute("error", "You are not authorized to access this page.");
            return "redirect:/";
        }
        else{
            for (UserDTO userDTO : usersForm.getUsers()) {
                User user = this.userRepository.findById(userDTO.getUserId()).orElse(null);
                UserRole role = this.userRoleRepository.findById(userDTO.getRoleId()).orElse(null);

                if (user != null && role != null) {
                    user.setRole(role);
                    this.userRepository.save(user);
                }
            }
            return "redirect:/admin/";
        }
    }
}
