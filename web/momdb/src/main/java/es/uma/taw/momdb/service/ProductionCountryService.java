package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.ProductionCountryRepository;
import es.uma.taw.momdb.dto.ProductionCountryDTO;
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
public class ProductionCountryService extends DTOService<ProductionCountryDTO, Productioncountry> {

    @Autowired
    private ProductionCountryRepository productionCountryRepository;

    /**
     * Busca un país de producción por su ID (código ISO 3166-1).
     * @param id El ID del país a buscar.
     * @return El {@link ProductionCountryDTO} encontrado, o null si no existe.
     */
    public ProductionCountryDTO findProductionCountry(String id) {
        Productioncountry productionCountry = productionCountryRepository.findById(id).orElse(null);
        return productionCountry != null ? productionCountry.toDTO() : null;
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
        if (name == null || name.trim().length() < 2) {
            throw new IllegalArgumentException("Country name must have at least 2 characters.");
        }
        String countryName = name.trim();
        String isoCode = countryName.substring(0, 2).toUpperCase();

        if (this.findProductionCountry(isoCode) != null) {
            throw new IllegalArgumentException("A production country with ISO code '" + isoCode + "' already exists");
        }

        Productioncountry productionCountry = new Productioncountry();
        productionCountry.setIso31661(isoCode);
        productionCountry.setCountry(countryName);
        productionCountryRepository.save(productionCountry);
    }

    /**
     * Obtiene todos los países de producción.
     * @return Una lista de DTOs {@link ProductionCountryDTO}.
     */
    public List<ProductionCountryDTO> findAllProductionCountries() {
        return entity2DTO(productionCountryRepository.findAll());
    }

    /**
     * Busca países de producción por su nombre.
     * @param country El nombre a buscar.
     * @return Una lista de DTOs {@link ProductionCountryDTO} que contienen el nombre.
     */
    public List<ProductionCountryDTO> findProductionCountriesByCountry(String country) {
        return entity2DTO(productionCountryRepository.findByCountryContainingIgnoreCase(country));
    }
} 