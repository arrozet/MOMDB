package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.dao.*;
import es.uma.taw.momdb.dto.GenericEntityDTO;
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
    @Autowired private CrewRoleRepository crewRoleRepository;
    @Autowired private GenreRepository genreRepository;
    @Autowired private KeywordRepository keywordRepository;
    @Autowired private ProductionCompanyRepository productionCompanyRepository;
    @Autowired private ProductionCountryRepository productionCountryRepository;
    @Autowired private SpokenLanguageRepository spokenLanguageRepository;

    @GetMapping("/")
    public String doInit(HttpSession session,
                         Model model) {
        if(!checkAuth(session, model)) {
            return "redirect:/";
        }


        // Obtener la entidad seleccionada de la sesión si existe. Si no existe, es Genre
        String selectedEntity = (String) session.getAttribute("selectedEntity");
        selectedEntity = selectedEntity != null ? selectedEntity : "Genre";


        // Cargar las entidades según la selección
        List<?> entities = null;
        entities = switch (selectedEntity) {
            case "Genre" -> genreRepository.findAll();
            case "Keyword" -> keywordRepository.findAll();
            case "ProductionCompany" -> productionCompanyRepository.findAll();
            case "ProductionCountry" -> productionCountryRepository.findAll();
            case "SpokenLanguage" -> spokenLanguageRepository.findAll();
            case "CrewRole" -> crewRoleRepository.findAll();
            case "UserRole" -> userRoleRepository.findAll();
            default -> entities;
        };

        // Añado lo necesario al modelo
        GenericEntityDTO genericEntity = new GenericEntityDTO();
        genericEntity.setSelectedEntity(selectedEntity);

        model.addAttribute("entities", entities);
        model.addAttribute("entityType", selectedEntity);

        // Resto de datos necesarios
        handleData(prepareUsersFormDTO(), genericEntity, model);

        return "admin";
    }

    private UsersFormDTO prepareUsersFormDTO() {
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

        return usersFormDTO;
    }

    @PostMapping("/changeUser")
    public String doChangeUser(@ModelAttribute("usersForm") UsersFormDTO usersForm,
                               HttpSession session, Model model) {
        if(!checkAuth(session, model)) {
            return "redirect:/";
        }

        // Recorro todos los usuarios
        for (UserDTO userDTO : usersForm.getUsers()) {
            // Cojo usuario y rol
            User user = this.userRepository.findById(userDTO.getUserId()).orElse(null);
            UserRole role = this.userRoleRepository.findById(userDTO.getRoleId()).orElse(null);

            // Les pongo sus nuevos roles
            if (user != null && role != null) {
                user.setRole(role);
                this.userRepository.save(user);
            }
        }
        return "redirect:/admin/";
    }

    @PostMapping("/showEntities")
    public String doShowEntities(@ModelAttribute("genericEntity") GenericEntityDTO genericEntity,
                                 HttpSession session, Model model) {
        if(!checkAuth(session, model)) {
            return "redirect:/";
        }

        session.setAttribute("selectedEntity", genericEntity.getSelectedEntity());

        return "redirect:/admin/";
    }

    private Boolean checkAuth(HttpSession session, Model model) {
        if(!isAdmin(session)) {
            /*
            TODO: este error no se ve en la página de login. Supongo que es porque model se borra al hacer redirect. Preguntar
            */
            model.addAttribute("error", "You are not authorized to access this page.");
            return false;
        }
        return true;
    }

    private void handleData(UsersFormDTO usersFormDTO,
                            GenericEntityDTO genericEntityDTO,
                            Model model) {
        // Primera tabla
        model.addAttribute("usersForm", usersFormDTO);
        model.addAttribute("userRoles", this.userRoleRepository.findAll());

        // Segunda tabla
        model.addAttribute("genericEntity", genericEntityDTO);
        model.addAttribute("everyEntity", List.of("Genre", "Keyword", "ProductionCompany",
                "ProductionCountry", "SpokenLanguage", "CrewRole", "UserRole"));
    }
}
