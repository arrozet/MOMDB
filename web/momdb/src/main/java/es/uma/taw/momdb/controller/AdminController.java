package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.dto.GenericEntityDTO;
import es.uma.taw.momdb.dto.UsersFormDTO;
import es.uma.taw.momdb.entity.EntityWithNameAndId;
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
     * Redirige a la página de gestión de roles de usuario por defecto.
     *
     * @return La redirección a la página de gestión de roles.
     */
    @GetMapping("/")
    public String doInit() {
        return "redirect:/admin/roles";
    }

    /**
     * Prepara la página de gestión de roles de usuario.
     *
     * @param session La sesión HTTP.
     * @param model   El modelo para la vista.
     * @return La vista "admin/roles" o una redirección si no hay autorización.
     */
    @GetMapping("/roles")
    public String doRoles(HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        handleRolesData(model);

        return "admin/roles";
    }

    /**
     * Prepara la página de gestión de entidades.
     *
     * @param session La sesión HTTP.
     * @param model   El modelo para la vista.
     * @return La vista "admin/entities" o una redirección si no hay autorización.
     */
    @GetMapping("/entities")
    public String doEntities(HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        // Obtener la entidad seleccionada de la sesión si existe. Si no existe, es Genre
        String selectedEntity = (String) session.getAttribute("selectedEntity");
        selectedEntity = selectedEntity != null ? selectedEntity : "Genre";

        String filterName = (String) session.getAttribute("filterName");

        handleEntitiesData(model, selectedEntity, filterName);

        return "admin/entities";
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

        return "redirect:/admin/roles";
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
        session.setAttribute("filterName", genericEntity.getFilterName());

        return "redirect:/admin/entities";
    }

    /**
     * Muestra el formulario para editar una entidad.
     * @param id El ID de la entidad a editar.
     * @param entityType El tipo de la entidad a editar.
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return La vista del formulario de edición o una redirección.
     */
    @GetMapping("/editEntity")
    public String doEditEntity(@RequestParam("id") String id, @RequestParam("entityType") String entityType, HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        EntityWithNameAndId<?> entity = adminService.findEntity(entityType, id);
        if (entity == null) {
            return "redirect:/admin/entities";
        }

        GenericEntityDTO entityDTO = new GenericEntityDTO();
        entityDTO.setId(String.valueOf(entity.getId()));
        entityDTO.setName(entity.getName());

        model.addAttribute("entity", entityDTO);
        model.addAttribute("entityType", entityType);

        return "admin/save_entity";
    }

    /**
     * Procesa la actualización de una entidad.
     * @param entity El DTO con los datos de la entidad a actualizar.
     * @param entityType El tipo de la entidad.
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return Una redirección a la lista de entidades.
     */
    @PostMapping("/updateEntity")
    public String doUpdateEntity(@ModelAttribute("entity") GenericEntityDTO entity, @RequestParam("entityType") String entityType, HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        adminService.updateEntity(entityType, entity.getId(), entity.getName());

        return "redirect:/admin/entities";
    }

    /**
     * Maneja la petición para borrar una entidad.
     * @param entityType El tipo de entidad a borrar.
     * @param id El ID de la entidad a borrar.
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return Una redirección a la lista de entidades.
     */
    @PostMapping("/deleteEntity")
    public String doDeleteEntity(@RequestParam("entityType") String entityType,
                                 @RequestParam("id") String id,
                                 HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        adminService.deleteEntity(entityType, id);

        return "redirect:/admin/entities";
    }

    /**
     * Muestra el formulario para crear una entidad.
     * @param entityType El tipo de la entidad a crear.
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return La vista del formulario de edición o una redirección.
     */
    @GetMapping("/addEntity")
    public String doAddEntity(@RequestParam("entityType") String entityType, HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        model.addAttribute("entity", new GenericEntityDTO());
        model.addAttribute("entityType", entityType);

        return "admin/save_entity";
    }

    /**
     * Procesa la creación de una entidad.
     * @param entity El DTO con los datos de la entidad a crear.
     * @param entityType El tipo de la entidad.
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return Una redirección a la lista de entidades.
     */
    @PostMapping("/createEntity")
    public String doCreateEntity(@ModelAttribute("entity") GenericEntityDTO entity, @RequestParam("entityType") String entityType, HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        try {
            adminService.createEntity(entityType, entity.getName());
            return "redirect:/admin/entities";
        } catch (IllegalArgumentException e) {
            // Guardar el mensaje de error en la sesión para mostrarlo en la vista
            session.setAttribute("errorMessage", e.getMessage());
            session.setAttribute("errorEntityType", entityType);
            session.setAttribute("errorEntityName", entity.getName());
            return "redirect:/admin/entities";
        }
    }

    /**
     * Comprueba si el usuario tiene permisos de administrador.
     *
     * @param session La sesión HTTP.
     * @param model   El modelo para la vista.
     * @return {@code true} si el usuario es administrador, {@code false} en caso contrario.
     */
    private boolean checkAuth(HttpSession session, Model model) {
        return super.checkAuth(session, model, "admin");
    }

    /**
     * Prepara los datos necesarios para la vista de gestión de roles.
     *
     * @param model El modelo para la vista.
     */
    private void handleRolesData(Model model) {
        model.addAttribute("usersForm", this.adminService.getUsersForm());
        model.addAttribute("userRoles", this.adminService.findAllUserRoles());
    }

    /**
     * Prepara los datos necesarios para la vista de gestión de entidades.
     *
     * @param model          El modelo para la vista.
     * @param selectedEntity La entidad seleccionada para mostrar.
     * @param filterName     El nombre del filtro aplicado.
     */
    private void handleEntitiesData(Model model, String selectedEntity, String filterName) {
        // Cargar las entidades según la selección
        List<?> entities = this.adminService.getEntities(selectedEntity, filterName);

        // Añado lo necesario al modelo
        GenericEntityDTO genericEntity = new GenericEntityDTO();
        genericEntity.setSelectedEntity(selectedEntity);
        genericEntity.setFilterName(filterName);

        model.addAttribute("entities", entities);
        model.addAttribute("entityType", selectedEntity);

        // Resto de datos necesarios
        model.addAttribute("genericEntity", genericEntity);
        model.addAttribute("everyEntity", List.of("Genre", "Keyword", "ProductionCompany",
                "ProductionCountry", "SpokenLanguage", "CrewRole", "UserRole"));
    }
}
