package com.shahrohit.chat.services.impl;

import com.shahrohit.chat.dtos.PublicKeyRequest;
import com.shahrohit.chat.models.Session;
import com.shahrohit.chat.models.User;
import com.shahrohit.chat.repositories.UserRepository;
import com.shahrohit.chat.services.SessionService;
import com.shahrohit.chat.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SessionService sessionService;

    @Override
    public boolean uploadPublicKey(PublicKeyRequest request, User user) {
        Session session = sessionService.getSession(user)
            .orElseThrow(()-> new RuntimeException("Unauthorized: Please Login again"));

        if(!session.getDeviceFingerprint().equals(request.getDeviceFingerprint())){
            throw new RuntimeException("Unauthorized: Invalid Device");
        }

        if(user.getPublicKey() == null || !user.getPublicKey().equals(request.getPublicKey())){
            user.setPublicKey(request.getPublicKey());
            userRepository.save(user);
            return true;
        }

        return false;
    }
}
