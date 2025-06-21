package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.UserRoleRepository;
import es.uma.taw.momdb.entity.UserRole;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * @author - arrozet (Rubén Oliva)
 * @co-authors - 
 */

/**
 * Servicio para gestionar la lógica de negocio de las operaciones sobre los roles de usuario.
 * Proporciona métodos para buscar, actualizar y eliminar roles.
 */
@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    /**
     * Obtiene todos los roles de usuario disponibles en el sistema.
     * @return Una lista de entidades {@link UserRole}.
     */
    public List<UserRole> findAllUserRoles() {
        return userRoleRepository.findAll();
    }

    /**
     * Busca un rol de usuario por su ID.
     * @param id El ID del rol a buscar.
     * @return El {@link UserRole} encontrado, o null si no existe.
     */
    public UserRole findUserRole(int id) {
        return userRoleRepository.findById(id).orElse(null);
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