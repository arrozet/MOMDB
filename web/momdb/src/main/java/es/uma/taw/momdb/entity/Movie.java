package es.uma.taw.momdb.entity;

import es.uma.taw.momdb.dto.DTO;
import es.uma.taw.momdb.dto.GenreDTO;
import es.uma.taw.momdb.dto.MovieDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "movie")
public class Movie implements Serializable, DTO<MovieDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "movie")
    private Set<Crew> crews = new LinkedHashSet<>();

    @ManyToMany
    private Set<User> favorite_users = new LinkedHashSet<>();

    @ManyToMany
    private Set<Genre> genres = new LinkedHashSet<>();

    @ManyToMany
    private Set<Keyword> keywords = new LinkedHashSet<>();

    @ManyToMany
    private Set<Productioncompany> productioncompanies = new LinkedHashSet<>();

    @ManyToMany
    private Set<Productioncountry> productioncountries = new LinkedHashSet<>();

    @ManyToMany
    private Set<Spokenlanguage> spokenlanguages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "movie")
    private Set<Review> reviews_users = new LinkedHashSet<>();

    @ManyToMany
    private Set<User> watchlist_users = new LinkedHashSet<>();

    public MovieDTO toDTO () {
        MovieDTO movie = new MovieDTO();
        movie.setId(this.id);
        movie.setTitulo(this.title);
        movie.setIdiomaOriginal(this.originalLanguage);
        movie.setFechaDeSalida(this.releaseDate);
        movie.setIngresos(this.revenue);

        List<GenreDTO> listaGeneros = new ArrayList<>();

        this.genres.forEach((final Genre genero) -> listaGeneros.add(genero.toDTO()));
        movie.setGeneros(listaGeneros);


        return movie;

    }

}