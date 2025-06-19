package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.Crewrole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * @author - arrozet (Rubén Oliva)
 * @co-authors -
 */

public interface CrewRoleRepository extends JpaRepository<Crewrole, Integer> {
    List<Crewrole> findByRoleContainingIgnoreCase(String role);
}
