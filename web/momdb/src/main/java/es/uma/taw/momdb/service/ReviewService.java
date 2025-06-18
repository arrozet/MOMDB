package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.ReviewRepository;
import es.uma.taw.momdb.dto.ReviewDTO;
import es.uma.taw.momdb.entity.Review;
import es.uma.taw.momdb.entity.ReviewId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService extends DTOService<ReviewDTO, Review> {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<ReviewDTO> getReviewsByMovieId(Integer movieId) {
        List<Review> reviews = reviewRepository.findByMovieId(movieId);
        return this.entity2DTO(reviews);
    }

    public void deleteReview(Integer movieId, Integer userId) {
        ReviewId id = new ReviewId();
        id.setMovieId(movieId);
        id.setUserId(userId);
        reviewRepository.deleteById(id);
    }
} 