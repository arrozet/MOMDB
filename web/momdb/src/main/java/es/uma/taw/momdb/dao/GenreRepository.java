package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * @author - arrozet (Rubén Oliva)
 * @co-authors -
 */

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
