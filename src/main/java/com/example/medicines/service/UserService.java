package com.example.medicines.service;

import com.example.medicines.entity.User;
import com.example.medicines.entity.UserType;
import com.example.medicines.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User register(String phoneNumber, UserType userType) {
        User user = User.builder()
                .phoneNumber(phoneNumber)
                .userType(userType)
                .build();
        return userRepository.save(user);
    }
}