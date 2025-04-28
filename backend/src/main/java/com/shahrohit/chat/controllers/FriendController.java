package com.shahrohit.chat.controllers;

import com.shahrohit.chat.dtos.UserProfile;
import com.shahrohit.chat.models.User;
import com.shahrohit.chat.services.FriendRequestService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@AllArgsConstructor
public class FriendController {

    private final FriendRequestService friendRequestService;

    @GetMapping("/all")
    public ResponseEntity<List<UserProfile>> getFriends(
        @AuthenticationPrincipal User user
    ){
        List<UserProfile> response = friendRequestService.getFriends(user.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/request")
    public ResponseEntity<Boolean> sendFriendRequest(
        @NotBlank @RequestParam(name = "username") String receiverUsername,
        @AuthenticationPrincipal User sender
    ){
       boolean response = friendRequestService.sendFriendRequest(sender.getId(), receiverUsername);
       return ResponseEntity.ok(response);
    }

    @DeleteMapping("/request/cancel/{username}")
    public ResponseEntity<Boolean> cancelFriendRequest(
        @NotBlank @PathVariable(name = "username") String receiverUsername,
        @AuthenticationPrincipal User sender
    ){
        boolean response = friendRequestService.cancelFriendRequest(sender.getId(), receiverUsername);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/request/sent")
    public ResponseEntity<List<UserProfile>> getSentFriendRequests(
        @AuthenticationPrincipal User sender
    ){
        List<UserProfile> response = friendRequestService.getSentFriendRequests(sender.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/request/received")
    public ResponseEntity<List<UserProfile>> getReceivedFriendRequests(
        @AuthenticationPrincipal User sender
    ){
        List<UserProfile> response = friendRequestService.getReceivedFriendRequests(sender.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/request/respond/{username}")
    public ResponseEntity<Boolean> respondToFriendRequest(
        @NotBlank @PathVariable(name = "username") String senderUsername,
        @NotNull @RequestParam(name = "accepted") Boolean accepted,
        @AuthenticationPrincipal User receiver
    ){
        boolean response = friendRequestService.respondToFriendRequest(receiver.getId(), senderUsername, accepted);
        return ResponseEntity.ok(response);
    }
}