package com.shahrohit.chat.repositories;

import com.shahrohit.chat.dtos.UserProfile;
import com.shahrohit.chat.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmailOrUsername(String email, String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmailOrUsername(String email, String username);

    @Query("SELECT new com.shahrohit.chat.dtos.UserProfile(u.name, u.username, u.profilePictureUrl) " +
        "FROM User u " +
        "WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
        "OR LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%'))"
    )
    List<UserProfile> searchUser(@Param("query") String query);
}
