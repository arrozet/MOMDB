package es.uma.taw.momdb.entity;

import es.uma.taw.momdb.dto.DTO;
import es.uma.taw.momdb.dto.GenreDTO;
import es.uma.taw.momdb.dto.MovieDTO;
import es.uma.taw.momdb.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User implements Serializable, DTO<UserDTO> {
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

    public UserDTO toDTO () {
        UserDTO user = new UserDTO();
        user.setUserId(this.id);
        user.setRoleId(this.role.getId());
        user.setUsername(this.username);
        user.setProfilePic(this.profilePic);

        return user;

    }

}