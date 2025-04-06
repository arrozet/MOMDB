package es.uma.taw.momdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "original_title", nullable = false)
    private String originalTitle;

    @Column(name = "budget")
    private Integer budget;

    @Column(name = "homepage", nullable = false)
    private String homepage;

    @Column(name = "original_language", nullable = false)
    private String originalLanguage;

    @Column(name = "overview", nullable = false, length = 2000)
    private String overview;

    @Column(name = "popularity", precision = 19, scale = 4)
    private BigDecimal popularity;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "revenue")
    private Long revenue;

    @Column(name = "runtime")
    private Integer runtime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @Column(name = "tagline")
    private String tagline;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "vote_average", precision = 3, scale = 1)
    private BigDecimal voteAverage;

    @ColumnDefault("0")
    @Column(name = "vote_count")
    private Integer voteCount;

}