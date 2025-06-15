package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.Productioncountry;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * @author - arrozet (Rubén Oliva)
 * @co-authors -
 */

public interface ProductionCountryRepository extends JpaRepository<Productioncountry, String> {
}
