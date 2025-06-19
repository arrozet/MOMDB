package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.Productioncountry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * @author - arrozet (Rubén Oliva)
 * @co-authors -
 */

public interface ProductionCountryRepository extends JpaRepository<Productioncountry, String> {
    List<Productioncountry> findByCountryContainingIgnoreCase(String country);
}
