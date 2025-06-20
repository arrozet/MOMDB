package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.ProductionCompanyRepository;
import es.uma.taw.momdb.entity.Productioncompany;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author - arrozet (Rubén Oliva)
 * @co-authors - 
 */

@Service
public class ProductionCompanyService {

    @Autowired
    private ProductionCompanyRepository productionCompanyRepository;

    public Productioncompany findProductionCompany(int id) {
        return productionCompanyRepository.findById(id).orElse(null);
    }

    public void deleteProductionCompany(int id) {
        productionCompanyRepository.deleteById(id);
    }

    public void updateProductionCompany(int id, String name) {
        Productioncompany productionCompany = productionCompanyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProductionCompany not found with id: " + id));
        productionCompany.setCompany(name);
        productionCompanyRepository.save(productionCompany);
    }
} 