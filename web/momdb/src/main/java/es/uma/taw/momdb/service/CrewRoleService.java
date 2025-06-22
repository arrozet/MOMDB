package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.CrewRoleRepository;
import es.uma.taw.momdb.dto.CrewRoleDTO;
import es.uma.taw.momdb.entity.Crewrole;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio de las operaciones sobre los roles del equipo (CrewRole).
 * Proporciona métodos para buscar, actualizar y eliminar roles.
 * 
 * @author arrozet (Rubén Oliva)
 */
@Service
public class CrewRoleService extends DTOService<CrewRoleDTO, Crewrole>{

    @Autowired
    private CrewRoleRepository crewRoleRepository;

    /**
     * Busca un CrewRole por su ID.
     * @param id El ID del rol a buscar.
     * @return El {@link CrewRoleDTO} encontrado, o null si no existe.
     */
    public CrewRoleDTO findCrewRole(int id) {
        Crewrole crewRole = crewRoleRepository.findById(id).orElse(null);
        return crewRole != null ? crewRole.toDTO() : null;
    }

    /**
     * Elimina un CrewRole por su ID.
     * @param id El ID del rol a eliminar.
     */
    public void deleteCrewRole(int id) {
        crewRoleRepository.deleteById(id);
    }

    /**
     * Actualiza el nombre de un CrewRole.
     * @param id El ID del rol a actualizar.
     * @param name El nuevo nombre para el rol.
     */
    public void updateCrewRole(int id, String name) {
        Crewrole crewRole = crewRoleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CrewRole not found with id: " + id));
        crewRole.setRole(name);
        crewRoleRepository.save(crewRole);
    }

    /**
     * Crea un nuevo CrewRole.
     * @param name El nombre del nuevo rol.
     */
    public void createCrewRole(String name) {
        Crewrole crewRole = new Crewrole();
        crewRole.setRole(name);
        crewRoleRepository.save(crewRole);
    }

    /**
     * Obtiene todos los roles del equipo.
     * @return Una lista de DTOs {@link CrewRoleDTO}.
     */
    public List<CrewRoleDTO> findAllCrewRoles() {
        return entity2DTO(crewRoleRepository.findAll());
    }

    /**
     * Busca roles del equipo por su nombre.
     * @param role El nombre a buscar.
     * @return Una lista de DTOs {@link CrewRoleDTO} que contienen el nombre.
     */
    public List<CrewRoleDTO> findCrewRolesByRole(String role) {
        return entity2DTO(crewRoleRepository.findByRoleContainingIgnoreCase(role));
    }

    /**
     * Obtiene todos los roles del equipo excepto el de "Actor".
     * @return Lista de entidades de roles de equipo.
     */
    public List<CrewRoleDTO> findAllRolesExceptActor() {
        List<Crewrole> roles = crewRoleRepository.findAllExceptActor();
        return this.entity2DTO(roles);
    }
} 