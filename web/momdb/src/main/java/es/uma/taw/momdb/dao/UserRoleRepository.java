package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
}
