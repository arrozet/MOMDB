package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.dto.UserRegistrationDTO;
import es.uma.taw.momdb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
/*
 * @author - amcgiluma (Juan Manuel Valenzuela)
 * @co-authors -
 */
@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@RequestParam("username") String username,
                                      @RequestParam("email") String email,
                                      @RequestParam("password") String password,
                                      Model model,
                                      RedirectAttributes redirectAttributes) {
        try {
            UserRegistrationDTO userDTO = new UserRegistrationDTO();
            userDTO.setUsername(username);
            userDTO.setEmail(email);
            userDTO.setPassword(password);
            userService.registerUser(userDTO);
            redirectAttributes.addFlashAttribute("success", "¡Usuario registrado con éxito! Por favor, inicie sesión.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}