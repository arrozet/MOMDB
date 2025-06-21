package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.SpokenLanguageRepository;
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
public class SpokenLanguageService {

    @Autowired
    private SpokenLanguageRepository spokenLanguageRepository;

    /**
     * Obtiene todos los idiomas.
     * @return Una lista de entidades {@link Spokenlanguage}.
     */
    public List<Spokenlanguage> findAllSpokenLanguages() {
        return spokenLanguageRepository.findAll();
    }

    /**
     * Busca idiomas por su nombre.
     * @param language El nombre a buscar.
     * @return Una lista de entidades {@link Spokenlanguage} que contienen el nombre.
     */
    public List<Spokenlanguage> findSpokenLanguagesByLanguage(String language) {
        return spokenLanguageRepository.findByLanguageContainingIgnoreCase(language);
    }

    /**
     * Busca un idioma por su ID (código ISO 639-1).
     * @param id El ID del idioma a buscar.
     * @return El {@link Spokenlanguage} encontrado, o null si no existe.
     */
    public Spokenlanguage findSpokenLanguage(String id) {
        return spokenLanguageRepository.findById(id).orElse(null);
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
        Spokenlanguage spokenLanguage = new Spokenlanguage();
        spokenLanguage.setIso6391(name.substring(0, 2).toLowerCase());
        if(this.findSpokenLanguage(spokenLanguage.getIso6391()) != null) {
            throw new IllegalArgumentException("A spoken language with ISO code '" + spokenLanguage.getIso6391() + "' already exists");
        }
        spokenLanguage.setLanguage(name);
        spokenLanguageRepository.save(spokenLanguage);
    }
} 