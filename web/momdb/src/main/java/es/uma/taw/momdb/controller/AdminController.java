package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.dto.GenericEntityDTO;
import es.uma.taw.momdb.dto.UsersFormDTO;
import es.uma.taw.momdb.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * @author - arrozet (Rubén Oliva)
 * @co-authors -
 */

/**
 * Controlador para la gestión de las funcionalidades del administrador.
 * Permite la gestión de usuarios y entidades del sistema.
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Autowired private AdminService adminService;

    /**
     * Inicializa la página de administración.
     * Carga los datos de los usuarios y las entidades seleccionadas.
     *
     * @param session La sesión HTTP.
     * @param model   El modelo para la vista.
     * @return La vista "admin" o una redirección a la página principal si no hay autorización.
     */
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
        List<?> entities = this.adminService.getEntities(selectedEntity);

        // Añado lo necesario al modelo
        GenericEntityDTO genericEntity = new GenericEntityDTO();
        genericEntity.setSelectedEntity(selectedEntity);

        model.addAttribute("entities", entities);
        model.addAttribute("entityType", selectedEntity);

        // Resto de datos necesarios
        handleData(this.adminService.getUsersForm(), genericEntity, model);

        return "admin";
    }

    /**
     * Maneja la petición para cambiar los roles de los usuarios.
     *
     * @param usersForm DTO con los datos del formulario de usuarios.
     * @param session   La sesión HTTP.
     * @param model     El modelo para la vista.
     * @return Una redirección a la página de administración.
     */
    @PostMapping("/changeUser")
    public String doChangeUser(@ModelAttribute("usersForm") UsersFormDTO usersForm,
                               HttpSession session, Model model) {
        if(!checkAuth(session, model)) {
            return "redirect:/";
        }

        this.adminService.updateUserRoles(usersForm);

        return "redirect:/admin/";
    }

    /**
     * Maneja la petición para mostrar un tipo diferente de entidad.
     *
     * @param genericEntity DTO con la entidad seleccionada.
     * @param session       La sesión HTTP.
     * @param model         El modelo para la vista.
     * @return Una redirección a la página de administración.
     */
    @PostMapping("/showEntities")
    public String doShowEntities(@ModelAttribute("genericEntity") GenericEntityDTO genericEntity,
                                 HttpSession session, Model model) {
        if(!checkAuth(session, model)) {
            return "redirect:/";
        }

        session.setAttribute("selectedEntity", genericEntity.getSelectedEntity());

        return "redirect:/admin/";
    }

    /**
     * Comprueba si el usuario tiene permisos de administrador.
     *
     * @param session La sesión HTTP.
     * @param model   El modelo para la vista.
     * @return {@code true} si el usuario es administrador, {@code false} en caso contrario.
     */
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

    /**
     * Prepara los datos necesarios para la vista de administración.
     *
     * @param usersFormDTO     DTO con los datos del formulario de usuarios.
     * @param genericEntityDTO DTO con la entidad genérica seleccionada.
     * @param model            El modelo para la vista.
     */
    private void handleData(UsersFormDTO usersFormDTO,
                            GenericEntityDTO genericEntityDTO,
                            Model model) {
        // Primera tabla
        model.addAttribute("usersForm", usersFormDTO);
        model.addAttribute("userRoles", this.adminService.findAllUserRoles());

        // Segunda tabla
        model.addAttribute("genericEntity", genericEntityDTO);
        model.addAttribute("everyEntity", List.of("Genre", "Keyword", "ProductionCompany",
                "ProductionCountry", "SpokenLanguage", "CrewRole", "UserRole"));
    }
}
