package com.shahrohit.chat.repositories;

import com.shahrohit.chat.dtos.UserProfile;
import com.shahrohit.chat.models.FriendShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendShipRepository extends JpaRepository<FriendShip, Long> {

    @Query("""
    SELECT CASE WHEN COUNT(fs) > 0 THEN true ELSE false END
    FROM FriendShip fs
    WHERE (fs.user1Id = :senderId AND fs.user2Id = :receiverId)
    OR (fs.user1Id = :receiverId AND fs.user2Id = :senderId)
    """)
    boolean existsFriendShip(
        @Param("senderId") Long senderId,
        @Param("receiverId") Long receiverId
    );

    @Query("""
    SELECT new com.shahrohit.chat.dtos.UserProfile(u.name,u.username,u.profilePictureUrl, com.shahrohit.chat.enums.FriendStatus.FRIEND)
    FROM FriendShip fs
    JOIN User u ON (u.id = CASE WHEN fs.user1Id = :userId THEN fs.user2Id ELSE fs.user1Id END)
    WHERE (fs.user1Id = :userId OR fs.user2Id = :userId)
    """)
    List<UserProfile> getFriends(Long userId);
}
