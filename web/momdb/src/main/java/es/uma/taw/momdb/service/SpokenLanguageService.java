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

@Service
public class SpokenLanguageService {

    @Autowired
    private SpokenLanguageRepository spokenLanguageRepository;

    public Spokenlanguage findSpokenLanguage(String id) {
        return spokenLanguageRepository.findById(id).orElse(null);
    }

    public void deleteSpokenLanguage(String id) {
        spokenLanguageRepository.deleteById(id);
    }

    public void updateSpokenLanguage(String id, String name) {
        Spokenlanguage spokenLanguage = spokenLanguageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SpokenLanguage not found with id: " + id));
        spokenLanguage.setLanguage(name);
        spokenLanguageRepository.save(spokenLanguage);
    }
} 