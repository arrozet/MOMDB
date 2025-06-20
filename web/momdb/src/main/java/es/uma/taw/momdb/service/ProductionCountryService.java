package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.ProductionCountryRepository;
import es.uma.taw.momdb.entity.Productioncountry;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author - arrozet (Rubén Oliva)
 * @co-authors - 
 */

@Service
public class ProductionCountryService {

    @Autowired
    private ProductionCountryRepository productionCountryRepository;

    public Productioncountry findProductionCountry(String id) {
        return productionCountryRepository.findById(id).orElse(null);
    }

    public void deleteProductionCountry(String id) {
        productionCountryRepository.deleteById(id);
    }

    public void updateProductionCountry(String id, String name) {
        Productioncountry productionCountry = productionCountryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProductionCountry not found with id: " + id));
        productionCountry.setCountry(name);
        productionCountryRepository.save(productionCountry);
    }
} 