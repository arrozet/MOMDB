package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.KeywordRepository;
import es.uma.taw.momdb.dto.KeywordDTO;
import es.uma.taw.momdb.entity.Keyword;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio de las operaciones sobre las palabras clave (Keywords).
 * Proporciona métodos para buscar, actualizar y eliminar palabras clave.
 * 
 * @author arrozet (Rubén Oliva)
 */

@Service
public class KeywordService extends DTOService<KeywordDTO, Keyword> {

    @Autowired
    private KeywordRepository keywordRepository;

    /**
     * Busca una palabra clave por su ID.
     * @param id El ID de la palabra clave a buscar.
     * @return El {@link KeywordDTO} encontrado, o null si no existe.
     */
    public KeywordDTO findKeyword(int id) {
        Keyword keyword = keywordRepository.findById(id).orElse(null);
        return keyword != null ? keyword.toDTO() : null;
    }

    /**
     * Elimina una palabra clave por su ID.
     * @param id El ID de la palabra clave a eliminar.
     */
    public void deleteKeyword(int id) {
        keywordRepository.deleteById(id);
    }

    /**
     * Actualiza el nombre de una palabra clave.
     * @param id El ID de la palabra clave a actualizar.
     * @param name El nuevo nombre para la palabra clave.
     */
    public void updateKeyword(int id, String name) {
        Keyword keyword = keywordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Keyword not found with id: " + id));
        keyword.setKeyword(name);
        keywordRepository.save(keyword);
    }

    /**
     * Crea una nueva palabra clave.
     * @param name El nombre de la nueva palabra clave.
     */
    public void createKeyword(String name) {
        Keyword keyword = new Keyword();
        keyword.setKeyword(name);
        keywordRepository.save(keyword);
    }

    /**
     * Obtiene todas las palabras clave.
     * @return Una lista de DTOs {@link KeywordDTO}.
     */
    public List<KeywordDTO> findAllKeywords() {
        return entity2DTO(keywordRepository.findAll());
    }

    /**
     * Busca palabras clave por su nombre.
     * @param keyword El nombre a buscar.
     * @return Una lista de DTOs {@link KeywordDTO} que contienen el nombre.
     */
    public List<KeywordDTO> findKeywordsByKeyword(String keyword) {
        return entity2DTO(keywordRepository.findByKeywordContainingIgnoreCase(keyword));
    }
} 