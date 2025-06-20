package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

/*
 * @author - Artur797 (Artur Vargas)
 * @co-authors -
 */

public interface PersonRepository extends JpaRepository<Person, Integer> {
    List<Person> findByNameContainingIgnoreCase(String name);
    Page<Person> findByNameContainingIgnoreCase(String name, Pageable pageable);
} 