package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.ProductionCountryRepository;
import es.uma.taw.momdb.entity.Productioncountry;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio de las operaciones sobre los países de producción.
 * Proporciona métodos para buscar, actualizar y eliminar países.
 * 
 * @author arrozet (Rubén Oliva)
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

    /**
     * Crea un nuevo país de producción.
     * El nombre se utiliza como ID (código ISO 3166-1) y como nombre del país.
     * @param name El nombre y código del nuevo país.
     * @throws IllegalArgumentException si ya existe un país de producción con el mismo código ISO 3166-1.
     */
    public void createProductionCountry(String name) {
        Productioncountry productionCountry = new Productioncountry();
        productionCountry.setIso31661(name.substring(0, 2).toUpperCase());
        if(this.findProductionCountry(productionCountry.getIso31661()) != null) {
            throw new IllegalArgumentException("A production country with ISO code '" + productionCountry.getIso31661() + "' already exists");
        }
        productionCountry.setCountry(name);
        productionCountryRepository.save(productionCountry);
    }

    /**
     * Obtiene todos los países de producción.
     * @return Una lista de entidades {@link Productioncountry}.
     */
    public List<Productioncountry> findAllProductionCountries() {
        return productionCountryRepository.findAll();
    }

    /**
     * Busca países de producción por su nombre.
     * @param country El nombre a buscar.
     * @return Una lista de entidades {@link Productioncountry} que contienen el nombre.
     */
    public List<Productioncountry> findProductionCountriesByCountry(String country) {
        return productionCountryRepository.findByCountryContainingIgnoreCase(country);
    }
} 