package com.esercizio8.demo.repository;
import java.util.UUID;
import com.esercizio8.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.id = :id AND u.email = :email")
    boolean existsByEmailAndId(@Param("email") String email, @Param("id") UUID id);

}
