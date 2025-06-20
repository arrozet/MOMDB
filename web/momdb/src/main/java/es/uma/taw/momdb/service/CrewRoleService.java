package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.CrewRoleRepository;
import es.uma.taw.momdb.entity.Crewrole;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author - arrozet (Rubén Oliva)
 * @co-authors - 
 */

@Service
public class CrewRoleService {

    @Autowired
    private CrewRoleRepository crewRoleRepository;

    public Crewrole findCrewRole(int id) {
        return crewRoleRepository.findById(id).orElse(null);
    }

    public void deleteCrewRole(int id) {
        crewRoleRepository.deleteById(id);
    }

    public void updateCrewRole(int id, String name) {
        Crewrole crewRole = crewRoleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CrewRole not found with id: " + id));
        crewRole.setRole(name);
        crewRoleRepository.save(crewRole);
    }
} 