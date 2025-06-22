package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

/*
 * @author - arrozet (Rubén Oliva)
 * @co-authors - edugbau (Eduardo González)
 */

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Query("select m from Movie m where m.title like concat('%', :title, '%')")
    public List<Movie> filterByTitle(@Param("title") String title);

    Page<Movie> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query("""
    select distinct m from Movie m
    left join m.genres g
    where 
        (:generoId is null or g.id = :generoId)
        and (:year is null or function('YEAR', m.releaseDate) >= :year)
        and (:rating is null or m.voteAverage >= :rating)
        and (:popMin is null or m.popularity >= :popMin)
        and (:popMax is null or m.popularity < :popMax)
    """)
    List<Movie> findByFiltros(
            @Param("generoId") Integer generoId,
            @Param("year") Integer year,
            @Param("rating") BigDecimal rating,
            @Param("popMin") BigDecimal popMin,
            @Param("popMax") BigDecimal popMax
    );

    @Query("""
    select distinct m from Movie m
    left join m.genres g
    where
        (:generoId is null or g.id = :generoId)
        and (:year is null or function('YEAR', m.releaseDate) >= :year)
        and (:rating is null or m.voteAverage >= :rating)
        and (:popMin is null or m.popularity >= :popMin)
        and (:popMax is null or m.popularity < :popMax)
    """)
    Page<Movie> findByFiltros(
            @Param("generoId") Integer generoId,
            @Param("year") Integer year,
            @Param("rating") BigDecimal rating,
            @Param("popMin") BigDecimal popMin,
            @Param("popMax") BigDecimal popMax,
            Pageable pageable
    );
    
    //Querys analista
    @Query("SELECT AVG(m.popularity) FROM Movie m")
    BigDecimal getAveragePopularity();

    @Query("SELECT AVG(m.popularity) FROM Movie m JOIN m.genres g WHERE g.id = :genreId")
    BigDecimal getAveragePopularityByGenre(@Param("genreId") Integer genreId);

    @Query("SELECT AVG(m.revenue) FROM Movie m")
    Double getAverageRevenue();

    @Query("SELECT AVG(m.revenue) FROM Movie m JOIN m.genres g WHERE g.id = :genreId")
    Double getAverageRevenueByGenre(@Param("genreId") Integer genreId);

    @Query("SELECT AVG(m.voteAverage) FROM Movie m")
    BigDecimal getAverageVoteAverage();

    @Query("SELECT AVG(m.voteAverage) FROM Movie m JOIN m.genres g WHERE g.id = :genreId")
    BigDecimal getAverageVoteAverageByGenre(@Param("genreId") Integer genreId);

    @Query("SELECT AVG(m.voteCount) FROM Movie m")
    Double getAverageVoteCount();

    @Query("SELECT AVG(m.voteCount) FROM Movie m JOIN m.genres g WHERE g.id = :genreId")
    Double getAverageVoteCountByGenre(@Param("genreId") Integer genreId);

    @Query("SELECT AVG(m.runtime) FROM Movie m")
    Double getAverageRuntime();

    @Query("SELECT AVG(m.runtime) FROM Movie m JOIN m.genres g WHERE g.id = :genreId")
    Double getAverageRuntimeByGenre(@Param("genreId") Integer genreId);

    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE m.id <> :movieId AND g.id IN :genreIds GROUP BY m HAVING COUNT(g.id) = :genreCount ORDER BY m.voteAverage DESC")
    List<Movie> findByExactGenresOrderByRating(@Param("movieId") Integer movieId, @Param("genreIds") List<Integer> genreIds, @Param("genreCount") long genreCount, Pageable pageable);

    @Query("SELECT DISTINCT c1.person.id FROM Crew c1 WHERE c1.movie.id = :movieId1 AND c1.person.id IN (SELECT c2.person.id FROM Crew c2 WHERE c2.movie.id = :movieId2)")
    List<Integer> findCommonPersonIds(@Param("movieId1") Integer movieId1, @Param("movieId2") Integer movieId2);
}