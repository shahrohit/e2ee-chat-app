package com.shahrohit.chat.services;

import com.shahrohit.chat.dtos.UserProfile;

import java.util.List;

public interface FriendRequestService {
    boolean sendFriendRequest(Long senderId, String receiverUsername);
    boolean cancelFriendRequest(Long senderId, String receiverUsername);
    List<UserProfile> getSentFriendRequests(Long userId);
    List<UserProfile> getReceivedFriendRequests(Long userId);
    boolean respondToFriendRequest(Long receiverId, String receiverUsername, boolean accepted);
    List<UserProfile> getFriends(Long userId);
}
