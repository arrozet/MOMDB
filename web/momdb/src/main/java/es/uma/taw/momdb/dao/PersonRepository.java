package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;

/*
 * @author - Artur797 (Artur Vargas)
 * @co-authors -
 */

public interface PersonRepository extends JpaRepository<Person, Integer> {
    List<Person> findByNameContainingIgnoreCase(String name);
} 