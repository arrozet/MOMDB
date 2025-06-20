package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.SpokenLanguageRepository;
import es.uma.taw.momdb.entity.Spokenlanguage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author - arrozet (Rubén Oliva)
 * @co-authors - 
 */

/**
 * Servicio para gestionar la lógica de negocio de las operaciones sobre los idiomas.
 * Proporciona métodos para buscar, actualizar y eliminar idiomas.
 */
@Service
public class SpokenLanguageService {

    @Autowired
    private SpokenLanguageRepository spokenLanguageRepository;

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
} 