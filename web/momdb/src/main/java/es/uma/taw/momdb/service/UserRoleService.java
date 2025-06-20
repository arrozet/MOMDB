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

 
@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public List<UserRole> findAllUserRoles() {
        return userRoleRepository.findAll();
    }

    public UserRole findUserRole(int id) {
        return userRoleRepository.findById(id).orElse(null);
    }

    public void deleteUserRole(int id) {
        userRoleRepository.deleteById(id);
    }

    public void updateUserRole(int id, String name) {
        UserRole userRole = userRoleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserRole not found with id: " + id));
        userRole.setName(name);
        userRoleRepository.save(userRole);
    }
} 