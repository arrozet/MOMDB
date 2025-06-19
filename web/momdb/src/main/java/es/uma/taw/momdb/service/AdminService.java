package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.*;
import es.uma.taw.momdb.dto.UserDTO;
import es.uma.taw.momdb.dto.UsersFormDTO;
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
        }
    }

    /**
     * Obtiene todos los roles de usuario disponibles en el sistema.
     *
     * @return Una lista de entidades {@link UserRole}.
     */
    public List<UserRole> findAllUserRoles() {
        return this.userRoleRepository.findAll();
    }
} 