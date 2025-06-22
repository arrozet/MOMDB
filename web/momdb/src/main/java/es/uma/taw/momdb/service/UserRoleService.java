package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.UserRoleRepository;
import es.uma.taw.momdb.dto.UserRoleDTO;
import es.uma.taw.momdb.entity.UserRole;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio de las operaciones sobre los roles de usuario.
 * Proporciona métodos para buscar, actualizar y eliminar roles.
 * 
 * @author arrozet (Rubén Oliva)
 */
@Service
public class UserRoleService extends DTOService<UserRoleDTO, UserRole> {

    @Autowired
    private UserRoleRepository userRoleRepository;

    /**
     * Obtiene todos los roles de usuario disponibles en el sistema.
     * @return Una lista de DTOs {@link UserRoleDTO}.
     */
    public List<UserRoleDTO> findAllUserRoles() {
        return entity2DTO(userRoleRepository.findAll());
    }

    /**
     * Busca roles de usuario por su nombre.
     * @param name El nombre a buscar.
     * @return Una lista de DTOs {@link UserRoleDTO} que contienen el nombre.
     */
    public List<UserRoleDTO> findUserRolesByName(String name) {
        return entity2DTO(userRoleRepository.findByNameContainingIgnoreCase(name));
    }

    /**
     * Busca un rol de usuario por su ID.
     * @param id El ID del rol a buscar.
     * @return El {@link UserRoleDTO} encontrado, o null si no existe.
     */
    public UserRoleDTO findUserRole(int id) {
        UserRole userRole = userRoleRepository.findById(id).orElse(null);
        return userRole != null ? userRole.toDTO() : null;
    }

    /**
     * Elimina un rol de usuario por su ID.
     * @param id El ID del rol a eliminar.
     */
    public void deleteUserRole(int id) {
        userRoleRepository.deleteById(id);
    }

    /**
     * Actualiza el nombre de un rol de usuario.
     * @param id El ID del rol a actualizar.
     * @param name El nuevo nombre para el rol.
     */
    public void updateUserRole(int id, String name) {
        UserRole userRole = userRoleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserRole not found with id: " + id));
        userRole.setName(name);
        userRoleRepository.save(userRole);
    }

    /**
     * Crea un nuevo rol de usuario.
     * @param name El nombre del nuevo rol.
     */
    public void createUserRole(String name) {
        UserRole userRole = new UserRole();
        userRole.setName(name);
        userRoleRepository.save(userRole);
    }
} 