package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.Crewrole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
 * @author arrozet (Rubén Oliva - 60.9%), Artur797 (Artur Vargas - 39.1%)
 */

public interface CrewRoleRepository extends JpaRepository<Crewrole, Integer> {
    List<Crewrole> findByRoleContainingIgnoreCase(String role);

    @Query("SELECT c FROM Crewrole c WHERE c.role <> 'Actor'")
    List<Crewrole> findAllExceptActor();


    @Query("SELECT c FROM Crewrole c WHERE c.role = 'Actor'")
    Crewrole findActor();
}
