package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.Review;
import es.uma.taw.momdb.entity.ReviewId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewId> {
    @Query("SELECT r FROM Review r WHERE r.movie.id = :movieId")
    List<Review> findByMovieId(@Param("movieId") Integer movieId);
} 