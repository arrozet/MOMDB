package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * @author Artur797 (Artur Vargas)
 */

public interface CharacterRepository extends JpaRepository<Character, Integer> {
}
