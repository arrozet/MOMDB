package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.EntityWithNameAndId;
import es.uma.taw.momdb.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Integer> {
}
