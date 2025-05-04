package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.Productioncountry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionCountryRepository extends JpaRepository<Productioncountry, String> {
}
