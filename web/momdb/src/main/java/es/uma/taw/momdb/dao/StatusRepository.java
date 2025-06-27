package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * @author Artur797 (Artur Vargas)
 */

public interface StatusRepository extends JpaRepository<Status, Integer> {
} 