package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*
 * @author - arrozet (Rubén Oliva)
 * @co-authors - edugbau (Eduardo González)
 */

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Query("select m from Movie m where m.title like concat('%', :title, '%')")
    public List<Movie> filterByTitle(@Param("title") String title);

    Page<Movie> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query("select distinct m from Movie m join m.genres g where g.id in :genreIds")
    public List<Movie> filterByGenresId(List<Integer> genreIds);
}