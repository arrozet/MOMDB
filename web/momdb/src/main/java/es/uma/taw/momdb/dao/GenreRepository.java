package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * @author - arrozet (Rubén Oliva)
 * @co-authors -
 */

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    List<Genre> findByGenreContainingIgnoreCase(String genre);
}
