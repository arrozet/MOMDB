package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.ProductionCompanyRepository;
import es.uma.taw.momdb.entity.Productioncompany;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio de las operaciones sobre las compañías productoras.
 * Proporciona métodos para buscar, actualizar y eliminar compañías.
 * 
 * @author arrozet (Rubén Oliva)
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

    /**
     * Crea una nueva compañía productora.
     * @param name El nombre de la nueva compañía.
     */
    public void createProductionCompany(String name) {
        Productioncompany productionCompany = new Productioncompany();
        productionCompany.setCompany(name);
        productionCompanyRepository.save(productionCompany);
    }

    /**
     * Obtiene todas las compañías productoras.
     * @return Una lista de entidades {@link Productioncompany}.
     */
    public List<Productioncompany> findAllProductionCompanies() {
        return productionCompanyRepository.findAll();
    }

    /**
     * Busca compañías productoras por su nombre.
     * @param company El nombre a buscar.
     * @return Una lista de entidades {@link Productioncompany} que contienen el nombre.
     */
    public List<Productioncompany> findProductionCompaniesByCompany(String company) {
        return productionCompanyRepository.findByCompanyContainingIgnoreCase(company);
    }
} 