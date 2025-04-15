package es.uma.taw.momdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "profile_pic")
    private byte[] profilePic;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private UserRole role;

    @ManyToMany(mappedBy = "favorite_users")
    private Set<Movie> favorite_movies = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Review> reviews_movies = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "favorite_users")
    private Set<Movie> watchlist_movies = new LinkedHashSet<>();

}