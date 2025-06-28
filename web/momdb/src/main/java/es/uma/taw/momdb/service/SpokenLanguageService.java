package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.SpokenLanguageRepository;
import es.uma.taw.momdb.dto.SpokenLanguageDTO;
import es.uma.taw.momdb.entity.Spokenlanguage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio de las operaciones sobre los idiomas.
 * Proporciona métodos para buscar, actualizar y eliminar idiomas.
 * 
 * @author arrozet (Rubén Oliva)
 */
@Service
public class SpokenLanguageService extends DTOService<SpokenLanguageDTO, Spokenlanguage> {

    @Autowired
    private SpokenLanguageRepository spokenLanguageRepository;

    /**
     * Obtiene todos los idiomas.
     * @return Una lista de DTOs {@link SpokenLanguageDTO}.
     */
    public List<SpokenLanguageDTO> findAllSpokenLanguages() {
        return entity2DTO(spokenLanguageRepository.findAll());
    }

    /**
     * Busca idiomas por su nombre.
     * @param language El nombre a buscar.
     * @return Una lista de DTOs {@link SpokenLanguageDTO} que contienen el nombre.
     */
    public List<SpokenLanguageDTO> findSpokenLanguagesByLanguage(String language) {
        return entity2DTO(spokenLanguageRepository.findByLanguageContainingIgnoreCase(language));
    }

    /**
     * Busca un idioma por su ID (código ISO 639-1).
     * @param id El ID del idioma a buscar.
     * @return El {@link SpokenLanguageDTO} encontrado, o null si no existe.
     */
    public SpokenLanguageDTO findSpokenLanguage(String id) {
        Spokenlanguage spokenLanguage = spokenLanguageRepository.findById(id).orElse(null);
        return spokenLanguage != null ? spokenLanguage.toDTO() : null;
    }

    /**
     * Elimina un idioma por su ID.
     * @param id El ID del idioma a eliminar.
     */
    public void deleteSpokenLanguage(String id) {
        spokenLanguageRepository.deleteById(id);
    }

    /**
     * Actualiza el nombre de un idioma.
     * @param id El ID del idioma a actualizar.
     * @param name El nuevo nombre para el idioma.
     */
    public void updateSpokenLanguage(String id, String name) {
        Spokenlanguage spokenLanguage = spokenLanguageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SpokenLanguage not found with id: " + id));
        spokenLanguage.setLanguage(name);
        spokenLanguageRepository.save(spokenLanguage);
    }

    /**
     * Crea un nuevo idioma.
     * El nombre se utiliza como ID (código ISO 639-1) y como nombre del idioma.
     * @param name El nombre y código del nuevo idioma.
     * @throws IllegalArgumentException si ya existe un idioma con el mismo código ISO 639-1.
     */
    public void createSpokenLanguage(String name) {
        if (name == null || name.trim().length() < 2) {
            throw new IllegalArgumentException("Language name must have at least 2 characters.");
        }
        String languageName = name.trim();
        String isoCode = languageName.substring(0, 2).toLowerCase();

        if(this.findSpokenLanguage(isoCode) != null) {
            throw new IllegalArgumentException("A spoken language with ISO code '" + isoCode + "' already exists");
        }

        Spokenlanguage spokenLanguage = new Spokenlanguage();
        spokenLanguage.setIso6391(isoCode);
        spokenLanguage.setLanguage(languageName);
        spokenLanguageRepository.save(spokenLanguage);
    }
} 