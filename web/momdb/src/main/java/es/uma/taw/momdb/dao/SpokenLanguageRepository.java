package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.Spokenlanguage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * @author - arrozet (Rubén Oliva)
 * @co-authors -
 */

public interface SpokenLanguageRepository extends JpaRepository<Spokenlanguage, String> {
    List<Spokenlanguage> findByLanguageContainingIgnoreCase(String language);
}
