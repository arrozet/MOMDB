package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.MovieRepository;
import es.uma.taw.momdb.dao.ReviewRepository;
import es.uma.taw.momdb.dao.UserRepository;
import es.uma.taw.momdb.dto.MovieDTO;
import es.uma.taw.momdb.dto.ReviewDTO;
import es.uma.taw.momdb.entity.Movie;
import es.uma.taw.momdb.entity.Review;
import es.uma.taw.momdb.entity.ReviewId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio de las operaciones sobre las reseñas de películas.
 * Proporciona métodos para crear, actualizar, eliminar y consultar reseñas de usuarios.
 * 
 * @author projectGeorge (Jorge Repullo), Artur797 (Artur Vargas)
 */
@Service
public class ReviewService extends DTOService<ReviewDTO, Review> {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired 
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieService movieService;

    /**
     * Obtiene todas las reseñas de una película.
     * @param movieId ID de la película.
     * @return Lista de DTO de reseñas.
     */
    public List<ReviewDTO> getReviewsByMovieId(Integer movieId) {
        List<Review> reviews = reviewRepository.findByMovieId(movieId);
        return this.entity2DTO(reviews);
    }

    /**
     * Obtiene todas las reseñas de un usuario.
     * @param userId ID del usuario.
     * @return Lista de DTO de reseñas.
     */
    public List<ReviewDTO> getReviewsByUserId(Integer userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        List<ReviewDTO> dtos = this.entity2DTO(reviews);
        for (ReviewDTO dto : dtos) {
            MovieDTO movie = movieService.findPeliculaById(dto.getMovieId());
            dto.setMovieTitle(movie != null ? movie.getTitulo() : "Unknown");
        }
        return dtos;
    }

    /**
     * Busca una reseña específica de un usuario para una película.
     * @param userId ID del usuario.
     * @param movieId ID de la película.
     * @return El DTO de la reseña, o null si no se encuentra.
     */
    public ReviewDTO findReviewByUserAndMovie(Integer userId, Integer movieId) {
        Review review = reviewRepository.findByUserIdAndMovieId(userId, movieId);
        return review != null ? review.toDTO() : null;
    }

    /**
     * Guarda o actualiza una reseña.
     * Si no existe, crea una nueva.
     * @param userId ID del usuario.
     * @param movieId ID de la película.
     * @param reviewDTO DTO con el contenido y la puntuación de la reseña.
     */
    public void saveOrUpdateReview(Integer userId, Integer movieId, ReviewDTO reviewDTO){
        Review review = reviewRepository.findByUserIdAndMovieId(userId, movieId);

        if (review == null) {
            review = new Review();
            ReviewId id = new ReviewId();
            id.setMovieId(movieId);
            id.setUserId(userId);
            review.setId(id); // <-- ¡Esto es clave!
            review.setUser(userRepository.findById(userId).orElse(null));
            review.setMovie(movieRepository.findById(movieId).orElse(null));
        }
        review.setContent(reviewDTO.getContent());
        review.setRating(reviewDTO.getRating());
        reviewRepository.save(review);
    }

    /**
     * Actualiza la puntuación media de una película basándose en sus reseñas.
     * @param movieId ID de la película.
     */
    public void updateMovieRating(Integer movieId) {
        List<Review> reviews = reviewRepository.findByMovieId(movieId);

        double sum = 0.0;
        int count = 0;

        for (Review r : reviews) {
            if (r.getRating() != null) {
                sum += r.getRating();
                count++;
            }
        }

        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie != null) {
            movie.setVoteAverage(BigDecimal.valueOf(sum / count));
            movieRepository.save(movie);
        }
    }

    /**
     * Elimina una reseña.
     * @param movieId ID de la película.
     * @param userId ID del usuario.
     */
    public void deleteReview(Integer movieId, Integer userId) {
        ReviewId id = new ReviewId();
        id.setMovieId(movieId);
        id.setUserId(userId);
        reviewRepository.deleteById(id);
    }
} 