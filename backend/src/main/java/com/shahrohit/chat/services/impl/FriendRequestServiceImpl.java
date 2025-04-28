package com.shahrohit.chat.services.impl;

import com.shahrohit.chat.dtos.UserProfile;
import com.shahrohit.chat.exceptions.Conflict;
import com.shahrohit.chat.exceptions.Forbidden;
import com.shahrohit.chat.models.FriendRequest;
import com.shahrohit.chat.models.FriendShip;
import com.shahrohit.chat.repositories.FriendRequestRepository;
import com.shahrohit.chat.repositories.FriendShipRepository;
import com.shahrohit.chat.repositories.UserRepository;
import com.shahrohit.chat.services.FriendRequestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FriendRequestServiceImpl implements FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;
    private final FriendShipRepository friendShipRepository;

    @Override
    public boolean sendFriendRequest(Long senderId, String receiverUsername) {
        Long receiverId = userRepository.findIdByUsername(receiverUsername);
        if(receiverId == null){ throw new Forbidden("User Not Found"); }

        if(senderId.equals(receiverId)) {
            throw new Forbidden("Cannot Send Friend Request");
        }

        boolean hasAlreadyRequested = friendRequestRepository.existsFriendRequest(senderId, receiverId);
        if(hasAlreadyRequested){
            throw new Conflict("Friend Request Already Sent");
        }

        boolean isFriend = friendShipRepository.existsFriendShip(senderId, receiverId);
        if(isFriend){
            throw new Conflict("You are already friends");
        }

        FriendRequest friendRequest = FriendRequest.builder()
            .senderId(senderId)
            .receiverId(receiverId)
            .build();

        friendRequestRepository.save(friendRequest);
        return true;
    }

    @Override
    public boolean cancelFriendRequest(Long senderId, String receiverUsername) {
        Long receiverId = userRepository.findIdByUsername(receiverUsername);
        if(receiverId == null){ throw new Forbidden("User Not Found"); }

        boolean isFriend = friendRequestRepository.existsFriendRequest(senderId, receiverId);
        if(isFriend){
            friendRequestRepository.deleteBySenderIdAndReceiverId(senderId, receiverId);
        } else{
            throw new Forbidden("There is no Friend Request");
        }
        return true;
    }

    @Override
    public List<UserProfile> getSentFriendRequests(Long senderId) {
       return friendRequestRepository.findFriendRequestsBySenderId(senderId);
    }

    @Override
    public List<UserProfile> getReceivedFriendRequests(Long receiverId) {
        return friendRequestRepository.findFriendRequestsByReceiverId(receiverId);
    }

    @Override
    public boolean respondToFriendRequest(Long receiverId, String senderUsername, boolean accepted) {
        Long senderId = userRepository.findIdByUsername(senderUsername);
        if(senderId == null){ throw new Forbidden("User Not Found"); }

        FriendRequest friendRequest = friendRequestRepository.findBySenderIdAndReceiverId(senderId, receiverId)
            .orElseThrow(() -> new Forbidden("Friend Request has not been sent"));

        if(accepted) {
            FriendShip friendShip = FriendShip.builder()
                .user1Id(senderId)
                .user2Id(receiverId)
                .build();

            friendShipRepository.save(friendShip);
        }

        friendRequestRepository.delete(friendRequest);
        return true;
    }

    @Override
    public List<UserProfile> getFriends(Long userId) {
        return friendShipRepository.getFriends(userId);
    }


}
