package es.uma.taw.momdb.entity;

import es.uma.taw.momdb.dto.DTO;
import es.uma.taw.momdb.dto.GenreDTO;
import es.uma.taw.momdb.dto.MovieDTO;
import es.uma.taw.momdb.dto.CrewDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

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

    @Column(name = "homepage")
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

    @Column(name = "image_link", length = 2048)
    private String imageLink;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "vote_average", precision = 3, scale = 1)
    private BigDecimal voteAverage;

    @ColumnDefault("0")
    @Column(name = "vote_count")
    private Integer voteCount;

    @OneToMany(mappedBy = "movie", fetch = FetchType.EAGER)
    private Set<Crew> crews = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new LinkedHashSet<>();

    @ManyToMany
    private Set<Keyword> keywords = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "movie_productioncompany",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "productioncompany_id"))
    private Set<Productioncompany> productioncompanies = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "movie_productioncountry",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "iso_3166_1"))
    private Set<Productioncountry> productioncountries = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "movie_spokenlanguage",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "iso_639_1"))
    private Set<Spokenlanguage> spokenlanguages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "movie")
    private Set<Review> reviews_users = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "favorite",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> movie_favorite = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "watchlist",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> movie_watchlist = new LinkedHashSet<>();

    public MovieDTO toDTO () {
        MovieDTO movie = new MovieDTO();
        movie.setId(this.id);
        movie.setTitulo(this.title);
        movie.setIdiomaOriginal(this.originalLanguage);
        movie.setFechaDeSalida(this.releaseDate);
        movie.setIngresos(this.revenue);
        movie.setMediaVotos(this.voteAverage);
        movie.setVotos(this.voteCount);
        movie.setDescripcion(this.overview);
        movie.setImageLink(this.imageLink);
        movie.setTagline(this.tagline);
        movie.setDuracion(this.runtime);

        List<GenreDTO> listaGeneros = new ArrayList<>();
        List<Integer> listaGeneroIds = new ArrayList<>();

        this.genres.forEach((final Genre genero) -> {
            listaGeneros.add(genero.toDTO());
            listaGeneroIds.add(genero.getId());
        });
        movie.setGeneros(listaGeneros);
        movie.setGeneroIds(listaGeneroIds);

        List<CrewDTO> listaEquipo = new ArrayList<>();
        List<Integer> listaEquipoIds = new ArrayList<>();

        // Convertir el Set de Crew a List de CrewDTO
        this.crews.stream()
                .sorted(Comparator.comparing(Crew::getId)) // Ordena por ID
                .forEach(crew -> {
                    listaEquipo.add(crew.toDTO());
                    listaEquipoIds.add(crew.getId());
                });
        movie.setEquipo(listaEquipo);
        movie.setEquipoIds(listaEquipoIds);

        return movie;
    }

}