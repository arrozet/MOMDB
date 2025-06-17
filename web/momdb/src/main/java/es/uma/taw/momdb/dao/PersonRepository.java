package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * @author - Artur797 (Artur Vargas)
 * @co-authors -
 */

public interface PersonRepository extends JpaRepository<Person, Integer> {
} 