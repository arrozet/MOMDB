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
public class WatchlistService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    public void addToWatchlist(Integer userId, Integer movieId) {
        User user = userRepository.findById(userId).orElse(null);
        Movie movie = movieRepository.findById(movieId).orElse(null);

        if (user != null && movie != null) {
            user.getUser_watchlist().add(movie);
            userRepository.save(user);
        }
    }

    public void removeFromWatchlist(Integer userId, Integer movieId) {
        User user = userRepository.findById(userId).orElse(null);
        Movie movie = movieRepository.findById(movieId).orElse(null);

        if (user != null && movie != null) {
            user.getUser_watchlist().remove(movie);
            userRepository.save(user);
        }
    }

    public boolean isInWatchlist(Integer userId, Integer movieId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null && user.getUser_watchlist() != null) {
            for (Movie movie : user.getUser_watchlist()) {
                if (movie.getId().equals(movieId)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<MovieDTO> getUserWatchlist(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);
        List<MovieDTO> listaWatchlist = new ArrayList<>();

        if (user != null && user.getUser_watchlist() != null) {
            for (Movie movie : user.getUser_watchlist()) {
                listaWatchlist.add(movie.toDTO());
            }
        }

        return listaWatchlist;
    }
} 