package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.KeywordRepository;
import es.uma.taw.momdb.entity.Keyword;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author - arrozet (Rubén Oliva)
 * @co-authors - 
 */

@Service
public class KeywordService {

    @Autowired
    private KeywordRepository keywordRepository;

    public Keyword findKeyword(int id) {
        return keywordRepository.findById(id).orElse(null);
    }

    public void deleteKeyword(int id) {
        keywordRepository.deleteById(id);
    }

    public void updateKeyword(int id, String name) {
        Keyword keyword = keywordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Keyword not found with id: " + id));
        keyword.setKeyword(name);
        keywordRepository.save(keyword);
    }
} 