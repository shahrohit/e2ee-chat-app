package com.shahrohit.chat.controllers;

import com.shahrohit.chat.dtos.PingResponse;
import com.shahrohit.chat.dtos.PublicKeyRequest;
import com.shahrohit.chat.dtos.UploadKeyResponse;
import com.shahrohit.chat.models.User;
import com.shahrohit.chat.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/ping")
    public ResponseEntity<PingResponse> pinUser(){
        System.out.println("Ping User");
//        System.out.println(authUser.getUsername());
        return ResponseEntity.ok(new PingResponse("Pong"));
    }

}
