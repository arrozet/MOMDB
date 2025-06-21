package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.CrewRoleRepository;
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
public class CrewRoleService {

    @Autowired
    private CrewRoleRepository crewRoleRepository;

    /**
     * Busca un CrewRole por su ID.
     * @param id El ID del rol a buscar.
     * @return El {@link Crewrole} encontrado, o null si no existe.
     */
    public Crewrole findCrewRole(int id) {
        return crewRoleRepository.findById(id).orElse(null);
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
     * @return Una lista de entidades {@link Crewrole}.
     */
    public List<Crewrole> findAllCrewRoles() {
        return crewRoleRepository.findAll();
    }

    /**
     * Busca roles del equipo por su nombre.
     * @param role El nombre a buscar.
     * @return Una lista de entidades {@link Crewrole} que contienen el nombre.
     */
    public List<Crewrole> findCrewRolesByRole(String role) {
        return crewRoleRepository.findByRoleContainingIgnoreCase(role);
    }
} 