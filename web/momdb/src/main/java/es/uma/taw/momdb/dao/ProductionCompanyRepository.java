package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.Productioncompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * @author - arrozet (Rubén Oliva)
 * @co-authors -
 */
public interface ProductionCompanyRepository extends JpaRepository<Productioncompany, Integer> {
    List<Productioncompany> findByCompanyContainingIgnoreCase(String company);
}
