package com.shahrohit.chat.controllers;

import com.shahrohit.chat.dtos.PingResponse;
import com.shahrohit.chat.dtos.PublicKeyRequest;
import com.shahrohit.chat.dtos.UploadKeyResponse;
import com.shahrohit.chat.dtos.UserProfile;
import com.shahrohit.chat.models.User;
import com.shahrohit.chat.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/keys")
    public ResponseEntity<UploadKeyResponse> uploadKey(
        @Valid @RequestBody PublicKeyRequest body,
        @AuthenticationPrincipal User authUser
    ){
       if(!authUser.getId().equals(body.userId())){
           throw new RuntimeException("Unauthorized Access");
       }
       boolean uploaded = userService.uploadPublicKey(body, authUser);
       UploadKeyResponse response = new UploadKeyResponse(uploaded);
       return ResponseEntity.ok(response);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<List<UserProfile>> searchUsers(@PathVariable String query){
        System.out.println("Searching for " + query);

        List<UserProfile> users = userService.searchUsers(query);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/ping")
    public ResponseEntity<PingResponse> pinUser(){
        return ResponseEntity.ok(new PingResponse("Pong"));
    }

}
