package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Query("select m from Movie m where m.title like concat('%', :title, '%')")
    public List<Movie> filterByTitle(@Param("title") String title);

}