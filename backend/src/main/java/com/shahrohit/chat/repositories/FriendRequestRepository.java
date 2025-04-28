package com.shahrohit.chat.repositories;

import com.shahrohit.chat.dtos.UserProfile;
import com.shahrohit.chat.models.FriendRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    Optional<FriendRequest> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    @Query("""
    SELECT new com.shahrohit.chat.dtos.UserProfile(u.name,u.username,u.profilePictureUrl, com.shahrohit.chat.enums.FriendStatus.RECEIVED)
    FROM FriendRequest fr
    JOIN User u ON u.id = fr.senderId
    WHERE fr.receiverId = :receiverId
    """)
    List<UserProfile> findFriendRequestsByReceiverId(@Param("receiverId") Long receiverId);

    @Query("""
    SELECT new com.shahrohit.chat.dtos.UserProfile(u.name,u.username,u.profilePictureUrl, com.shahrohit.chat.enums.FriendStatus.SENT)
    FROM FriendRequest fr
    JOIN User u On u.id = fr.receiverId
    WHERE fr.senderId = :senderId
    """)
    List<UserProfile> findFriendRequestsBySenderId(@Param("senderId") Long senderId);

    @Query("""
    SELECT CASE WHEN COUNT(fr) > 0 THEN true ELSE false END
    from FriendRequest fr
    WHERE (fr.senderId = :senderId AND fr.receiverId = :receiverId)
    OR (fr.senderId = :receiverId AND fr.receiverId = :senderId)
    """)
    boolean existsFriendRequest(
        @Param("senderId") Long senderId,
        @Param("receiverId") Long receiverId
    );

    @Transactional
    void deleteBySenderIdAndReceiverId(Long senderId, Long id);
}
