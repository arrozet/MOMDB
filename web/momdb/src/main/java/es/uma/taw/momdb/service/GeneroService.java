package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.GenreRepository;
import es.uma.taw.momdb.dto.GenreDTO;
import es.uma.taw.momdb.entity.Genre;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio de las operaciones sobre los géneros cinematográficos.
 * Proporciona métodos para buscar, crear, actualizar y eliminar géneros del sistema.
 * 
 * @author Artur797 (Artur Vargas), arrozet (Rubén Oliva)
 */

@Service
public class GeneroService extends DTOService<GenreDTO, Genre>{

    @Autowired
    private GenreRepository genreRepository;

    /**
     * Obtiene todos los géneros.
     * @return Una lista de DTOs {@link GenreDTO}.
     */
    public List<GenreDTO> findAllGenres() {
        return entity2DTO(genreRepository.findAll());
    }

    /**
     * Busca géneros por su nombre.
     * @param genre El nombre a buscar.
     * @return Una lista de DTOs {@link GenreDTO} que contienen el nombre.
     */
    public List<GenreDTO> findGenresByGenre(String genre) {
        return entity2DTO(genreRepository.findByGenreContainingIgnoreCase(genre));
    }

    public List<GenreDTO> listarGeneros () {
        List<Genre> entities = this.genreRepository.findAll();
        return this.entity2DTO(entities);
    }

    /**
     * Busca un género por su ID.
     * @param id El ID del género a buscar.
     * @return El {@link GenreDTO} encontrado, o null si no existe.
     */
    public GenreDTO findGenre(int id) {
        Genre genre = genreRepository.findById(id).orElse(null);
        return genre != null ? genre.toDTO() : null;
    }

    /**
     * Elimina un género por su ID.
     * @param id El ID del género a eliminar.
     */
    public void deleteGenre(int id) {
        genreRepository.deleteById(id);
    }

    /**
     * Actualiza el nombre de un género.
     * @param id El ID del género a actualizar.
     * @param name El nuevo nombre para el género.
     */
    public void updateGenre(int id, String name) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre not found with id: " + id));
        genre.setGenre(name);
        genreRepository.save(genre);
    }

    /**
     * Crea un nuevo género.
     * @param name El nombre del nuevo género.
     */
    public void createGenre(String name) {
        Genre genre = new Genre();
        genre.setGenre(name);
        genreRepository.save(genre);
    }
}
