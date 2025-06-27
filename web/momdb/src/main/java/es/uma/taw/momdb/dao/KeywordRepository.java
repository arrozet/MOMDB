package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * @author arrozet (Rubén Oliva)
 */

public interface KeywordRepository extends JpaRepository<Keyword, Integer> {
    List<Keyword> findByKeywordContainingIgnoreCase(String keyword);
}
