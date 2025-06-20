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

/**
 * Servicio para gestionar la lógica de negocio de las operaciones sobre los países de producción.
 * Proporciona métodos para buscar, actualizar y eliminar países.
 */
@Service
public class ProductionCountryService {

    @Autowired
    private ProductionCountryRepository productionCountryRepository;

    /**
     * Busca un país de producción por su ID (código ISO 3166-1).
     * @param id El ID del país a buscar.
     * @return El {@link Productioncountry} encontrado, o null si no existe.
     */
    public Productioncountry findProductionCountry(String id) {
        return productionCountryRepository.findById(id).orElse(null);
    }

    /**
     * Elimina un país de producción por su ID.
     * @param id El ID del país a eliminar.
     */
    public void deleteProductionCountry(String id) {
        productionCountryRepository.deleteById(id);
    }

    /**
     * Actualiza el nombre de un país de producción.
     * @param id El ID del país a actualizar.
     * @param name El nuevo nombre para el país.
     */
    public void updateProductionCountry(String id, String name) {
        Productioncountry productionCountry = productionCountryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProductionCountry not found with id: " + id));
        productionCountry.setCountry(name);
        productionCountryRepository.save(productionCountry);
    }
} 