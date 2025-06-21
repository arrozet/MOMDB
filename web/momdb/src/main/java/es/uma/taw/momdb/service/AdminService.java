package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.*;
import es.uma.taw.momdb.dto.UserDTO;
import es.uma.taw.momdb.dto.UsersFormDTO;
import es.uma.taw.momdb.entity.EntityWithNameAndId;
import es.uma.taw.momdb.entity.User;
import es.uma.taw.momdb.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para gestionar las operaciones de administrador.
 * Proporciona métodos para interactuar con las entidades y usuarios del sistema.
 */
@Service
public class AdminService {
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected UserRoleRepository userRoleRepository;

    @Autowired
    protected CrewRoleRepository crewRoleRepository;

    @Autowired
    protected GenreRepository genreRepository;

    @Autowired
    protected KeywordRepository keywordRepository;

    @Autowired
    protected ProductionCompanyRepository productionCompanyRepository;

    @Autowired
    protected ProductionCountryRepository productionCountryRepository;

    @Autowired
    protected SpokenLanguageRepository spokenLanguageRepository;

    @Autowired private GeneroService generoService;
    @Autowired private KeywordService keywordService;
    @Autowired private ProductionCompanyService productionCompanyService;
    @Autowired private ProductionCountryService productionCountryService;
    @Autowired private SpokenLanguageService spokenLanguageService;
    @Autowired private CrewRoleService crewRoleService;
    @Autowired private UserRoleService userRoleService;

    /**
     * Obtiene una lista de entidades basada en el tipo especificado.
     *
     * @param entityType El tipo de entidad a recuperar (por ejemplo, "Genre", "Keyword").
     * @return Una lista de entidades del tipo solicitado, o una lista vacía si el tipo no es válido.
     */
    public List<?> getEntities(String entityType) {
        return getEntities(entityType, null);
    }

    /**
     * Obtiene una lista de entidades basada en el tipo especificado y filtrada por nombre.
     *
     * @param entityType El tipo de entidad a recuperar (por ejemplo, "Genre", "Keyword").
     * @param filterName El nombre por el que filtrar.
     * @return Una lista de entidades del tipo solicitado, o una lista vacía si el tipo no es válido.
     */
    public List<?> getEntities(String entityType, String filterName) {
        if (filterName == null || filterName.isBlank()) {
            return switch (entityType) {
                case "Genre" -> genreRepository.findAll();
                case "Keyword" -> keywordRepository.findAll();
                case "ProductionCompany" -> productionCompanyRepository.findAll();
                case "ProductionCountry" -> productionCountryRepository.findAll();
                case "SpokenLanguage" -> spokenLanguageRepository.findAll();
                case "CrewRole" -> crewRoleRepository.findAll();
                case "UserRole" -> userRoleRepository.findAll();
                default -> new ArrayList<>();
            };
        } else {
            return switch (entityType) {
                case "Genre" -> genreRepository.findByGenreContainingIgnoreCase(filterName);
                case "Keyword" -> keywordRepository.findByKeywordContainingIgnoreCase(filterName);
                case "ProductionCompany" -> productionCompanyRepository.findByCompanyContainingIgnoreCase(filterName);
                case "ProductionCountry" -> productionCountryRepository.findByCountryContainingIgnoreCase(filterName);
                case "SpokenLanguage" -> spokenLanguageRepository.findByLanguageContainingIgnoreCase(filterName);
                case "CrewRole" -> crewRoleRepository.findByRoleContainingIgnoreCase(filterName);
                case "UserRole" -> userRoleRepository.findByNameContainingIgnoreCase(filterName);
                default -> new ArrayList<>();
            };
        }
    }

    /**
     * Borra una entidad en función de su tipo e id.
     * @param entityType El tipo de la entidad.
     * @param id El id de la entidad.
     */
    public void deleteEntity(String entityType, String id) {
        switch (entityType) {
            case "Genre" -> generoService.deleteGenre(Integer.parseInt(id));
            case "Keyword" -> keywordService.deleteKeyword(Integer.parseInt(id));
            case "ProductionCompany" -> productionCompanyService.deleteProductionCompany(Integer.parseInt(id));
            case "ProductionCountry" -> productionCountryService.deleteProductionCountry(id);
            case "SpokenLanguage" -> spokenLanguageService.deleteSpokenLanguage(id);
            case "CrewRole" -> crewRoleService.deleteCrewRole(Integer.parseInt(id));
            case "UserRole" -> userRoleService.deleteUserRole(Integer.parseInt(id));
        }
    }

    /**
     * Prepara un DTO para el formulario de gestión de usuarios.
     *
     * @return Un {@link UsersFormDTO} que contiene la lista de todos los usuarios y sus roles.
     */
    public UsersFormDTO getUsersForm() {
        List<User> users = this.userRepository.findAll();
        UsersFormDTO usersFormDTO = new UsersFormDTO();
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User u : users) {
            UserDTO dto = new UserDTO();
            dto.setUserId(u.getId());
            dto.setRoleId(u.getRole().getId());
            dto.setUsername(u.getUsername());
            userDTOs.add(dto);
        }
        usersFormDTO.setUsers(userDTOs);

        return usersFormDTO;
    }

    /**
     * Actualiza los roles de los usuarios basándose en los datos del formulario.
     *
     * @param usersForm El DTO que contiene la información de los usuarios y sus nuevos roles.
     */
    public void updateUserRoles(UsersFormDTO usersForm) {
        for (UserDTO userDTO : usersForm.getUsers()) {
            User user = this.userRepository.findById(userDTO.getUserId()).orElse(null);
            UserRole role = this.userRoleRepository.findById(userDTO.getRoleId()).orElse(null);

            if (user != null && role != null) {
                user.setRole(role);
                this.userRepository.save(user);
            }

            // TODO: echar al usuario si se cambia su propio rol (ya no es admin)
        }
    }

    /**
     * Obtiene todos los roles de usuario disponibles en el sistema.
     *
     * @return Una lista de entidades {@link UserRole}.
     */
    public List<UserRole> findAllUserRoles() {
        return this.userRoleService.findAllUserRoles();
    }

    /**
     * Busca una entidad por su tipo e ID.
     * @param entityType El tipo de la entidad a buscar.
     * @param id El ID de la entidad a buscar.
     * @return Un objeto que implementa EntityWithNameAndId, o null si no se encuentra.
     */
    public EntityWithNameAndId<?> findEntity(String entityType, String id) {
        return switch (entityType) {
            case "Genre" -> generoService.findGenre(Integer.parseInt(id));
            case "Keyword" -> keywordService.findKeyword(Integer.parseInt(id));
            case "ProductionCompany" -> productionCompanyService.findProductionCompany(Integer.parseInt(id));
            case "ProductionCountry" -> productionCountryService.findProductionCountry(id);
            case "SpokenLanguage" -> spokenLanguageService.findSpokenLanguage(id);
            case "CrewRole" -> crewRoleService.findCrewRole(Integer.parseInt(id));
            case "UserRole" -> userRoleService.findUserRole(Integer.parseInt(id));
            default -> null;
        };
    }

    /**
     * Actualiza el nombre de una entidad.
     * @param entityType El tipo de la entidad a actualizar.
     * @param id El ID de la entidad a actualizar.
     * @param name El nuevo nombre para la entidad.
     */
    public void updateEntity(String entityType, String id, String name) {
        switch (entityType) {
            case "Genre" -> generoService.updateGenre(Integer.parseInt(id), name);
            case "Keyword" -> keywordService.updateKeyword(Integer.parseInt(id), name);
            case "ProductionCompany" -> productionCompanyService.updateProductionCompany(Integer.parseInt(id), name);
            case "ProductionCountry" -> productionCountryService.updateProductionCountry(id, name);
            case "SpokenLanguage" -> spokenLanguageService.updateSpokenLanguage(id, name);
            case "CrewRole" -> crewRoleService.updateCrewRole(Integer.parseInt(id), name);
            case "UserRole" -> userRoleService.updateUserRole(Integer.parseInt(id), name);
        }
    }

    /**
     * Crea una nueva entidad.
     * @param entityType El tipo de la entidad a crear.
     * @param name El nombre de la nueva entidad.
     */
    public void createEntity(String entityType, String name) {
        switch (entityType) {
            case "Genre" -> generoService.createGenre(name);
            case "Keyword" -> keywordService.createKeyword(name);
            case "ProductionCompany" -> productionCompanyService.createProductionCompany(name);
            case "ProductionCountry" -> productionCountryService.createProductionCountry(name);
            case "SpokenLanguage" -> spokenLanguageService.createSpokenLanguage(name);
            case "CrewRole" -> crewRoleService.createCrewRole(name);
            case "UserRole" -> userRoleService.createUserRole(name);
        }
    }
} 