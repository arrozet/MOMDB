package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.Review;
import es.uma.taw.momdb.entity.ReviewId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @author Artur797 (Artur Vargas - 73.9%), projectGeorge (Jorge Repullo - 26.1%)
 */

@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewId> {
    @Query("SELECT r FROM Review r WHERE r.movie.id = :movieId")
    List<Review> findByMovieId(@Param("movieId") Integer movieId);

    @Query("SELECT r FROM Review r WHERE r.user.id = :userId AND r.movie.id = :movieId")
    Review findByUserIdAndMovieId(@Param("userId") Integer userId, @Param("movieId") Integer movieId);

    @Query("select r from Review r where r.user.id = :userId")
    List<Review> findByUserId(@Param("userId") Integer userId);
} 