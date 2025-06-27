package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.MovieRepository;
import es.uma.taw.momdb.dao.UserRepository;
import es.uma.taw.momdb.dto.MovieDTO;
import es.uma.taw.momdb.entity.Movie;
import es.uma.taw.momdb.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio de las operaciones sobre las listas de favoritos.
 * Proporciona métodos para añadir, eliminar y consultar películas en las listas de favoritos de los usuarios.
 * 
 * @author projectGeorge (Jorge Repullo - 72.6%), arrozet (Rubén Oliva, Javadocs - 27.4%)
 */

@Service
public class FavoriteService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    /**
     * Añade una película a la lista de favoritos de un usuario.
     * @param userId ID del usuario.
     * @param movieId ID de la película.
     */
    public void addToFavorites(Integer userId, Integer movieId) {
        User user = userRepository.findById(userId).orElse(null);
        Movie movie = movieRepository.findById(movieId).orElse(null);

        if (user != null && movie != null) {
            user.getUser_favorite().add(movie);
            userRepository.save(user);
        }
    }

    /**
     * Elimina una película de la lista de favoritos de un usuario.
     * @param userId ID del usuario.
     * @param movieId ID de la película.
     */
    public void removeFromFavorites(Integer userId, Integer movieId) {
        User user = userRepository.findById(userId).orElse(null);
        Movie movie = movieRepository.findById(movieId).orElse(null);

        if (user != null && movie != null) {
            user.getUser_favorite().remove(movie);
            userRepository.save(user);
        }
    }

    /**
     * Comprueba si una película está en la lista de favoritos de un usuario.
     * @param userId ID del usuario.
     * @param movieId ID de la película.
     * @return `true` si la película está en favoritos, `false` en caso contrario.
     */
    public boolean isFavorite(Integer userId, Integer movieId) {
        User user = userRepository.findUserWithFavorites(userId);
        if (user != null && user.getUser_favorite() != null) {
            for (Movie movie : user.getUser_favorite()) {
                if (movie.getId().equals(movieId)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Obtiene la lista de películas favoritas de un usuario.
     * @param userId ID del usuario.
     * @return Lista de DTO de las películas favoritas.
     */
    public List<MovieDTO> getUserFavorites(Integer userId) {
        User user = userRepository.findUserWithFavorites(userId);
        List<MovieDTO> listaFavoritos = new ArrayList<>();

        if (user != null && user.getUser_favorite() != null) {
            for (Movie movie : user.getUser_favorite()) {
                listaFavoritos.add(movie.toDTO());
            }
        }

        return listaFavoritos;
    }
} 