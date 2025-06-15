package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/*
 * @author - arrozet (Rubén Oliva)
 * @co-authors -
 */

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.username = :user and u.password = :password")
    public User checkUser(@Param("user") String user, @Param("password") String password);
}
