package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.MovieRepository;
import es.uma.taw.momdb.dao.ReviewRepository;
import es.uma.taw.momdb.dao.UserRepository;
import es.uma.taw.momdb.dto.ReviewDTO;
import es.uma.taw.momdb.entity.Movie;
import es.uma.taw.momdb.entity.Review;
import es.uma.taw.momdb.entity.ReviewId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ReviewService extends DTOService<ReviewDTO, Review> {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired 
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    public List<ReviewDTO> getReviewsByMovieId(Integer movieId) {
        List<Review> reviews = reviewRepository.findByMovieId(movieId);
        return this.entity2DTO(reviews);
    }

    public ReviewDTO findReviewByUserAndMovie(Integer userId, Integer movieId) {
        Review review = reviewRepository.findByUserIdAndMovieId(userId, movieId);
        return review != null ? review.toDTO() : null;
    }

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

    public void deleteReview(Integer movieId, Integer userId) {
        ReviewId id = new ReviewId();
        id.setMovieId(movieId);
        id.setUserId(userId);
        reviewRepository.deleteById(id);
    }
} 