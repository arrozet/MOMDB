package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.Crew;
import es.uma.taw.momdb.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CrewRepository extends JpaRepository<Crew, Integer> {
    @Query("select c from Crew c where c.person.name like concat('%', :name, '%')")
    public List<Crew> filterByName(@Param("name") String name);

    @Query("select c from Crew c where c.crewRole.id = 13 ")
    public List<Crew> filterActors();
}
