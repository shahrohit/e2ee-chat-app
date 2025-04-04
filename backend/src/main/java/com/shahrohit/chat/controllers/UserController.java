package com.shahrohit.chat.controllers;

import com.shahrohit.chat.dtos.PublicKeyRequest;
import com.shahrohit.chat.models.User;
import com.shahrohit.chat.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/keys")
    public String uploadKey(
        @Valid @RequestBody PublicKeyRequest body,
        @AuthenticationPrincipal User authUser
    ){
       if(!authUser.getUsername().equals(body.getUsername())){
           throw new RuntimeException("Unauthorized Access");
       }

       boolean isUploaded = userService.uploadPublicKey(body, authUser);
       return isUploaded ? "Uploaded public Key" : "Already have public key";
    }

}
