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

/*
 * @author - projectGeorge (Jorge Repullo)
 */

@Service
public class FavoriteService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    public void addToFavorites(Integer userId, Integer movieId) {
        User user = userRepository.findById(userId).orElse(null);
        Movie movie = movieRepository.findById(movieId).orElse(null);

        if (user != null && movie != null) {
            user.getUser_favorite().add(movie);
            userRepository.save(user);
        }
    }

    public void removeFromFavorites(Integer userId, Integer movieId) {
        User user = userRepository.findById(userId).orElse(null);
        Movie movie = movieRepository.findById(movieId).orElse(null);

        if (user != null && movie != null) {
            user.getUser_favorite().remove(movie);
            userRepository.save(user);
        }
    }

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