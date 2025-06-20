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
            case "Genre" -> genreRepository.deleteById(Integer.parseInt(id));
            case "Keyword" -> keywordRepository.deleteById(Integer.parseInt(id));
            case "ProductionCompany" -> productionCompanyRepository.deleteById(Integer.parseInt(id));
            case "ProductionCountry" -> productionCountryRepository.deleteById(id);
            case "SpokenLanguage" -> spokenLanguageRepository.deleteById(id);
            case "CrewRole" -> crewRoleRepository.deleteById(Integer.parseInt(id));
            case "UserRole" -> userRoleRepository.deleteById(Integer.parseInt(id));
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

    /**
     * Busca una entidad por su tipo e ID.
     * @param entityType El tipo de la entidad a buscar.
     * @param id El ID de la entidad a buscar.
     * @return Un objeto que implementa EntityWithNameAndId, o null si no se encuentra.
     */
    public EntityWithNameAndId<?> findEntity(String entityType, String id) {
        return switch (entityType) {
            case "Genre" -> genreRepository.findById(Integer.parseInt(id)).orElse(null);
            case "Keyword" -> keywordRepository.findById(Integer.parseInt(id)).orElse(null);
            case "ProductionCompany" -> productionCompanyRepository.findById(Integer.parseInt(id)).orElse(null);
            case "ProductionCountry" -> productionCountryRepository.findById(id).orElse(null);
            case "SpokenLanguage" -> spokenLanguageRepository.findById(id).orElse(null);
            case "CrewRole" -> crewRoleRepository.findById(Integer.parseInt(id)).orElse(null);
            case "UserRole" -> userRoleRepository.findById(Integer.parseInt(id)).orElse(null);
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
            case "Genre" -> {
                genreRepository.findById(Integer.parseInt(id)).ifPresent(e -> {
                    e.setGenre(name);
                    genreRepository.save(e);
                });
            }
            case "Keyword" -> {
                keywordRepository.findById(Integer.parseInt(id)).ifPresent(e -> {
                    e.setKeyword(name);
                    keywordRepository.save(e);
                });
            }
            case "ProductionCompany" -> {
                productionCompanyRepository.findById(Integer.parseInt(id)).ifPresent(e -> {
                    e.setCompany(name);
                    productionCompanyRepository.save(e);
                });
            }
            case "ProductionCountry" -> {
                productionCountryRepository.findById(id).ifPresent(e -> {
                    e.setCountry(name);
                    productionCountryRepository.save(e);
                });
            }
            case "SpokenLanguage" -> {
                spokenLanguageRepository.findById(id).ifPresent(e -> {
                    e.setLanguage(name);
                    spokenLanguageRepository.save(e);
                });
            }
            case "CrewRole" -> {
                crewRoleRepository.findById(Integer.parseInt(id)).ifPresent(e -> {
                    e.setRole(name);
                    crewRoleRepository.save(e);
                });
            }
            case "UserRole" -> {
                userRoleRepository.findById(Integer.parseInt(id)).ifPresent(e -> {
                    e.setName(name);
                    userRoleRepository.save(e);
                });
            }
        }
    }
} 