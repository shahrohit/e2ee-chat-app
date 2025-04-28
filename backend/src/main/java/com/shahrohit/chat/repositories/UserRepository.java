package com.shahrohit.chat.repositories;

import com.shahrohit.chat.dtos.UserProfile;
import com.shahrohit.chat.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmailOrUsername(String email, String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    @Query("SELECT u.id FROM User u WHERE u.username = :username")
    Long findIdByUsername(@Param("username") String username);

    @Query("""
    SELECT new com.shahrohit.chat.dtos.UserProfile(
        u.name,
        u.username,
        u.profilePictureUrl,
        CASE
            WHEN frSent.id IS NOT NULL THEN com.shahrohit.chat.enums.FriendStatus.SENT
            WHEN frReceived.id IS NOT NULL THEN com.shahrohit.chat.enums.FriendStatus.RECEIVED
            ELSE com.shahrohit.chat.enums.FriendStatus.NONE
        END
    )
    FROM User u
    LEFT JOIN FriendRequest frSent ON frSent.senderId = :currentUserId AND frSent.receiverId = u.id
    LEFT JOIN FriendRequest frReceived ON frReceived.senderId = u.id AND frReceived.receiverId = :currentUserId
    WHERE u.id <> :currentUserId
    AND (LOWER(u.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%')))
    AND NOT EXISTS (
        SELECT 1 FROM FriendShip f
        WHERE (f.user1Id = :currentUserId AND f.user2Id = u.id)
           OR (f.user1Id = u.id AND f.user2Id = :currentUserId)
    )
""")
    List<UserProfile> searchUser(@Param("query") String query, @Param("currentUserId") Long currentUserId);
}
