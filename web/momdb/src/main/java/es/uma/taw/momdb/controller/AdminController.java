package es.uma.taw.momdb.controller;

import es.uma.taw.momdb.dto.GenericEntityDTO;
import es.uma.taw.momdb.dto.UserDTO;
import es.uma.taw.momdb.dto.UsersFormDTO;
import es.uma.taw.momdb.dto.DTOWithNameAndId;
import es.uma.taw.momdb.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para la gestión de las funcionalidades del administrador.
 * Permite la gestión de usuarios y entidades del sistema.
 
 * @author arrozet (Rubén Oliva)
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Autowired private AdminService adminService;

    /**
     * Redirige a la página de gestión de usuarios por defecto.
     *
     * @return La redirección a la página de gestión de usuarios.
     */
    @GetMapping("/")
    public String doInit() {
        return "redirect:/admin/users";
    }

    /**
     * Prepara la página de gestión de usuarios.
     *
     * @param session La sesión HTTP.
     * @param model   El modelo para la vista.
     * @return La vista "admin/users" o una redirección si no hay autorización.
     */
    @GetMapping("/users")
    public String doUsers(@RequestParam(value = "filterName", required = false) String filterName, HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        if (filterName != null) {
            session.setAttribute("userFilterName", filterName);
        } else {
            filterName = (String) session.getAttribute("userFilterName");
        }

        handleUsersData(model, filterName);

        return "admin/users";
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

        UserDTO currentUser = (UserDTO) session.getAttribute("user");

        try {
            boolean selfDemotion = this.adminService.updateUserRoles(usersForm, currentUser);

            if (selfDemotion) {
                session.invalidate();
                return "redirect:/login?logout";
            } else {
                // Actualizo la sesión si estoy cambiando mi usuario
                usersForm.getUsers().stream()
                        .filter(u -> u.getUserId() == currentUser.getUserId())
                        .findFirst()
                        .ifPresent(u -> session.setAttribute("user", adminService.findUser(currentUser.getUserId())));
            }
        } catch (IllegalArgumentException e) {
            session.setAttribute("usersErrorMessage", e.getMessage());
        }

        return "redirect:/admin/users";
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

        DTOWithNameAndId<?> entity = adminService.findEntity(entityType, id);
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

        try {
            adminService.updateEntity(entityType, entity.getId(), entity.getName());
            return "redirect:/admin/entities";
        } catch (IllegalArgumentException e) {
            session.setAttribute("entityErrorMessage", e.getMessage());
            model.addAttribute("entity", entity);
            model.addAttribute("entityType", entityType);
            return "admin/save_entity";
        }
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
            session.setAttribute("entityErrorMessage", e.getMessage());
            model.addAttribute("entity", entity);
            model.addAttribute("entityType", entityType);
            return "admin/save_entity";
        } catch (Exception e) {
            // Para otros errores (p. ej. duplicados), mostrar en la lista
            session.setAttribute("errorMessage", e.getMessage());
            session.setAttribute("errorEntityType", entityType);
            session.setAttribute("errorEntityName", entity.getName());
            return "redirect:/admin/entities";
        }
    }

    /**
     * Maneja la petición para borrar un usuario.
     * @param id El ID del usuario a borrar.
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return Una redirección a la lista de usuarios.
     */
    @GetMapping("/deleteUser")
    public String doDeleteUser(@RequestParam("id") int id, HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }

        UserDTO currentUser = (UserDTO) session.getAttribute("user");

        if (currentUser.getUserId() == id) {
            session.setAttribute("usersErrorMessage", "You cannot delete your own account. Please ask another administrator to do it.");
            return "redirect:/admin/users";
        }

        try {
            adminService.deleteUser(id, currentUser);
        } catch (IllegalArgumentException e) {
            session.setAttribute("usersErrorMessage", e.getMessage());
        }

        return "redirect:/admin/users";
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
     * Prepara los datos para la vista de gestión de usuarios.
     *
     * @param model El modelo para la vista.
     * @param filterName El filtro para buscar usuarios por nombre.
     */
    private void handleUsersData(Model model, String filterName) {
        UsersFormDTO usersForm = new UsersFormDTO();
        usersForm.setUsers(adminService.getUsers(filterName));

        model.addAttribute("usersForm", usersForm);
        model.addAttribute("userRoles", adminService.getUserRoles());
        model.addAttribute("filterName", filterName);
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

    /**
     * Muestra el formulario para añadir un nuevo usuario.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return La vista del formulario para guardar un usuario.
     */
    @GetMapping("/addUser")
    public String doAddUser(Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        model.addAttribute("user", new UserDTO());
        model.addAttribute("userRoles", adminService.getUserRoles());
        return "admin/save_user";
    }

    /**
     * Muestra el formulario para editar un usuario existente.
     * @param id El ID del usuario a editar.
     * @param model El modelo para la vista.
     * @param session La sesión HTTP.
     * @return La vista del formulario para guardar un usuario.
     */
    @GetMapping("/editUser")
    public String doEditUser(@RequestParam("id") int id, Model model, HttpSession session) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        UserDTO user = adminService.findUser(id);
        model.addAttribute("user", user);
        model.addAttribute("userRoles", adminService.getUserRoles());
        return "admin/save_user";
    }

    /**
     * Guarda un usuario (nuevo o existente).
     * @param userDTO El DTO del usuario con los datos del formulario.
     * @param session La sesión HTTP.
     * @param model El modelo para la vista.
     * @return Una redirección a la lista de usuarios.
     */
    @PostMapping("/saveUser")
    public String doSaveUser(@ModelAttribute("user") UserDTO userDTO, HttpSession session, Model model) {
        if (!checkAuth(session, model)) {
            return "redirect:/";
        }
        try {
            adminService.saveUser(userDTO);

            UserDTO sessionUser = (UserDTO) session.getAttribute("user");
            if (userDTO.getUserId() != 0 && sessionUser.getUserId() == userDTO.getUserId()) {
                session.setAttribute("user", adminService.findUser(userDTO.getUserId()));
            }
        } catch (IllegalArgumentException e) {
            session.setAttribute("usersErrorMessage", e.getMessage());
        }
        return "redirect:/admin/users";
    }
}
