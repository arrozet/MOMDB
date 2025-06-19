package es.uma.taw.momdb.entity;

import es.uma.taw.momdb.dto.DTO;
import es.uma.taw.momdb.dto.ReviewDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "review")
public class Review implements DTO<ReviewDTO> {
    @EmbeddedId
    private ReviewId id;

    @MapsId("movieId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private es.uma.taw.momdb.entity.User user;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Override
    public ReviewDTO toDTO() {
        ReviewDTO dto = new ReviewDTO();
        dto.setMovieId(this.id.getMovieId());
        dto.setUserId(this.id.getUserId());
        dto.setUsername(this.user.getUsername());
        dto.setContent(this.content);
        dto.setRating(this.rating);
        return dto;
    }
}