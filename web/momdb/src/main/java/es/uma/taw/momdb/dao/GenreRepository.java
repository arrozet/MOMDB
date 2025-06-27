package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
 * @author arrozet (Rubén Oliva - 73.7%), edugbau (Eduardo González - 26.3%)
 */

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    List<Genre> findByGenreContainingIgnoreCase(String genre);

    @Query("SELECT g.genre, size(g.movies) FROM Genre g ORDER BY g.genre")
    List<Object[]> countMoviesByGenre();
}
