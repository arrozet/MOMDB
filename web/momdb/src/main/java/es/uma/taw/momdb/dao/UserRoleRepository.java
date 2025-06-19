package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * @author - arrozet (Rubén Oliva)
 * @co-authors -
 */

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    List<UserRole> findByNameContainingIgnoreCase(String name);
}
