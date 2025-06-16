package es.uma.taw.momdb.entity;

import es.uma.taw.momdb.dto.DTO;
import es.uma.taw.momdb.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.LinkedHashSet;
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

    @Column(name = "profile_pic_link", length = 2048)
    private String profilePicLink;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private UserRole role;

    @ManyToMany(mappedBy = "favorites")
    private Set<Movie> favorite_movies = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Review> reviews_movies = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "favorites")
    private Set<Movie> watchlist_movies = new LinkedHashSet<>();

    public UserDTO toDTO () {
        UserDTO user = new UserDTO();
        user.setUserId(this.id);
        user.setRoleId(this.role.getId());
        user.setRolename(this.role.getName());
        user.setUsername(this.username);
        user.setProfilePic(this.profilePicLink);

        return user;

    }

}