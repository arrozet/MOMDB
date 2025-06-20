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

/**
 * Servicio para gestionar la lógica de negocio de las operaciones sobre las compañías productoras.
 * Proporciona métodos para buscar, actualizar y eliminar compañías.
 */
@Service
public class ProductionCompanyService {

    @Autowired
    private ProductionCompanyRepository productionCompanyRepository;

    /**
     * Busca una compañía productora por su ID.
     * @param id El ID de la compañía a buscar.
     * @return La {@link Productioncompany} encontrada, o null si no existe.
     */
    public Productioncompany findProductionCompany(int id) {
        return productionCompanyRepository.findById(id).orElse(null);
    }

    /**
     * Elimina una compañía productora por su ID.
     * @param id El ID de la compañía a eliminar.
     */
    public void deleteProductionCompany(int id) {
        productionCompanyRepository.deleteById(id);
    }

    /**
     * Actualiza el nombre de una compañía productora.
     * @param id El ID de la compañía a actualizar.
     * @param name El nuevo nombre para la compañía.
     */
    public void updateProductionCompany(int id, String name) {
        Productioncompany productionCompany = productionCompanyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProductionCompany not found with id: " + id));
        productionCompany.setCompany(name);
        productionCompanyRepository.save(productionCompany);
    }
} 