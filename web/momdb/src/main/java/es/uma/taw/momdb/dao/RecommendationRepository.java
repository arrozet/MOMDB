package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.Recommendation;
import es.uma.taw.momdb.entity.RecommendationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
/*
 * @author - amcgiluma (Juan Manuel Valenzuela)
 * @co-authors -
 */
public interface RecommendationRepository extends JpaRepository<Recommendation, RecommendationId> {
    @Query("SELECT r FROM Recommendation r WHERE r.mainMovie.id = :movieId")
    List<Recommendation> findByMainMovieId(@Param("movieId") Integer movieId);
}