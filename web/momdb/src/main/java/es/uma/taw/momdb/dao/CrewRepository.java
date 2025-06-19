package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.Crew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/*
 * @author - Artur797 (Artur Vargas)
 * @co-authors -
 */

import java.util.List;

public interface CrewRepository extends JpaRepository<Crew, Integer> {
    @Query("select c from Crew c where c.person.name like concat('%', :name, '%')")
    public List<Crew> filterByName(@Param("name") String name);

    @Query("select c from Crew c where c.crewRole.role='Actor' ")
    public List<Crew> filterActors();

    @Query("SELECT c FROM Crew c WHERE c.person.id = :personaId AND c.movie.id = :movieId AND c.crewRole.role='Actor'")
    List<Crew> findActorByPersonAndMovie(@Param("personaId") int personaId, @Param("movieId") int movieId);

    @Query("SELECT c FROM Crew c WHERE c.person.id = :personaId AND c.crewRole.role='Actor'")
    List<Crew> findActorByPerson(@Param("personaId") int personaId);

    @Query("SELECT c FROM Crew c WHERE c.person.id = :personaId AND c.crewRole.role <> 'Actor'")
    List<Crew> findNonActorCrewByPerson(@Param("personaId") int personaId);
}

